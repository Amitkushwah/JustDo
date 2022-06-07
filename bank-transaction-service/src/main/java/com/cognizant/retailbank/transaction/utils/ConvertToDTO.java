package com.cognizant.retailbank.transaction.utils;

import com.cognizant.retailbank.transaction.dto.TransactionStatusDTO;
import com.cognizant.retailbank.transaction.model.FinancialTransactions;

/*
 * Utility Class Converting Model into DTO
 */
public class ConvertToDTO {

	public static TransactionStatusDTO convertToTransferTransactionDTO(FinancialTransactions financialTransactions,boolean status,float recieverAccountBalance) {
		TransactionStatusDTO transactionStatusDTO=new TransactionStatusDTO();
		transactionStatusDTO.setSuccess(status);
		transactionStatusDTO.setSenderAccountId(financialTransactions.getAccountId());
		transactionStatusDTO.setRecieverAccountId(financialTransactions.getOtherPartyAccountId());
		transactionStatusDTO.setTransactionAmount(financialTransactions.getAmountOfTransaction());
		transactionStatusDTO.setTransactionDate(financialTransactions.getDateOfTransaction());
		transactionStatusDTO.setTransactionId(financialTransactions.getTransactionId());
		transactionStatusDTO.setTransactionStatusCode(financialTransactions.getRefTransactionStatus().getTransactionStatusCode());
		transactionStatusDTO.setTransactionTypeCode(financialTransactions.getRefTransactionTypes().getTransactionTypeCode());
		transactionStatusDTO.setTransactionStatusDescription(financialTransactions.getRefTransactionStatus().getTransactionStatusDescription());
		transactionStatusDTO.setTransactionTypeDescription(financialTransactions.getRefTransactionTypes().getTransactionTypeDescription());
		transactionStatusDTO.setClosingBalance(financialTransactions.getClosingBalance());
		transactionStatusDTO.setRecieverClosingBalance(recieverAccountBalance);
		return transactionStatusDTO;
	}
	public static TransactionStatusDTO convertToTransactionDTO(FinancialTransactions financialTransactions,boolean status) {
		System.out.println("Fin tran"+financialTransactions);
		TransactionStatusDTO transactionStatusDTO=new TransactionStatusDTO();
		transactionStatusDTO.setSuccess(status);
		transactionStatusDTO.setSenderAccountId(financialTransactions.getAccountId());
		transactionStatusDTO.setRecieverAccountId(financialTransactions.getOtherPartyAccountId());
		transactionStatusDTO.setTransactionAmount(financialTransactions.getAmountOfTransaction());
		transactionStatusDTO.setTransactionDate(financialTransactions.getDateOfTransaction());
		transactionStatusDTO.setTransactionId(financialTransactions.getTransactionId());
		transactionStatusDTO.setTransactionStatusCode(financialTransactions.getRefTransactionStatus().getTransactionStatusCode());
		transactionStatusDTO.setTransactionTypeCode(financialTransactions.getRefTransactionTypes().getTransactionTypeCode());
		transactionStatusDTO.setTransactionStatusDescription(financialTransactions.getRefTransactionStatus().getTransactionStatusDescription());
		transactionStatusDTO.setTransactionTypeDescription(financialTransactions.getRefTransactionTypes().getTransactionTypeDescription());
		transactionStatusDTO.setClosingBalance(financialTransactions.getClosingBalance());
		return transactionStatusDTO;
	}
}
