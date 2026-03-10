package com.productinfo.service.impl;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.productinfo.exception.ProductNotFoundException;
import com.productinfo.model.Product;
import com.productinfo.model.enums.Delivery;
import com.productinfo.model.enums.OfferType;
import com.productinfo.model.enums.Payment;
import com.productinfo.service.IProductInfoService;

@Service
public class ProductInfoServiceRestClientImpl implements IProductInfoService {

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	@Autowired
	private DiscoveryClient discoveryClient;

	private final RestClient restClient;

	// Use @Qualifier to specify the LoadBalanced version
	public ProductInfoServiceRestClientImpl(@Qualifier("loadBalancedRestClientBuilder") RestClient.Builder builder) {
		// Now 'builder' is the one with the LoadBalancer intercepter.
		// The service name 'product-catalog' is used as the base URL.
		this.restClient = builder.baseUrl("http://product-catalog/catalog-service/v1").build();
	}

	@Override
	public Product getById(int productId) throws ProductNotFoundException {
		@Nullable
		Product product = restClient
				.get()
				.uri("/products/productId/{productId}", productId)
				.retrieve()
				.body(Product.class);
		logInstanceInfo_DiscoveryClient();
		// return product.toString();
		return product;
	}

	@Override
	public List<Product> getAll() {
		ParameterizedTypeReference<List<Product>> bodyType = new ParameterizedTypeReference<List<Product>>() {
		};
		@Nullable
		List<Product> products = restClient
				.get()
				.uri("/products")
				.retrieve()
				.body(bodyType);
		logInstanceInfo_LoadBalancerClient();
		return products;
	}

	@Override
	public List<Product> getByCategory(String category) throws ProductNotFoundException {
		ParameterizedTypeReference<List<Product>> bodyType = new ParameterizedTypeReference<List<Product>>() {
		};
		ResponseEntity<List<Product>> response = restClient
				.get()
				.uri("/products/category?categoryname={category}", category)
				.retrieve()
				.toEntity(bodyType);
		return response.getBody();
	}

	@Override
	public List<Product> getByBrandAndPayType(String brand, Payment paymentType) throws ProductNotFoundException {
		ParameterizedTypeReference<List<Product>> bodyType = new ParameterizedTypeReference<List<Product>>() {
		};
		ResponseEntity<List<Product>> response = restClient
				.get()
				.uri("/products/brand/{brand}/payment/{payment}", brand, paymentType)
				.retrieve()
				.toEntity(bodyType);
		return response.getBody();
	}

	@Override
	public List<Product> getByColor(String color) throws ProductNotFoundException {
		ParameterizedTypeReference<List<Product>> bodyType = new ParameterizedTypeReference<List<Product>>() {
		};
		ResponseEntity<List<Product>> response = restClient
				.get()
				.uri("/products/color/{color}", color)
				.retrieve()
				.toEntity(bodyType);
		return response.getBody();
	}

	@Override
	public List<Product> getByCategoryAndDelivery(String category, Delivery delivery) throws ProductNotFoundException {
		ParameterizedTypeReference<List<Product>> bodyType = new ParameterizedTypeReference<List<Product>>() {
		};
		ResponseEntity<List<Product>> response = restClient
				.get()
				.uri("/products/category/{category}/delivery/{delivery}", category, delivery)
				.retrieve()
				.toEntity(bodyType);
		return response.getBody();
	}

	@Override
	public List<Product> getByNameContains(String name) throws ProductNotFoundException {
		ParameterizedTypeReference<List<Product>> bodyType = new ParameterizedTypeReference<List<Product>>() {
		};
		ResponseEntity<List<Product>> response = restClient
				.get()
				.uri("/products/name/{name}", name)
				.retrieve()
				.toEntity(bodyType);
		return response.getBody();
	}

	@Override
	public List<Product> getByNameAndOfferType(String name, OfferType offerType) throws ProductNotFoundException {
		ParameterizedTypeReference<List<Product>> bodyType = new ParameterizedTypeReference<List<Product>>() {
		};
		ResponseEntity<List<Product>> response = restClient
				.get()
				.uri("/products/name/{name}/offer/{offer}", name, offerType)
				.retrieve()
				.toEntity(bodyType);
		return response.getBody();
	}

	private void logInstanceInfo_DiscoveryClient() {
		List<ServiceInstance> serviceInstances = discoveryClient.getInstances("product-catalog");
		serviceInstances.forEach(instance -> {
			System.out.println(".........details..........");
			System.out.println("port " + instance.getPort());
			System.out.println("scheme " + instance.getScheme());
			System.out.println("serviceId " + instance.getServiceId());
			System.out.println("instanceId " + instance.getInstanceId());
			System.out.println("host " + instance.getHost());
			System.out.println("metadata " + instance.getMetadata());
		});
	}

	private void logInstanceInfo_LoadBalancerClient() {
		ServiceInstance instance = loadBalancerClient.choose("product-catalog");
		System.out.println(".........details..........");
		System.out.println("port " + instance.getPort());
		System.out.println("scheme " + instance.getScheme());
		System.out.println("serviceId" + instance.getServiceId());
		System.out.println("instanceId " + instance.getInstanceId());
		System.out.println("host " + instance.getHost());
		System.out.println("metadata " + instance.getMetadata());
	}

}
