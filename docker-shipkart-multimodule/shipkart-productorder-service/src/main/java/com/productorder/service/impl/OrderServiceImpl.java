package com.productorder.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.productorder.model.dtos.OrderDto;
import com.productorder.model.entities.Order;
import com.productorder.repository.IOrderRepository;
import com.productorder.service.IOrderService;

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

	private OrderDto toOrderDto(Order order) {
		return mapper.map(order, OrderDto.class);
	}

}
