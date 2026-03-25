package com.productcart.service;

import com.productcart.model.dtos.CartDto;
import com.productcart.model.dtos.CheckoutRequestDto;

public interface ICartService {

	CartDto addToCart(int userId, int productId, int quantity);

	CartDto viewCart(int userId);

	String placeOrder(int userId, CheckoutRequestDto checkoutRequestBody);

	String clearCart(int userId);

}
