package com.productinfo.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.productinfo.exception.ProductNotFoundException;
import com.productinfo.model.Product;
import com.productinfo.model.enums.Delivery;
import com.productinfo.model.enums.OfferType;
import com.productinfo.model.enums.Payment;

@FeignClient(name = "product-catalog")
public interface IProductCatalogFeignClient {

	// GET http://localhost:8081/catalog-service/v1/products/productId/1
	@GetMapping("/catalog-service/v1/products/productId/{productId}")
	Product readById(@PathVariable int productId) throws ProductNotFoundException;

	// GET http://localhost:8081/catalog-service/v1/products
	@GetMapping("/catalog-service/v1/products")
	List<Product> readAll();

	// GET
	// http://localhost:8081/catalog-service/v1/products/category?categoryname=sports
	@GetMapping("/catalog-service/v1/products/category")
	List<Product> readByCategory(@RequestParam String categoryname);

	// GET
	// http://localhost:8081/catalog-service/v1/products/brand/Samsung/payment/UPI
	@GetMapping("/catalog-service/v1/products/brand/{brand}/payment/{payment}")
	List<Product> readByBrandAndPayType(@PathVariable String brand,
			@PathVariable("payment") Payment paymentType);

	// GET
	// http://localhost:8081/catalog-service/v1/products/color/Thunderstorm%20Violet
	@GetMapping("/catalog-service/v1/products/color/{color}")
	List<Product> readByColor(@PathVariable String color);

	// GET
	// http://localhost:8081/catalog-service/v1/products/category/Sports/delivery/STANDARD
	@GetMapping("/catalog-service/v1/products/category/{category}/delivery/{delivery}")
	List<Product> readByCategoryAndDelivery(@PathVariable String category,
			@PathVariable Delivery delivery);

	// GET http://localhost:8081/catalog-service/v1/products/name/ball
	@GetMapping("/catalog-service/v1/products/name/{name}")
	List<Product> readByNameContains(@PathVariable("name") String partialName);

	// GET
	// http://localhost:8081/catalog-service/v1/products/name/ball/offer/BANK%20OFFER
	@GetMapping("/catalog-service/v1/products/name/{name}/offer/{offer}")
	List<Product> readByNameAndOfferType(@PathVariable("name") String partialName,
			@PathVariable("offer") OfferType offerType);

}
