package com.productinfo.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

	// Spring Cloud auto-configures Eureka to use RestClient for its HTTP
	// communication.
	// When it finds a RestClient.Builder bean in the context, it uses it —
	// but if that bean is @LoadBalanced, every HTTP call
	// including http://localhost:8761/eureka gets routed through the load balancer,
	// which looks up localhost as a service name in Eureka — which doesn't exist.

	// 1. Eureka-Client and External APIs use this RestClient.Builder bean
	// (No Load Balancing) using standard DNS.
	// Marked as Primary so that Eureka-Client picks up this RestClient.Builder bean
	// for connecting to Eureka-Server (i.e. to http://localhost:8761)
	@Bean
	@Primary
	RestClient.Builder standardRestClientBuilder() {
		return RestClient.builder();
	}

	// 2. Our application uses this RestClient.Builder bean for inter-service
	// communication to services registered on Eureka-Registry like
	// 'product-catalog' (With Load Balancing using logical host-names like
	// http://product-cataglog/ instead of actual physical host-names)
	@Bean
	@LoadBalanced
	RestClient.Builder loadBalancedRestClientBuilder() {
		return RestClient.builder();
	}

}
