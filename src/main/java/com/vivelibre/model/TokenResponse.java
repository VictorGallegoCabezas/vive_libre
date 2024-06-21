package com.vivelibre.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenResponse {
	
	@JsonAlias("auth-vivelibre-token")
	private String token;
	
	private String date;	

}
