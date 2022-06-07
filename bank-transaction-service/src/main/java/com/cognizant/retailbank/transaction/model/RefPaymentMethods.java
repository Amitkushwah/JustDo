package com.cognizant.retailbank.transaction.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
/*
* Model class of Table Ref_Payment_Methods
*/
@Data
@NoArgsConstructor
@Entity
public class RefPaymentMethods {
	@Id
	private String paymentMethodCode;
	private String payementMethodName;
}
