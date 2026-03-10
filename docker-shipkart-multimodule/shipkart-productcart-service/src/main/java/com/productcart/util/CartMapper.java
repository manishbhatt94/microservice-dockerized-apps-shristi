package com.productcart.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.productcart.model.dtos.CartDto;
import com.productcart.model.entities.Cart;

@Component
public class CartMapper {

	@Autowired
	private ModelMapper mapper;

	public CartDto toCartDto(Cart cart) {
		return mapper.map(cart, CartDto.class);
	}

}
