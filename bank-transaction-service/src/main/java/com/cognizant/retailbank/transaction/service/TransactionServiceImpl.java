package com.cognizant.retailbank.transaction.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cognizant.retailbank.transaction.dto.RulesDto;
import com.cognizant.retailbank.transaction.dto.TransactionStatusDTO;
import com.cognizant.retailbank.transaction.model.FinancialTransactions;
import com.cognizant.retailbank.transaction.model.RefTransactionStatus;
import com.cognizant.retailbank.transaction.model.RefTransactionTypes;
import com.cognizant.retailbank.transaction.repositry.TransactionRepositry;
import com.cognizant.retailbank.transaction.utils.ConvertToDTO;
import com.cognizant.retailbank.transaction.utils.RuleInput;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/*
 * Service class for Transaction Controller
 */

@Slf4j
@Service
@Data
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	TransactionRepositry transactionRepositry;
	
	@Value("${rules-service.uri}")
	private String ruleBaseUri;

	/*
	 * Function to record deposit transaction into database
	 */
	public TransactionStatusDTO deposit(String accountId, float amount, float accountBalance) {
		log.info("Inside Transaction Service");
		log.info("Start deposit ");
		FinancialTransactions financialTransactions = new FinancialTransactions();
		RefTransactionStatus refTransactionStatus = new RefTransactionStatus();
		refTransactionStatus.setTransactionStatusCode("SUCCESS");
		RefTransactionTypes refTransactionTypes = new RefTransactionTypes();
		refTransactionTypes.setTransactionTypeCode("DEPOSIT");
		financialTransactions.setAccountId(accountId);
		financialTransactions.setAmountOfTransaction(amount);
		financialTransactions.setRefTransactionStatus(refTransactionStatus);
		financialTransactions.setDateOfTransaction(LocalDateTime.now());
		financialTransactions.setRefTransactionTypes(refTransactionTypes);
		financialTransactions.setClosingBalance(accountBalance + amount);
		FinancialTransactions result = transactionRepositry.saveAndFlush(financialTransactions);
		log.debug("result {}",result);
		log.info("End deposit ");
		return ConvertToDTO.convertToTransactionDTO(result, true);
	}

	/*
	 * Function to record withdraw transaction into database
	 */
	public TransactionStatusDTO withdraw(String accountId, float amount, float accountBalance) {
		log.info("Inside Transaction Service");
		log.info("Start withdraw ");
		RuleInput ruleInput = new RuleInput();
		ruleInput.setAccountId(Long.valueOf(accountId));
		ruleInput.setCurrentBalance(accountBalance);
		log.debug("request input {}", ruleInput);
		ResponseEntity<RulesDto> rulesResponse = restTemplate
				.postForEntity(ruleBaseUri+"/rules/evaluateMinBal", ruleInput, RulesDto.class);
		log.debug("response {}", rulesResponse);
		log.debug("reponse {} {} {}", rulesResponse.getStatusCodeValue() == 200,
				rulesResponse.getBody().getIsNotMinimum(),
				accountBalance - amount > rulesResponse.getBody().getMinimumBalance());
		boolean isAllowed = rulesResponse.getStatusCodeValue() == 200 && rulesResponse.getBody().getIsNotMinimum()
				&& accountBalance - amount >= rulesResponse.getBody().getMinimumBalance();
		FinancialTransactions financialTransactions = new FinancialTransactions();
		RefTransactionStatus refTransactionStatus = new RefTransactionStatus();
		RefTransactionTypes refTransactionTypes = new RefTransactionTypes();
		if (isAllowed) {
			refTransactionStatus.setTransactionStatusCode("SUCCESS");
			refTransactionTypes.setTransactionTypeCode("WITHDRAW");
			financialTransactions.setAccountId(accountId);
			financialTransactions.setAmountOfTransaction(amount);
			financialTransactions.setDateOfTransaction(LocalDateTime.now());
			financialTransactions.setRefTransactionStatus(refTransactionStatus);
			financialTransactions.setRefTransactionTypes(refTransactionTypes);
			financialTransactions.setClosingBalance(accountBalance - amount);
			FinancialTransactions result = transactionRepositry.saveAndFlush(financialTransactions);
			log.debug("result {}",result);
			log.info("End Withdraw ");
			return ConvertToDTO.convertToTransactionDTO(result, true);
		} else {
			refTransactionStatus.setTransactionStatusCode("FAILURE");
			refTransactionTypes.setTransactionTypeCode("WITHDRAW");
			financialTransactions.setAccountId(accountId);
			financialTransactions.setAmountOfTransaction(amount);
			financialTransactions.setDateOfTransaction(LocalDateTime.now());
			financialTransactions.setRefTransactionStatus(refTransactionStatus);
			financialTransactions.setRefTransactionTypes(refTransactionTypes);
			financialTransactions.setClosingBalance(accountBalance);
			FinancialTransactions result = transactionRepositry.saveAndFlush(financialTransactions);
			log.debug("result {}",result);
			log.info("End Withdraw ");
			return ConvertToDTO.convertToTransactionDTO(result, false);
		}

	}

	/*
	 * Function to get all transactions by customer id from database
	 */
	public List<FinancialTransactions> getAllTransactions(String customerId) {
		log.info("Inside Transaction Service");
		log.info("Start getAllTransactions");
		List<String> accounts = new ArrayList<>();
		Sort sort = Sort.by("accountId").ascending();
		accounts.add("2000220xc");
		accounts.add("2000220xd");
		log.info("End getAllTransactions");
		return transactionRepositry.findAllByAccountIdIn(accounts, sort);
	}

	/*
	 * Function to record transfer transaction into database
	 */
	public TransactionStatusDTO transfer(String sourceAccountId, String targetAccountId, float amount,
			float accountBalance, float recieverAccountBalance) {
		log.info("Inside Transaction Service");
		log.info("Start transfer");
		RuleInput ruleInput = new RuleInput();
		ruleInput.setAccountId(Long.valueOf(sourceAccountId));
		ruleInput.setCurrentBalance(accountBalance);
		ResponseEntity<RulesDto> rulesResponse = restTemplate
				.postForEntity(ruleBaseUri+"/rules/evaluateMinBal", ruleInput, RulesDto.class);
		log.debug("response {}", rulesResponse);
		boolean isAllowed = rulesResponse.getStatusCodeValue() == 200 && rulesResponse.getBody().getIsNotMinimum()
				&& accountBalance - amount >= rulesResponse.getBody().getMinimumBalance();
		FinancialTransactions financialTransactionsSender = new FinancialTransactions();
		RefTransactionStatus refTransactionStatus = new RefTransactionStatus();
		RefTransactionTypes refTransactionTypesSender = new RefTransactionTypes();
		log.debug("Account balance {}", recieverAccountBalance);
		log.debug("Account balance {}", accountBalance);
		log.debug("Amount {}", amount);
		if (isAllowed) {
			FinancialTransactions financialTransactionsReciever = new FinancialTransactions();
			RefTransactionTypes refTransactionTypesReciever = new RefTransactionTypes();
			refTransactionTypesReciever.setTransactionTypeCode("RECIEVED");
			refTransactionStatus.setTransactionStatusCode("SUCCESS");
			refTransactionTypesSender.setTransactionTypeCode("TRANSFER");
			financialTransactionsSender.setAccountId(sourceAccountId);
			financialTransactionsSender.setOtherPartyAccountId(targetAccountId);
			financialTransactionsSender.setAmountOfTransaction(amount);
			financialTransactionsSender.setDateOfTransaction(LocalDateTime.now());
			financialTransactionsSender.setRefTransactionStatus(refTransactionStatus);
			financialTransactionsSender.setRefTransactionTypes(refTransactionTypesSender);
			financialTransactionsSender.setClosingBalance(accountBalance - amount);
			financialTransactionsReciever.setClosingBalance(recieverAccountBalance + amount);
			financialTransactionsReciever.setAccountId(targetAccountId);
			financialTransactionsReciever.setAmountOfTransaction(amount);
			financialTransactionsReciever.setDateOfTransaction(LocalDateTime.now());
			financialTransactionsReciever.setOtherPartyAccountId(sourceAccountId);
			financialTransactionsReciever.setRefTransactionStatus(refTransactionStatus);
			financialTransactionsReciever.setRefTransactionTypes(refTransactionTypesReciever);
			FinancialTransactions result = transactionRepositry.saveAndFlush(financialTransactionsSender);
			transactionRepositry.save(financialTransactionsReciever);
			log.debug("result {}",result);
			log.info("End transfer ");
			return ConvertToDTO.convertToTransferTransactionDTO(result, true, recieverAccountBalance + amount);
		} else {
			refTransactionStatus.setTransactionStatusCode("FAILURE");
			refTransactionTypesSender.setTransactionTypeCode("TRANSFER");
			financialTransactionsSender.setAccountId(sourceAccountId);
			financialTransactionsSender.setOtherPartyAccountId(targetAccountId);
			financialTransactionsSender.setAmountOfTransaction(amount);
			financialTransactionsSender.setDateOfTransaction(LocalDateTime.now());
			financialTransactionsSender.setRefTransactionStatus(refTransactionStatus);
			financialTransactionsSender.setRefTransactionTypes(refTransactionTypesSender);
			financialTransactionsSender.setClosingBalance(accountBalance);
			FinancialTransactions result = transactionRepositry.saveAndFlush(financialTransactionsSender);
			log.debug("result {}",result);
			log.info("End transfer ");
			return ConvertToDTO.convertToTransferTransactionDTO(result, false, recieverAccountBalance);
		}
	}

	/*
	 * Function to get all transactions by account id from database
	 */
	public List<TransactionStatusDTO> getAccountStatement(String accountID, String fromDate, String toDate) {
		log.info("Inside Transaction Service");
		log.info("Start getAccountStatement");
		List<FinancialTransactions> statementList;
		if (fromDate.length() < 1) {
			statementList = transactionRepositry.findAllByMonth(accountID);
		} else {
			statementList = transactionRepositry.findAllByDateOfTransactionBetween(fromDate, toDate,accountID);
		}
		log.debug("result {}",statementList);
		log.info("End getAccountStatement");
		return statementList.stream().map(x -> ConvertToDTO.convertToTransactionDTO(x, true))
				.collect(Collectors.toList());
	}

}
