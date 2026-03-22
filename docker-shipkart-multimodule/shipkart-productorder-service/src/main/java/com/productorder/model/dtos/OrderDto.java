package com.productorder.model.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.productorder.model.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderDto {

	@EqualsAndHashCode.Include
	private Integer orderId;

	private Integer userId;

	private double totalAmount;

	private OrderStatus status;

	private LocalDateTime placedAt;

	private LocalDateTime updatedAt;

	private List<OrderItemDto> items = new ArrayList<>();

}
