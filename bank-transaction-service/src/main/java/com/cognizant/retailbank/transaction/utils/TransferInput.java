package com.cognizant.retailbank.transaction.utils;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * Class for Transfer action Input
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferInput {
	@NotNull(message = "sourceAccountId is required")
	@NotEmpty(message = "sourceAccountId cannot be empty")
	private String sourceAccountId;
	@NotNull(message = "targetAccountId is required")
	@NotEmpty(message = "targetAccountId cannot be empty")
	private String targetAccountId;
	@NotNull(message = "amount is required")
	@DecimalMin(value = "1",message = "amount cannot be smaller than or equal to 0")
	private BigDecimal amount;
	@NotNull(message = "sourceClosingBalance is required")
	private BigDecimal sourceClosingBalance;
	@NotNull(message = "targetClosingBalance is required")
	private BigDecimal targetClosingBalance;
}
