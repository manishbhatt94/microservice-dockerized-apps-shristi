package com.oauthclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class OauthClientConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests((auth) -> { 
				auth
					.requestMatchers("/view-products", "/place-order/**").permitAll()
					.anyRequest().authenticated();
			});
		return http.build();
		// @formatter:on
	}

}
