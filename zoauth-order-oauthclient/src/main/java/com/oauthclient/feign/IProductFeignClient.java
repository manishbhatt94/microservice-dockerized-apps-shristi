package com.oauthclient.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.oauthclient.config.OauthClientConfig;
import com.oauthclient.model.Product;

@FeignClient(name = "product-resource", url = "http://localhost:8095", configuration = OauthClientConfig.class)
public interface IProductFeignClient {

	@GetMapping("/products")
	public List<Product> getAll();

	@GetMapping("/products/productId/{productId}")
	public Product getById(@PathVariable int productId);

}
