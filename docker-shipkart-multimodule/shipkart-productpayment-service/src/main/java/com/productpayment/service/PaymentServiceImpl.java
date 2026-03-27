package com.productpayment.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.productpayment.model.entities.Payment;
import com.productpayment.model.entities.PaymentAttempt;
import com.productpayment.model.enums.AttemptStatus;
import com.productpayment.model.enums.PaymentStatus;
import com.productpayment.repository.IPaymentRepository;
import com.productpayment.thirdparty.paymentgateway.GatewayResponse;
import com.productpayment.thirdparty.paymentgateway.PaymentGatewayIntegration;
import com.sharedevents.models.PaymentRequestedEvent;
import com.sharedevents.models.PaymentResultEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {

	private final IPaymentRepository paymentRepository;

	private final PaymentGatewayIntegration paymentGateway;

	private final KafkaTemplate<String, PaymentResultEvent> kafkaTemplate;

	@Value("${kafka-topic-names.payment-result}")
	private String paymentResultTopic;

	private Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Override
	public void processPayment(PaymentRequestedEvent event) {

		// Step 1 — create Payment master record as PENDING
		Payment payment = new Payment();
		payment.setOrderId(event.getOrderId());
		payment.setUserId(event.getUserId());
		payment.setAmount(event.getAmount());
		payment.setPaymentMethod(event.getPaymentMethod());
		payment.setStatus(PaymentStatus.PENDING);
		payment.setCreatedAt(LocalDateTime.now());
		payment.setUpdatedAt(LocalDateTime.now());
		Payment savedPayment = paymentRepository.save(payment);

		// Step 2 — call the mock gateway
		GatewayResponse gatewayResponse = paymentGateway.charge(event.getOrderId(), event.getAmount(),
				event.getPaymentMethod());

		// Step 3 — record the attempt
		PaymentAttempt attempt = new PaymentAttempt();
		attempt.setPayment(savedPayment);
		attempt.setPaymentMethod(event.getPaymentMethod());
		attempt.setStatus(gatewayResponse.isSuccess() ? AttemptStatus.SUCCESS : AttemptStatus.FAILED);
		attempt.setGatewayTransactionId(gatewayResponse.getGatewayTransactionId());
		attempt.setGatewayOrderId(gatewayResponse.getGatewayOrderId());
		attempt.setGatewayStatus(gatewayResponse.getGatewayStatus());
		attempt.setGatewayResponseCode(gatewayResponse.getGatewayResponseCode());
		attempt.setFailureReason(gatewayResponse.getFailureReason());
		attempt.setAmountRequested(event.getAmount());
		attempt.setAmountCaptured(gatewayResponse.getAmountCaptured());
		attempt.setAttemptedAt(LocalDateTime.now());
		attempt.setGatewayTimestamp(gatewayResponse.getGatewayTimestamp());
		attempt.setRawGatewayResponse(gatewayResponse.getRawGatewayResponse());
		savedPayment.getAttempts().add(attempt);

		// Step 4 — update Payment master status
		savedPayment.setStatus(gatewayResponse.isSuccess() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
		savedPayment.setUpdatedAt(LocalDateTime.now());
		paymentRepository.save(savedPayment);
		// This saves both Payment and PaymentAttempt
		// because of CascadeType.ALL on the attempts list

		// Step 5 — publish result event
		PaymentResultEvent resultEvent = new PaymentResultEvent();
		resultEvent.setOrderId(event.getOrderId());
		resultEvent.setPaymentId(savedPayment.getPaymentId());
		resultEvent.setSuccess(gatewayResponse.isSuccess());
		resultEvent.setAmountCharged(gatewayResponse.getAmountCaptured());
		resultEvent.setFailureReason(gatewayResponse.getFailureReason());

		kafkaTemplate.send(paymentResultTopic, event.getOrderId().toString(), // partition key
				resultEvent);

		logger.info("Produce PaymentResultEvent. Event: {}", resultEvent);

	}

}
