package com.cognizant.retailbank.transaction.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.retailbank.transaction.dto.TransactionStatusDTO;
import com.cognizant.retailbank.transaction.model.FinancialTransactions;
import com.cognizant.retailbank.transaction.service.TransactionService;
import com.cognizant.retailbank.transaction.utils.DepositWithdrawalInput;
import com.cognizant.retailbank.transaction.utils.TransferInput;

import lombok.extern.slf4j.Slf4j;

/*
	Rest Controller to Handle all Transactions related API
*/
@RestController
@Slf4j
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	/*
	 * Function to create a transaction record of type deposit for given account id
	 */
	@PostMapping("/deposit")
	public TransactionStatusDTO depositAmount(@RequestBody @Valid DepositWithdrawalInput input) { 
		log.info("Start depositAmount");
		return transactionService.deposit(input.getAccountId().toPlainString(),input.getAmount().floatValue(),input.getBalance().floatValue());
	}
	
	/*
	 * Function to create a transaction record of type withdraw for given account id
	 */
	@PostMapping("/withdraw")
	public TransactionStatusDTO withdrawAmount(@RequestBody @Valid DepositWithdrawalInput input) { 
		log.info("Start withdrawAmount");
		return transactionService.withdraw(input.getAccountId().toPlainString(),input.getAmount().floatValue(),input.getBalance().floatValue());
	}
	
	/*
	 * Function to create a transaction record of type transfer for given account ids
	 */
	@PostMapping("/transfer")
	public TransactionStatusDTO transferAmount(@RequestBody @Valid TransferInput input) {
		log.info("Start transferAmount");
		return transactionService.transfer(String.valueOf(input.getSourceAccountId()), String.valueOf(input.getTargetAccountId()), input.getAmount().floatValue(),input.getSourceClosingBalance().floatValue(),input.getTargetClosingBalance().floatValue());
	}
	
	/*
	 * Function to get all transaction record for given customer id
	 */
	@GetMapping("/getTransactions/{customerId}")
	public List<FinancialTransactions> getAllTransactions(@PathVariable String customerId){
		log.info("Start getAllTransactions");
		return transactionService.getAllTransactions(customerId);
	}
	
	/*
	 * Function to get AccountStatement for given account id
	 */
	@GetMapping("/getAccountStatement")
	public List<TransactionStatusDTO> getAccountTransaction(@RequestParam String accountId,
			String fromDate,String toDate
			){
		log.info("Start getAccountTransaction");
		log.info("from Val {}",fromDate.length());
		log.info("from Val "+fromDate.length());
		return transactionService.getAccountStatement(accountId, fromDate, toDate); 
	}
}
