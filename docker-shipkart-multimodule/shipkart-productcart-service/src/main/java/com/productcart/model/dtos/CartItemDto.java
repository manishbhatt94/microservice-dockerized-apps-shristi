package com.productcart.model.dtos;

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
public class CartItemDto {

	@EqualsAndHashCode.Include
	private Integer cartItemId;

	private Integer productId;

	private String productName;

	private int quantity;

	private double price;

}
