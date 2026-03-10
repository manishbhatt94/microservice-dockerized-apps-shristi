package com.productcatalog.controllers;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.productcatalog.exception.ProductNotFoundException;
import com.productcatalog.model.dtos.CreateProductDto;
import com.productcatalog.model.dtos.ProductDto;
import com.productcatalog.model.enums.Delivery;
import com.productcatalog.model.enums.OfferType;
import com.productcatalog.model.enums.Payment;
import com.productcatalog.service.IProductService;
import com.productcatalog.web.ICourses;

import lombok.RequiredArgsConstructor;

// Beans annotated with RefreshScope are lazily refreshed when we hit the actuator refresh end-point
// i.e. POST /actuator/refresh
// See: https://docs.spring.io/spring-cloud-commons/reference/spring-cloud-commons/application-context-services.html#refresh-scope
@RefreshScope
@RequiredArgsConstructor
@RequestMapping("/catalog-service/v1")
@RestController
public class ProductController {

	private final IProductService productService;

	private final ICourses courses;

	@Value("${message}")
	private String message;

	// http://localhost:8081/catalog-service/v1/show-message
	@GetMapping("/show-message")
	public String showMessage() {
		return message;
	}

	// http://localhost:8081/catalog-service/v1/show-courses
	@GetMapping("/show-courses")
	public List<String> showCourses() {
		return courses.getCourses();
	}

	// POST http://localhost:8081/catalog-service/v1/admin/products
	@PostMapping("/admin/products")
	ResponseEntity<Void> addProduct(@RequestBody CreateProductDto productDto) {
		productService.addProduct(productDto);
		return ResponseEntity.status(HttpStatus.CREATED.value()).build();
	}

	// PUT http://localhost:8081/catalog-service/v1/admin/products
	@PutMapping("/admin/products")
	ResponseEntity<Void> updateProduct(@RequestBody ProductDto productDto) {
		// productService.updateProduct(productDto); // Expects all fields
		productService.updateProductVerbose(productDto);
		return ResponseEntity.accepted().build();
	}

	// DELETE http://localhost:8081/catalog-service/v1/admin/products/productId/1
	@DeleteMapping("/admin/products/productId/{productId}")
	ResponseEntity<Void> deleteProduct(@PathVariable int productId) {
		productService.deleteProduct(productId);
		return ResponseEntity.ok().build();
	}

	// GET http://localhost:8081/catalog-service/v1/products/productId/1
	@GetMapping("/products/productId/{productId}")
	ResponseEntity<ProductDto> getById(@PathVariable int productId) throws ProductNotFoundException {
		ProductDto productDto = productService.getById(productId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("info", "returns a product by ID");
		headers.add("timestamp", LocalTime.now().toString());
		return new ResponseEntity<>(productDto, headers, HttpStatusCode.valueOf(200));
	}

	// GET http://localhost:8081/catalog-service/v1/products
	@GetMapping("/products")
	ResponseEntity<List<ProductDto>> getAll() {
		List<ProductDto> products = productService.getAll();
		HttpHeaders headers = new HttpHeaders();
		headers.add("info", "returns a list of products");
		headers.add("timestamp", LocalTime.now().toString());
		return ResponseEntity.status(HttpStatusCode.valueOf(200)).headers(headers).body(products);
	}

	// GET
	// http://localhost:8081/catalog-service/v1/products/category?categoryname=sports
	@GetMapping("/products/category")
	ResponseEntity<List<ProductDto>> getByCategory(@RequestParam String categoryname) {
		List<ProductDto> products = productService.getByCategory(categoryname);
		return ResponseEntity.ok(products);
	}

	// GET
	// http://localhost:8081/catalog-service/v1/products/brand/Samsung/payment/UPI
	@GetMapping("/products/brand/{brand}/payment/{payment}")
	ResponseEntity<List<ProductDto>> getByBrandAndPayType(@PathVariable String brand,
			@PathVariable("payment") Payment paymentType) {
		List<ProductDto> products = productService.getByBrandAndPayType(brand, paymentType);
		return ResponseEntity.ok(products);
	}

	// GET
	// http://localhost:8081/catalog-service/v1/products/color/Thunderstorm%20Violet
	@GetMapping("/products/color/{color}")
	ResponseEntity<List<ProductDto>> getByColor(@PathVariable String color) {
		List<ProductDto> products = productService.getByColor(color);
		return ResponseEntity.ok(products);
	}

	// GET
	// http://localhost:8081/catalog-service/v1/products/category/Sports/delivery/STANDARD
	@GetMapping("/products/category/{category}/delivery/{delivery}")
	ResponseEntity<List<ProductDto>> getByCategoryAndDelivery(@PathVariable String category,
			@PathVariable Delivery delivery) {
		List<ProductDto> products = productService.getByCategoryAndDelivery(category, delivery);
		return ResponseEntity.ok(products);
	}

	// GET http://localhost:8081/catalog-service/v1/products/name/ball
	@GetMapping("/products/name/{name}")
	ResponseEntity<List<ProductDto>> getByNameContains(@PathVariable("name") String partialName) {
		List<ProductDto> products = productService.getByNameContains(partialName);
		return ResponseEntity.ok(products);
	}

	// GET
	// http://localhost:8081/catalog-service/v1/products/name/ball/offer/BANK%20OFFER
	@GetMapping("/products/name/{name}/offer/{offer}")
	ResponseEntity<List<ProductDto>> getByNameAndOfferType(@PathVariable("name") String partialName,
			@PathVariable("offer") OfferType offerType) {
		List<ProductDto> products = productService.getByNameAndOfferType(partialName, offerType);
		return ResponseEntity.ok(products);
	}

}
