package com.cognizant.retailbank.apigateway.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * DTO Class for response from Auth Service
 */

@Data
@NoArgsConstructor
public class AuthServiceDto {
	private String userid;
	private String name;
	private boolean isValid;
	private String role;
	private String message;
	private String error;
	private String path;
	private int status;
	private String timestamp;
}
