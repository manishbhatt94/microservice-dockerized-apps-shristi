package com.productorder.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.productorder.model.dtos.OrderDto;
import com.productorder.model.entities.Order;
import com.productorder.model.entities.OrderItem;
import com.productorder.model.enums.OrderStatus;
import com.productorder.repository.IOrderRepository;
import com.productorder.service.IOrderService;
import com.sharedevents.models.OrderPlacedEvent;
import com.sharedevents.models.PaymentRequestedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

	private final IOrderRepository orderRepository;

	private final ModelMapper mapper;

	private final KafkaTemplate<String, PaymentRequestedEvent> kafkaTemplate;

	@Value("${kafka-topic-names.payment-requested}")
	private String paymentRequestedTopicName;

	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	public OrderDto getOrderByOrderId(int orderId) {
		Order order = orderRepository.findByOrderIdWithItems(orderId)
				.orElseThrow(() -> new RuntimeException(String.format("Order with ID: %d - Not Found", orderId)));
		return toOrderDto(order);
	}

	@Override
	public List<OrderDto> getOrdersByUserId(int userId) {
		return orderRepository.findByUserId(userId).stream().map(this::toOrderDto).toList();
	}

	@KafkaListener(topics = "${kafka-topic-names.order-placed}", groupId = "product_order-group", properties = {
			"spring.json.value.default.type=com.sharedevents.models.OrderPlacedEvent" })
	private void handleOrderPlacedEvent(OrderPlacedEvent orderEvent) {
		logger.info("Consume OrderPlacedEvent. Event: {}", orderEvent);

		Order savedOrder = createOrder(orderEvent);

		// Publish event to "payment-requested-events" topic to initiate payment
		PaymentRequestedEvent paymentEvent = new PaymentRequestedEvent();
		paymentEvent.setOrderId(savedOrder.getOrderId());
		paymentEvent.setUserId(orderEvent.getUserId());
		paymentEvent.setAmount(orderEvent.getTotalAmount());
		paymentEvent.setPaymentMode(orderEvent.getPaymentMode());

		kafkaTemplate.send(paymentRequestedTopicName, savedOrder.getOrderId().toString(), paymentEvent);

		logger.info("Produce PaymentRequestedEvent. Event: {}", paymentEvent);
	}

	private Order createOrder(OrderPlacedEvent event) {
		Order order = new Order();
		order.setStatus(OrderStatus.PENDING);
		order.setUserId(event.getUserId());
		order.setTotalAmount(event.getTotalAmount());
		order.setPlacedAt(event.getPlacedAt());

		List<OrderItem> orderItems = event.getItems().stream().map((itemDto) -> {
			return mapper.map(itemDto, OrderItem.class);
		}).toList();

		order.setItems(orderItems);

		// Save order to DB, and return saved entity object
		return orderRepository.save(order);
	}

	private OrderDto toOrderDto(Order order) {
		return mapper.map(order, OrderDto.class);
	}

}
