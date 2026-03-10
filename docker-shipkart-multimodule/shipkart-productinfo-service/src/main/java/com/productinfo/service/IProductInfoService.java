package com.productinfo.service;

import java.util.List;

import com.productinfo.exception.ProductNotFoundException;
import com.productinfo.model.Product;
import com.productinfo.model.enums.Delivery;
import com.productinfo.model.enums.OfferType;
import com.productinfo.model.enums.Payment;

public interface IProductInfoService {

	Product getById(int productId) throws ProductNotFoundException;

	List<Product> getAll();

	// Other filtering:

	List<Product> getByCategory(String category) throws ProductNotFoundException;

	List<Product> getByBrandAndPayType(String brand, Payment paymentType) throws ProductNotFoundException;

	List<Product> getByColor(String color) throws ProductNotFoundException;

	List<Product> getByCategoryAndDelivery(String category, Delivery delivery) throws ProductNotFoundException;

	List<Product> getByNameContains(String name) throws ProductNotFoundException;

	List<Product> getByNameAndOfferType(String name, OfferType offerType) throws ProductNotFoundException;

}
