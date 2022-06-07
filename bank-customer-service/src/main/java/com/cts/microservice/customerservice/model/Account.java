package com.cts.microservice.customerservice.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {

	private long customerId;
	private String accountType;
	private String accHolderName;
	
}
