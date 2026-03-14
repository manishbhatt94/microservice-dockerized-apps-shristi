package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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


}
