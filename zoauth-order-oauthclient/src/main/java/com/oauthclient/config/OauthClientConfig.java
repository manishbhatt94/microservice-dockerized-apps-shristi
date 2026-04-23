package com.oauthclient.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

import feign.RequestInterceptor;

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

	@Bean
	OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository) {

		OAuth2AuthorizedClientService clientService = new InMemoryOAuth2AuthorizedClientService(
				clientRegistrationRepository);

		AuthorizedClientServiceOAuth2AuthorizedClientManager manager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
				clientRegistrationRepository, clientService);

		manager.setAuthorizedClientProvider(
				OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build());

		return manager;
	}

	@Bean
	RequestInterceptor oauth2FeignInterceptor(OAuth2AuthorizedClientManager authorizedClientManager) {
		return (requestTemplate) -> {
			// @formatter:off
			OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
					.withClientRegistrationId("order-client")
					.principal(new UsernamePasswordAuthenticationToken("order-client", null, List.of()))
					.build();
			OAuth2AuthorizedClient client = authorizedClientManager.authorize(request);
			if (client != null) {
				requestTemplate.header("Authorization",
						"Bearer " + client.getAccessToken().getTokenValue());
			}
			// @formatter:on
		};
	}

}
