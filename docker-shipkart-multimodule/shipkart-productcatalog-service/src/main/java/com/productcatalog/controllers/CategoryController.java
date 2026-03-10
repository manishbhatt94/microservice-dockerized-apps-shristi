package com.productcatalog.controllers;

import java.time.LocalTime;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.productcatalog.exception.CategoryNotFoundException;
import com.productcatalog.model.dtos.CategoryDto;
import com.productcatalog.service.ICategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/catalog-service/v1")
@RestController
public class CategoryController {

	private final ICategoryService categoryService;

	// POST http://localhost:8081/catalog-service/v1/admin/categories
	@PostMapping("/admin/categories")
	ResponseEntity<Void> addCategory(@RequestBody CategoryDto categoryDto) {
		categoryService.addCategory(categoryDto);
		return ResponseEntity.status(HttpStatus.CREATED.value()).build();
	}

	// PUT http://localhost:8081/catalog-service/v1/admin/categories
	@PutMapping("/admin/categories")
	ResponseEntity<Void> updateCategory(@RequestBody CategoryDto categoryDto) {
		categoryService.updateCategory(categoryDto);
		return ResponseEntity.accepted().build();
	}

	// DELETE http://localhost:8081/catalog-service/v1/admin/categories/categoryId/1
	@DeleteMapping("/admin/categories/categoryId/{categoryId}")
	ResponseEntity<Void> deleteCategory(@PathVariable int categoryId) {
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok().build();
	}

	// GET http://localhost:8081/catalog-service/v1/categories/categoryId/1
	@GetMapping("/categories/categoryId/{categoryId}")
	ResponseEntity<CategoryDto> getById(@PathVariable int categoryId) throws CategoryNotFoundException {
		CategoryDto categoryDto = categoryService.getById(categoryId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("info", "returns a category by ID");
		headers.add("timestamp", LocalTime.now().toString());
		return new ResponseEntity<>(categoryDto, headers, HttpStatusCode.valueOf(200));
	}

	// GET http://localhost:8081/catalog-service/v1/categories
	@GetMapping("/categories")
	ResponseEntity<List<CategoryDto>> getAll() {
		List<CategoryDto> categories = categoryService.getAll();
		HttpHeaders headers = new HttpHeaders();
		headers.add("info", "returns a list of categories");
		headers.add("timestamp", LocalTime.now().toString());
		return ResponseEntity.status(HttpStatusCode.valueOf(200)).headers(headers).body(categories);
	}

}
