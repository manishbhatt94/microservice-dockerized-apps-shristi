package com.authserver.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
		// @formatter:off
		http
			.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
			.with(authorizationServerConfigurer, Customizer.withDefaults())
			.authorizeHttpRequests((authorize) -> {
				authorize.anyRequest().authenticated();
			});
		// @formatter:on
		return http.build();
	}

	@Bean
	RegisteredClientRepository clientRegistrationRepository() {
		return new InMemoryRegisteredClientRepository(clientRegistration());
	}

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	private RegisteredClient clientRegistration() {
		System.out.println("Client Secrent (encoded): " + encoder().encode("order-client-secret"));
		// @formatter:off
		RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("order-client")
				.clientSecret(encoder().encode("order-client-secret"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.scope("openid")
				.build();
		// @formatter:on
		System.out.println("AuthorizationServerConfig::clientRegistration - RegisteredClient = " + client);
		return client;
	}

}
