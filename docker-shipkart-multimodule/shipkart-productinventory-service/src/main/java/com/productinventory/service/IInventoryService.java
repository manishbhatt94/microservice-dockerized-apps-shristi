package com.productinventory.service;

public interface IInventoryService {

	void addStock(int productId, int stock);

	void updateStock(int productId, int stock, String type);

	int checkStock(int productId);

}
