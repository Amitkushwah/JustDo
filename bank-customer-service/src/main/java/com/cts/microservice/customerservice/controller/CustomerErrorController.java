package com.cts.microservice.customerservice.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.microservice.customerservice.exception.CustomerNotFoundException;
import com.cts.microservice.customerservice.exception.ServiceNotFoundException;
import com.cts.microservice.customerservice.model.CustomerErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CustomerErrorController {

	//Global Exception Handler
	
	@ExceptionHandler({CustomerNotFoundException.class})
	public ResponseEntity<CustomerErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException exception){
	CustomerErrorResponse errorResponse = new CustomerErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND, exception.getMessage(), "Customer doesn't exist.");
	log.info("Error in Controller");
	return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler({ServiceNotFoundException.class})
	public ResponseEntity<CustomerErrorResponse> handleServiceNotFoundException(ServiceNotFoundException exception){
	CustomerErrorResponse errorResponse = new CustomerErrorResponse(LocalDateTime.now(), HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage(), "Service unavailable");
	log.info("Error in Controller");
	return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	
	}
 
















