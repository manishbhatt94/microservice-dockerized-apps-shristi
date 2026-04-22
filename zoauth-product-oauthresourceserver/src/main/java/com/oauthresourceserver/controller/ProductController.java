package com.oauthresourceserver.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.oauthresourceserver.model.Product;

@RestController
public class ProductController {

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAll() {
		return ResponseEntity.ok(fetchProducts());
	}

	@GetMapping("/products/productId/{productId}")
	public ResponseEntity<Product> getById(@PathVariable int productId) {
		Product matched = fetchProducts().stream().filter(product -> product.getProductId().equals(productId))
				.findFirst().orElseThrow();
		return ResponseEntity.ok(matched);
	}

	private List<Product> fetchProducts() {
		return Arrays.asList(new Product(1, "Nike Jordans Relax Sneakers", 15999.0),
				new Product(2, "Skmei Digital Sports Wristwatch", 2520.0),
				new Product(3, "Ikea Spring Workdesk Standing Desk", 19500.0),
				new Product(4, "Yamaha YamaLube Chain Lube", 399.0),
				new Product(5, "Lizol Floor Cleaner Liquid Lavender - 10 Litre", 850.0),
				new Product(6, "Hawkins Venchura - Induction Cook Top - 2400 Watt", 3930.0),
				new Product(7, "Xiaomi Mi Band 8", 6250.0));
	}

}
