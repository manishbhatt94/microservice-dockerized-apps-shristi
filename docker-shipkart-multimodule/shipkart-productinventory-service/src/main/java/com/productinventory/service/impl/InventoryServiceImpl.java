package com.productinventory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productinventory.model.entities.Inventory;
import com.productinventory.repository.InventoryRepository;
import com.productinventory.service.IInventoryService;

@Service
public class InventoryServiceImpl implements IInventoryService {

	@Autowired
	private InventoryRepository repository;

	@Override
	public void addStock(int productId, int stock) {
		Inventory inventory = new Inventory(null, productId, stock);
		repository.save(inventory);
	}

	@Override
	public void updateStock(int productId, int stock, String type) {
		Inventory inventory = repository
				.findByProductId(productId)
				.orElseThrow(() -> new RuntimeException("invalid productId"));
		int existingstock = inventory.getStock(); // get the old stock
		if (type.equals("increment")) {
			inventory.setStock(existingstock + stock); // add to the new stock
		} else if (type.equals("decrement")) {
			inventory.setStock(existingstock - stock); // add to the new stock
		}
		repository.save(inventory);
	}

	@Override
	public int checkStock(int productId) {
		Inventory inventory = repository
				.findByProductId(productId)
				.orElseThrow(() -> new RuntimeException("invalid productId"));
		return inventory.getStock();
	}

}
