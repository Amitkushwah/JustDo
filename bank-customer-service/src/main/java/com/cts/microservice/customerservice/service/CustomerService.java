 package com.cts.microservice.customerservice.service;

import java.util.ArrayList;
import java.util.List;

import javax.management.ServiceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.microservice.customerservice.exception.CustomerNotFoundException;
import com.cts.microservice.customerservice.model.Account;
import com.cts.microservice.customerservice.model.AccountCreationStatus;
import com.cts.microservice.customerservice.model.Auth;
import com.cts.microservice.customerservice.model.Customer;
import com.cts.microservice.customerservice.repository.CustomerRepository;
import com.cts.microservice.customerservice.util.CustomerInput;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service

public class CustomerService {

	@Autowired
	CustomerRepository repository;
	
	@Autowired
	RestTemplate rest;
	
	@Autowired
	Account account;
	
	@Value("${auth-service.url}")
	private String authBaseUrl;
	
	@Value("${account-service.url}")
	private String accountBaseUrl;
	
	
	 public AccountCreationStatus createCustomer(final CustomerInput customerinput) throws ServiceNotFoundException {
	     Customer cust = new Customer();
	     cust.setCustomerName(customerinput.getName());
	     cust.setAddress(customerinput.getAddress());
	     cust.setDob(customerinput.getDob());
	     cust.setPanNo(customerinput.getPanNo());
	     Customer result = repository.saveAndFlush(cust);
	     
	     //account service status check setter
	     account.setCustomerId(result.getCustomerId());
	     account.setAccountType(customerinput.getAccountType());
	     account.setAccHolderName(result.getCustomerName());
	     
	     //auth service status check setter
	     Auth auth = new Auth();
	     auth.setUserid(customerinput.getUsername());
	     auth.setPassword(customerinput.getPassword());
	     auth.setRole("CUSTOMER");
	     auth.setUsername(customerinput.getName());
	     auth.setCustomerId(result.getCustomerId());
	     
	     //response from auth-service
	     ResponseEntity<Auth> authresult = rest.postForEntity(authBaseUrl+"/auth-ms/createUser", auth, Auth.class);
	     log.info("Checking up this portion {}",authresult);
	     if(authresult.getStatusCode().is2xxSuccessful()) {
	    	  //response from account-service
	    	  return rest.postForObject(accountBaseUrl+"/account/create-account", account, AccountCreationStatus.class);
	     }
	     else {
	    	throw new ServiceNotFoundException("Service not available");
	     }  
	    }
	 
	    // Get all students from the h2 database.
	    public List<Customer> getAll() {
	        final List<Customer> customers = new ArrayList<>();
	        repository.findAll().forEach(customer -> customers.add(customer));
	        return customers;
	    }
	
	    
	   //get details of customer through id
	   public Customer getCustomerDetails(long customerId) throws CustomerNotFoundException {
		 Customer cust = repository.getCustomerByCustomerId(customerId);
		 
		 if(cust == null) {
	    		throw new CustomerNotFoundException("Customer Doesn't Exist");
		 }
		 return cust;
	 }
	 
	    
	    }
