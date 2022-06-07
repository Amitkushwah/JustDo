package com.cts.microservice.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.cts.microservice.customerservice.model.Customer;

@Component
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	public Customer getCustomerByCustomerId(long customerId);
	
}
