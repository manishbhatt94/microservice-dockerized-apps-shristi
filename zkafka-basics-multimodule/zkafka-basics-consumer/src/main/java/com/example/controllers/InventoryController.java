package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.IInventoryService;

@RestController
public class InventoryController {

	@Autowired
	private IInventoryService inventoryService;

	@GetMapping("/status")
	public String getInventoryStatus() {
		return inventoryService.getStatus();
	}

}
