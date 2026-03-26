package com.productpayment.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.productpayment.service.IPaymentService;
import com.sharedevents.models.PaymentRequestedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentListeners {

	private final IPaymentService paymentService;

	@KafkaListener(topics = "${kafka-topic-names.payment-requested}", groupId = "product_payment-group", properties = {
			"spring.json.value.default.type=com.sharedevents.models.PaymentRequestedEvent" })
	public void handlePaymentRequestedEvent(PaymentRequestedEvent event) {
		paymentService.processPayment(event);
	}

}
