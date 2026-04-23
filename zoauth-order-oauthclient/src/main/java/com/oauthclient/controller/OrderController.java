package com.oauthclient.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.oauthclient.feign.IProductFeignClient;
import com.oauthclient.model.Product;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final IProductFeignClient productFeign;

	// http://localhost:8096/place-order/3
	@GetMapping("/place-order/{productId}")
	Product placeOrder(@PathVariable Integer productId) {
		Product product = productFeign.getById(productId);
		return product;
	}

	// http://localhost:8096/view-products
	@GetMapping("/view-products")
	List<Product> viewAll() {
		return productFeign.getAll();
	}

}
