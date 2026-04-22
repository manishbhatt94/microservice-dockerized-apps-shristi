package com.authserver.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfig {

//	// Backup: Old Code
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
//		http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
//				.with(authorizationServerConfigurer, Customizer.withDefaults())
//				.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());
//		return http.build();
//	}

	@Bean
	@Order(1)
	SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.oauth2AuthorizationServer((authorizationServer) -> {
				http.securityMatcher(authorizationServer.getEndpointsMatcher());
				authorizationServer.oidc(Customizer.withDefaults()); // Enable OpenID Connect 1.0 ??
			})
			.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
			.exceptionHandling((exceptions) -> {
				exceptions.defaultAuthenticationEntryPointFor(new LoginUrlAuthenticationEntryPoint("/login"),
					new MediaTypeRequestMatcher(MediaType.TEXT_HTML));
			});
		// @formatter:on
		return http.build();
	}

	@Bean
	@Order(2)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
			.formLogin(Customizer.withDefaults());
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
		// @formatter:off
		RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("order-client")
				.clientSecret(encoder().encode("order-client-secret"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.scope("read")
				.build();
		// @formatter:on
		System.out.println("AuthorizationServerConfig::clientRegistration - RegisteredClient = " + client);
		return client;
	}

}
