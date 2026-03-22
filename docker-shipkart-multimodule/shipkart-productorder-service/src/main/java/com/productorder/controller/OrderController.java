package com.productorder.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productorder.model.dtos.OrderDto;
import com.productorder.service.IOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order-service/v1")
@RequiredArgsConstructor
public class OrderController {

	private final IOrderService orderService;

	// GET http://localhost:8085/order-service/v1/orders/1
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<OrderDto> getOrderByOrderId(@PathVariable int orderId) {
		OrderDto orderDto = orderService.getOrderByOrderId(orderId);
		return ResponseEntity.ok(orderDto);
	}

	// GET http://localhost:8085/order-service/v1/orders/user/1
	@GetMapping("/orders/user/{userId}")
	public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable int userId) {
		List<OrderDto> ordersDto = orderService.getOrdersByUserId(userId);
		return ResponseEntity.ok(ordersDto);
	}

}
