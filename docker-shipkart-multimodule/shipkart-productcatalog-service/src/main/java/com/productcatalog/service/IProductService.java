package com.productcatalog.service;

import java.util.List;

import com.productcatalog.exception.ProductNotFoundException;
import com.productcatalog.model.dtos.CreateProductDto;
import com.productcatalog.model.dtos.ProductDto;
import com.productcatalog.model.enums.Delivery;
import com.productcatalog.model.enums.OfferType;
import com.productcatalog.model.enums.Payment;

public interface IProductService {

	// CRUD operations:

	void addProduct(CreateProductDto productDto);

	void updateProduct(ProductDto productDto);

	void updateProductVerbose(ProductDto productDto);

	void deleteProduct(int productId);

	ProductDto getById(int productId) throws ProductNotFoundException;

	List<ProductDto> getAll();

	// Other filtering:

	List<ProductDto> getByCategory(String category) throws ProductNotFoundException;

	List<ProductDto> getByBrandAndPayType(String brand, Payment paymentType) throws ProductNotFoundException;

	List<ProductDto> getByColor(String color) throws ProductNotFoundException;

	List<ProductDto> getByCategoryAndDelivery(String category, Delivery delivery) throws ProductNotFoundException;

	List<ProductDto> getByNameContains(String name) throws ProductNotFoundException;

	List<ProductDto> getByNameAndOfferType(String name, OfferType offerType) throws ProductNotFoundException;

}
