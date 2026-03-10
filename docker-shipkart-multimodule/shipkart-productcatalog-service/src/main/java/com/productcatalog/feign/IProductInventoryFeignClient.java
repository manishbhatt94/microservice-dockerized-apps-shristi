package com.productcatalog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-inventory", url = "http://product-inventory:8083")
public interface IProductInventoryFeignClient {

	// http://localhost:8083/inventory-service/v1/inventories/add-stock?productId=1&stock=100
	@PostMapping("/inventory-service/v1/inventories/add-stock") // called by catalog
	String addStock(@RequestParam int productId, @RequestParam int stock);

}
