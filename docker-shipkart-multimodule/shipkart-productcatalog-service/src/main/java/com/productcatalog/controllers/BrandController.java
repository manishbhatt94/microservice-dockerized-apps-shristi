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

import com.productcatalog.exception.BrandNotFoundException;
import com.productcatalog.model.dtos.BrandDto;
import com.productcatalog.service.IBrandService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/catalog-service/v1")
@RestController
public class BrandController {

	private final IBrandService brandService;

	// POST http://localhost:8081/catalog-service/v1/admin/brands
	@PostMapping("/admin/brands")
	ResponseEntity<Void> addBrand(@RequestBody BrandDto brandDto) {
		brandService.addBrand(brandDto);
		return ResponseEntity.status(HttpStatus.CREATED.value()).build();
	}

	// PUT http://localhost:8081/catalog-service/v1/admin/brands
	@PutMapping("/admin/brands")
	ResponseEntity<Void> updateBrand(@RequestBody BrandDto brandDto) {
		brandService.updateBrand(brandDto);
		return ResponseEntity.accepted().build();
	}

	// DELETE http://localhost:8081/catalog-service/v1/admin/brands/brandId/1
	@DeleteMapping("/admin/brands/brandId/{brandId}")
	ResponseEntity<Void> deleteBrand(@PathVariable int brandId) {
		brandService.deleteBrand(brandId);
		return ResponseEntity.ok().build();
	}

	// GET http://localhost:8081/catalog-service/v1/brands/brandId/1
	@GetMapping("/brands/brandId/{brandId}")
	ResponseEntity<BrandDto> getById(@PathVariable int brandId) throws BrandNotFoundException {
		BrandDto brandDto = brandService.getById(brandId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("info", "returns a brand by ID");
		headers.add("timestamp", LocalTime.now().toString());
		return new ResponseEntity<>(brandDto, headers, HttpStatusCode.valueOf(200));
	}

	// GET http://localhost:8081/catalog-service/v1/brands
	@GetMapping("/brands")
	ResponseEntity<List<BrandDto>> getAll() {
		List<BrandDto> brands = brandService.getAll();
		HttpHeaders headers = new HttpHeaders();
		headers.add("info", "returns a list of brands");
		headers.add("timestamp", LocalTime.now().toString());
		return ResponseEntity.status(HttpStatusCode.valueOf(200)).headers(headers).body(brands);
	}

}
