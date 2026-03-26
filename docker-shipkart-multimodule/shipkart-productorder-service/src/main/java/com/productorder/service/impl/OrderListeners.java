package com.productorder.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.productorder.model.entities.Order;
import com.productorder.service.IOrderService;
import com.sharedevents.models.OrderPlacedEvent;
import com.sharedevents.models.PaymentRequestedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderListeners {

	private final KafkaTemplate<String, PaymentRequestedEvent> kafkaTemplate;

	private final IOrderService orderService;

	@Value("${kafka-topic-names.payment-requested}")
	private String paymentRequestedTopicName;

	private Logger logger = LoggerFactory.getLogger(OrderListeners.class);

	@KafkaListener(topics = "${kafka-topic-names.order-placed}", groupId = "product_order-group", properties = {
			"spring.json.value.default.type=com.sharedevents.models.OrderPlacedEvent" })
	public void handleOrderPlacedEvent(OrderPlacedEvent orderEvent) {
		logger.info("Consume OrderPlacedEvent. Event: {}", orderEvent);

		Order savedOrder = orderService.createOrder(orderEvent);

		// Publish event to "payment-requested-events" topic to initiate payment
		PaymentRequestedEvent paymentEvent = new PaymentRequestedEvent();
		paymentEvent.setOrderId(savedOrder.getOrderId());
		paymentEvent.setUserId(orderEvent.getUserId());
		paymentEvent.setAmount(orderEvent.getTotalAmount());
		paymentEvent.setPaymentMode(orderEvent.getPaymentMode());

		kafkaTemplate.send(paymentRequestedTopicName, savedOrder.getOrderId().toString(), paymentEvent);

		logger.info("Produce PaymentRequestedEvent. Event: {}", paymentEvent);
	}

}
