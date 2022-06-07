package com.cognizant.retailbank.transaction.service;

import java.util.List;

import com.cognizant.retailbank.transaction.dto.TransactionStatusDTO;
import com.cognizant.retailbank.transaction.model.FinancialTransactions;

public interface TransactionService {

	public TransactionStatusDTO deposit(String accountId, float amount, float accountBalance);
	public TransactionStatusDTO withdraw(String accountId, float amount, float accountBalance);
	public List<FinancialTransactions> getAllTransactions(String customerId);
	public TransactionStatusDTO transfer(String sourceAccountId, String targetAccountId, float amount,
			float accountBalance, float recieverAccountBalance);
	public List<TransactionStatusDTO> getAccountStatement(String accountID, String fromDate, String toDate);
}
