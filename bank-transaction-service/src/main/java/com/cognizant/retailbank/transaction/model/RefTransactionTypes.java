package com.cognizant.retailbank.transaction.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
/*
* Model class of Table Ref_Transaction_Types
*/
@Data
@NoArgsConstructor
@Entity
public class RefTransactionTypes {
	@Id
	private String transactionTypeCode;
	private String transactionTypeDescription;
}
