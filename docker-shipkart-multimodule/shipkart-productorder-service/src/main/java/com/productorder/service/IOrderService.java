package com.productorder.service;

import java.util.List;

import com.productorder.model.dtos.OrderDto;

public interface IOrderService {

	OrderDto getOrderByOrderId(int orderId);

	List<OrderDto> getOrdersByUserId(int userId);

}
