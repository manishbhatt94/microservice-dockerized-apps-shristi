package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Product;
import com.example.service.IOrderService;

@RestController
public class OrderController {

	@Autowired
	private IOrderService orderService;

	@GetMapping("/status/{message}")
	public String sendMessage(@PathVariable String message) {
		orderService.sendStatus(message);
		return "message sent";
	}

	@PostMapping("/place-order")
	String placeOrder(@RequestBody Product product) {
		return orderService.placeOrder(product);
	}

}
