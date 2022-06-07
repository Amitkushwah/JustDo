package com.cognizant.retailbank.apigateway.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Utility Class for Role Management
 */
public class RoleUtil {

	private final static List<String> empAllowedPath = new ArrayList<>(
			Arrays.asList("/customer-service/customer/createCustomer", "/customer-service/customer/getCustomerDetails","/customer-service/customer/getall"));
	private final static List<String> customeAllowedPath = new ArrayList<>(
			Arrays.asList("/customer-service/customer/getCustomerDetails", "/account-service/account/get_cust_acc",
					"/account-service/account/get_acc", "/account-service/account/deposit", "/account-service/account/withdraw",
					"/account-service/account/account-statement", "/account-service/account/transfer"));
	private static Map<String, List<String>> roles = new HashMap<>();
	
	public static Map<String, List<String>> getRoles(){
		roles.put("CUSTOMER", customeAllowedPath);
		roles.put("EMPLOYEE", empAllowedPath);
		return roles;
	}

}
