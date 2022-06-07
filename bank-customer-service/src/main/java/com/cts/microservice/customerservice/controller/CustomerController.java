package com.cts.microservice.customerservice.controller;

import java.util.List;

import javax.management.ServiceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.microservice.customerservice.exception.CustomerNotFoundException;
import com.cts.microservice.customerservice.model.AccountCreationStatus;
import com.cts.microservice.customerservice.model.Customer;
import com.cts.microservice.customerservice.service.CustomerService;
import com.cts.microservice.customerservice.util.CustomerInput;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	
	
	@Autowired
	CustomerService service;
	
	//Creation of user
	@PostMapping(value= "/createCustomer")
	 public AccountCreationStatus save(@RequestBody @Validated CustomerInput customer) throws ServiceNotFoundException {
		log.info("Adding Customer by taking information");
	       return service.createCustomer(customer);
	       
	    }
	
	
	//Getall user
	 @GetMapping(value= "/getall", produces= "application/vnd.jcg.api.v1+json")
		public List<Customer> getAll() {
	        return service.getAll();
	    }
	 

	 //get customer information by id
	 @GetMapping(value="/getCustomerDetails/{customerid}")
	 public Customer getCustomerById(@PathVariable long customerid) throws CustomerNotFoundException {
		 Customer cus = null;
		 cus= service.getCustomerDetails(customerid);
		 log.error("Customer Doesn't Exist");
		 return cus;
	 } 
}
















