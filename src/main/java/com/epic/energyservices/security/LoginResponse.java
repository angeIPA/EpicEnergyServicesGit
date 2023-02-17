package com.epic.energyservices.security;


import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResponse {
	// Token
//	private String token;	
	// Imposta il prefisso che indica il tipo di Token
	private String type = "Bearer";
	// Dati dell'utente
	private int id;
	private String username;
	private String email;
	private List<String> roles;
	private Date expirationTime;
	
	public LoginResponse(String type, int id, String email, List<String> roles, Date expirationTime) {
		super();
		this.type = type;
		this.id = id;
		this.email = email;
		this.roles = roles;
		this.expirationTime = expirationTime;
	}
}
