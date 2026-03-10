package com.productinventory.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.productinventory.service.IInventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/inventory-service/v1")
@RequiredArgsConstructor
public class InventoryController {

	private final IInventoryService inventoryService;

	// http://localhost:8083/inventory-service/v1/inventories/add-stock?productId=1&stock=100
	@PostMapping("/inventories/add-stock") // called by catalog
	ResponseEntity<String> addStock(@RequestParam int productId, @RequestParam int stock) {
		inventoryService.addStock(productId, stock);
		return ResponseEntity.ok("added");
	}

	// http://localhost:8083/inventory-service/v1/inventories/update-stock?productId=1&stock=100&type=increment
	// http://localhost:8083/inventory-service/v1/inventories/update-stock?productId=1&stock=100&type=decrement
	@PutMapping("/inventories/update-stock") // called by admin from panel
	ResponseEntity<String> updateStock(@RequestParam int productId, @RequestParam int stock,
			@RequestParam String type) {
		inventoryService.updateStock(productId, stock, type);
		return ResponseEntity.ok("updated");
	}

	// http://localhost:8083/inventory-service/v1/inventories/check-stock/productId/1
	@GetMapping("/inventories/check-stock/productId/{productId}")
	ResponseEntity<Integer> checkStock(@PathVariable int productId) {
		int stock = inventoryService.checkStock(productId);
		return ResponseEntity.ok(stock);
	}

}
