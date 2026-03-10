package com.productcatalog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.productcatalog.web.Backend;
import com.productcatalog.web.Frontend;
import com.productcatalog.web.ICourses;

@Configuration
public class AppConfig {

	@Bean
	ModelMapper mapper() {
		return new ModelMapper();
	}

	@Bean
	@Profile(value = { "default", "dev" })
	ICourses getBackend() {
		return new Backend();
	}

	@Bean
	@Profile("prod")
	ICourses getFrontend() {
		return new Frontend();
	}

}
