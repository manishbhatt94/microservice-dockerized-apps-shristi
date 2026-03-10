package com.productinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productinfo.exception.ProductNotFoundException;
import com.productinfo.feign.IProductCatalogFeignClient;
import com.productinfo.model.Product;
import com.productinfo.model.enums.Delivery;
import com.productinfo.model.enums.OfferType;
import com.productinfo.model.enums.Payment;
import com.productinfo.service.IProductInfoService;

@Service
public class ProductInfoServiceFeignClientImpl implements IProductInfoService {

	@Autowired
	private IProductCatalogFeignClient feignClient;

	@Override
	public Product getById(int productId) throws ProductNotFoundException {
		// return feignClient.readById(productId).toString();
		return feignClient.readById(productId);
	}

	@Override
	public List<Product> getAll() {
		return feignClient.readAll();
	}

	@Override
	public List<Product> getByCategory(String category) throws ProductNotFoundException {
		return feignClient.readByCategory(category);
	}

	@Override
	public List<Product> getByBrandAndPayType(String brand, Payment paymentType) throws ProductNotFoundException {
		return feignClient.readByBrandAndPayType(brand, paymentType);
	}

	@Override
	public List<Product> getByColor(String color) throws ProductNotFoundException {
		return feignClient.readByColor(color);
	}

	@Override
	public List<Product> getByCategoryAndDelivery(String category, Delivery delivery) throws ProductNotFoundException {
		return feignClient.readByCategoryAndDelivery(category, delivery);
	}

	@Override
	public List<Product> getByNameContains(String name) throws ProductNotFoundException {
		return feignClient.readByNameContains(name);
	}

	@Override
	public List<Product> getByNameAndOfferType(String name, OfferType offerType) throws ProductNotFoundException {
		return feignClient.readByNameAndOfferType(name, offerType);
	}

}
