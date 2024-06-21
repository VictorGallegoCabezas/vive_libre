package com.vivelibre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ViveLibreBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViveLibreBackendApplication.class, args);
	}
	
	@Bean
	public WebClient getWebClient(){
		return WebClient.create();
	}


}
