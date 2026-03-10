package com.productcart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.productcart.model.dtos.Product;

@FeignClient(name = "product-info", url = "http://product-info:8082")
public interface IProductInfoClient {

	// GET http://localhost:8082/info-service/v1/products/productId/1
	@GetMapping("/info-service/v1/products/productId/{productId}")
	Product viewById(@PathVariable int productId);

}
