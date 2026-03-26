package com.productorder.service;

import java.util.List;

import com.productorder.model.dtos.OrderDto;
import com.productorder.model.entities.Order;
import com.sharedevents.models.OrderPlacedEvent;

public interface IOrderService {

	OrderDto getOrderByOrderId(int orderId);

	List<OrderDto> getOrdersByUserId(int userId);

	Order createOrder(OrderPlacedEvent event);

}
