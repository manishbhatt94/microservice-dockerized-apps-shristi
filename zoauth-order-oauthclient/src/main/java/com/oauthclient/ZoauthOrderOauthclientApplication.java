package com.oauthclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ZoauthOrderOauthclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZoauthOrderOauthclientApplication.class, args);
	}

}
