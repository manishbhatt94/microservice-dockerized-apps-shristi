package com.productcart.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productcart.model.dtos.AddToCartRequestDto;
import com.productcart.model.dtos.CartDto;
import com.productcart.service.ICartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart-service/v1")
@RequiredArgsConstructor
public class CartController {

	private final ICartService cartService;

	// POST
	// http://localhost:8084/cart-service/v1/cart/add?userId=1&productId=1&quantity=2
	@PostMapping("/cart/add")
	ResponseEntity<CartDto> addToCart(@RequestBody AddToCartRequestDto cartRequestDto) {
		CartDto cartDto = cartService.addToCart(cartRequestDto.getUserId(), cartRequestDto.getProductId(),
				cartRequestDto.getQuantity());
		return ResponseEntity.ok().body(cartDto);
	}

	// GET
	// http://localhost:8084/cart-service/v1/cart/view/userId/1
	@GetMapping("/cart/view/userId/{userId}")
	ResponseEntity<CartDto> viewCart(@PathVariable int userId) {
		CartDto cartDto = cartService.viewCart(userId);
		return ResponseEntity.ok(cartDto);
	}

	// POST
	// http://localhost:8084/cart-service/v1/cart/userId/1/place-order
	@PostMapping("/cart/userId/{userId}/place-order")
	ResponseEntity<String> placeOrder(@PathVariable int userId) {
		String message = cartService.placeOrder(userId);
		return ResponseEntity.ok(message);
	}

	// updateCart
	// removeFromCart

	// PUT
	// http://localhost:8084/cart-service/v1/cart/userId/1/clear-cart
	@PutMapping("/cart/userId/{userId}/clear-cart")
	ResponseEntity<String> clearCart(@PathVariable int userId) {
		String message = cartService.clearCart(userId);
		return ResponseEntity.ok(message);
	}

}
