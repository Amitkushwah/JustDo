package com.cognizant.retailbank.transaction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO class of Rule Service Response
 */
@Data
@NoArgsConstructor
public class RulesDto {
	private Boolean isNotMinimum;
	private float minimumBalance;
}
