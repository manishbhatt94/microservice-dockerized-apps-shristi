package com.productcart.service;

import com.productcart.model.dtos.CartDto;

public interface ICartService {

	CartDto addToCart(int userId, int productId, int quantity);

	CartDto viewCart(int userId);

	String placeOrder(int userId);

	String clearCart(int userId);

}
