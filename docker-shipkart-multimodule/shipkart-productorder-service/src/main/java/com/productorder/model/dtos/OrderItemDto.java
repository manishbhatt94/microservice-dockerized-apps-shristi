package com.productorder.model.dtos;

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
public class OrderItemDto {

	@EqualsAndHashCode.Include
	private Integer orderItemId;

	private Integer productId;

	private String productName; // snapshot from event

	private int quantity;

	private double priceAtPurchase; // snapshot from event

}
