package com.cognizant.retailbank.transaction.utils;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Class for Account Statement Input
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatementInput {
	private String accountId;
	private LocalDate startDate;
	private LocalDate endDate;
}
