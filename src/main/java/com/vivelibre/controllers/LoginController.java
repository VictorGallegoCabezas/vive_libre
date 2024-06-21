package com.vivelibre.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.vivelibre.model.TokenRequest;
import com.vivelibre.model.TokenResponse;

import reactor.core.publisher.Mono;

@RestController
public class LoginController {

	private WebClient client;

	@Value("${urlToken}")
	private String urlToken;

	@Autowired
	public LoginController(WebClient client) {
		this.client = client;
	}

	@GetMapping(value = "get-token", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<TokenResponse> getToken(@RequestBody TokenRequest request) {
		return client.post().uri(urlToken).contentType(MediaType.APPLICATION_JSON).bodyValue(request).retrieve()
				.bodyToMono(TokenResponse.class);
	}

}
