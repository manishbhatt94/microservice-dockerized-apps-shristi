package com.productinfo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productinfo.model.Product;
import com.productinfo.model.enums.Delivery;
import com.productinfo.model.enums.OfferType;
import com.productinfo.model.enums.Payment;
import com.productinfo.service.IProductInfoService;

@RestController
@RequestMapping("/info-service/v1")
public class ProductInfoController {

	// @Qualifier("productInfoServiceRestClientImpl")
	// @Qualifier("productInfoServiceFeignClientImpl")
	@Qualifier("productInfoServiceRCResilienceImpl")
	@Autowired
	private IProductInfoService productInfoService;

	// GET http://localhost:8082/info-service/v1/products/productId/1
	@GetMapping("/products/productId/{productId}")
	ResponseEntity<Product> viewById(@PathVariable int productId) {
		// String product = productInfoService.getById(productId);
		Product product = productInfoService.getById(productId);
		return ResponseEntity.ok(product);
	}

	// GET http://localhost:8082/info-service/v1/products
	@GetMapping("/products")
	ResponseEntity<List<Product>> getAll() {
		List<Product> products = productInfoService.getAll();
		return ResponseEntity.ok(products);
	}

	// GET
	// http://localhost:8082/info-service/v1/products/category/Sports
	@GetMapping("/products/category/{category}")
	ResponseEntity<List<Product>> viewByCategory(@PathVariable String category) {
		List<Product> products = productInfoService.getByCategory(category);
		return ResponseEntity.ok(products);
	}

	// GET
	// http://localhost:8082/info-service/v1/products/brand/Samsung/payment/UPI
	@GetMapping("/products/brand/{brand}/payment/{payment}")
	ResponseEntity<List<Product>> viewByBrandAndPayType(@PathVariable String brand,
			@PathVariable("payment") Payment paymentType) {
		List<Product> products = productInfoService.getByBrandAndPayType(brand, paymentType);
		return ResponseEntity.ok(products);
	}

	// GET
	// http://localhost:8082/info-service/v1/products/color/Thunderstorm%20Violet
	@GetMapping("/products/color/{color}")
	ResponseEntity<List<Product>> viewByColor(@PathVariable String color) {
		List<Product> products = productInfoService.getByColor(color);
		return ResponseEntity.ok(products);
	}

	// GET
	// http://localhost:8082/info-service/v1/products/category/Sports/delivery/STANDARD
	@GetMapping("/products/category/{category}/delivery/{delivery}")
	ResponseEntity<List<Product>> viewByCategoryAndDelivery(@PathVariable String category,
			@PathVariable Delivery delivery) {
		List<Product> products = productInfoService.getByCategoryAndDelivery(category, delivery);
		return ResponseEntity.ok(products);
	}

	// GET http://localhost:8082/info-service/v1/products/name/ball
	@GetMapping("/products/name/{name}")
	ResponseEntity<List<Product>> viewByNameContains(@PathVariable("name") String partialName) {
		List<Product> products = productInfoService.getByNameContains(partialName);
		return ResponseEntity.ok(products);
	}

	// GET
	// http://localhost:8082/info-service/v1/products/name/ball/offer/BANK%20OFFER
	@GetMapping("/products/name/{name}/offer/{offer}")
	ResponseEntity<List<Product>> viewByNameAndOfferType(@PathVariable("name") String partialName,
			@PathVariable("offer") OfferType offerType) {
		List<Product> products = productInfoService.getByNameAndOfferType(partialName, offerType);
		return ResponseEntity.ok(products);
	}

}
