package com.example.service;

import com.example.model.Product;

public interface IOrderService {

	void sendStatus(String message);

	String placeOrder(Product product);

}
