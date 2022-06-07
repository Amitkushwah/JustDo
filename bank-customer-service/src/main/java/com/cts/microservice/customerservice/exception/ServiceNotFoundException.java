package com.cts.microservice.customerservice.exception;

public class ServiceNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceNotFoundException(String msg) {
		
		super(msg);
	}

}
