package com.productorder.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.productorder.model.dtos.OrderDto;
import com.productorder.model.entities.Order;
import com.productorder.model.entities.OrderItem;
import com.productorder.model.enums.OrderStatus;
import com.productorder.repository.IOrderRepository;
import com.productorder.service.IOrderService;
import com.sharedevents.models.OrderPlacedEvent;
import com.sharedevents.models.PaymentResultEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

	private final IOrderRepository orderRepository;

	private final ModelMapper mapper;

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

	@Override
	public Order createOrder(OrderPlacedEvent event) {
		Order order = new Order();
		order.setStatus(OrderStatus.PENDING);
		order.setUserId(event.getUserId());
		order.setTotalAmount(event.getTotalAmount());
		order.setPaymentMethod(event.getPaymentMethod());
		order.setPlacedAt(event.getPlacedAt());

		List<OrderItem> orderItems = event.getItems().stream().map((itemDto) -> {
			return mapper.map(itemDto, OrderItem.class);
		}).toList();

		order.setItems(orderItems);

		// Save order to DB, and return saved entity object
		return orderRepository.save(order);
	}

	@Override
	public void handlePaymentResult(PaymentResultEvent event) {
		Order order = orderRepository.findById(event.getOrderId()).orElseThrow(
				() -> new RuntimeException(String.format("Order with ID: %d - Not Found", event.getOrderId())));

		order.setPaymentId(event.getPaymentId());
		order.setStatus(event.isSuccess() ? OrderStatus.PAID : OrderStatus.FAILED);
		order.setUpdatedAt(LocalDateTime.now());

		orderRepository.save(order);
	}

	private OrderDto toOrderDto(Order order) {
		return mapper.map(order, OrderDto.class);
	}

}
