package com.cts.microservice.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auth {

	private long customerId;
	private String userid;
	private String username;
	private String password;
	private String authtoken;
	private String role;
	
}
