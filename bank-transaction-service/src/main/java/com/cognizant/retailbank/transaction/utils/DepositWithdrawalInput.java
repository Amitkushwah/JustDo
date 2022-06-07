package com.cognizant.retailbank.transaction.utils;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.Data;

/*
 * Class for Deposit Input as well as Withdrawal Input
 */
@Data
public class DepositWithdrawalInput {
	@NotNull(message = "account id is required")
	@DecimalMin(value = "1",message = "Invalid account id.")
	private BigDecimal accountId;
	@NotNull(message = "amount is required")
	@DecimalMin(value = "1",message = "amount cannot be smaller than or equal to 0")
	private BigDecimal amount;
	@NotNull(message = "balance is required")
	private BigDecimal balance;
}
