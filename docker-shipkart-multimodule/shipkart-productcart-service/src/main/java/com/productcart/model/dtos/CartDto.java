package com.productcart.model.dtos;

import java.util.ArrayList;
import java.util.List;

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
public class CartDto {

	@EqualsAndHashCode.Include
	private Integer cartId;

	private Integer userId;

	private List<CartItemDto> cartItems = new ArrayList<>();

	private double totalPrice;

}
