package com.cognizant.retailbank.transaction.utils;

import lombok.Data;
/*
 * Class for Rule Input
 */
@Data
public class RuleInput {
	private long accountId;
	private double currentBalance;
}
