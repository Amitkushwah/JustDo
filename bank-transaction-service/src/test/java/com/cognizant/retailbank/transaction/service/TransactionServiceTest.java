package com.cognizant.retailbank.transaction.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.retailbank.transaction.dto.TransactionStatusDTO;
import com.cognizant.retailbank.transaction.model.FinancialTransactions;
import com.cognizant.retailbank.transaction.model.RefTransactionStatus;
import com.cognizant.retailbank.transaction.model.RefTransactionTypes;
import com.cognizant.retailbank.transaction.repositry.TransactionRepositry;
import com.cognizant.retailbank.transaction.utils.ConvertToDTO;

@SpringBootTest
public class TransactionServiceTest {

	
	
	@Mock
	TransactionRepositry transactionRepositry;
	
	@InjectMocks
	TransactionServiceImpl transactionService;
	
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void depositTest() {
		RefTransactionStatus refTransactionStatus = new RefTransactionStatus();
		refTransactionStatus.setTransactionStatusCode("SUCCESS");
		RefTransactionTypes refTransactionTypes = new RefTransactionTypes();
		refTransactionTypes.setTransactionTypeCode("DEPOSIT");
		FinancialTransactions transaction=new FinancialTransactions();
		LocalDateTime date=LocalDateTime.now();
		transaction.setAccountId("100100");
		transaction.setAmountOfTransaction(1000);
		transaction.setRefTransactionStatus(refTransactionStatus);
		transaction.setDateOfTransaction(date);
		transaction.setRefTransactionTypes(refTransactionTypes);
		transaction.setClosingBalance(4000);
		TransactionStatusDTO transacionDto=ConvertToDTO.convertToTransactionDTO(transaction, true);
		when(transactionRepositry.save(Mockito.any(FinancialTransactions.class))).thenReturn(transaction);
		TransactionStatusDTO result=transactionService.deposit("100100", 1000, 3000);
		assertEquals(result.getTransactionStatusCode(), transacionDto.getTransactionStatusCode());
	}
	@Test
	public void withdrawTest() {
		RefTransactionStatus refTransactionStatus = new RefTransactionStatus();
		refTransactionStatus.setTransactionStatusCode("SUCCESS");
		RefTransactionTypes refTransactionTypes = new RefTransactionTypes();
		refTransactionTypes.setTransactionTypeCode("WITHDRAW");
		FinancialTransactions transaction=new FinancialTransactions();
		LocalDateTime date=LocalDateTime.now();
		transaction.setAccountId("100100");
		transaction.setAmountOfTransaction(1000);
		transaction.setRefTransactionStatus(refTransactionStatus);
		transaction.setDateOfTransaction(date);
		transaction.setRefTransactionTypes(refTransactionTypes);
		transaction.setClosingBalance(4000);
		TransactionStatusDTO transacionDto=ConvertToDTO.convertToTransactionDTO(transaction, true);
		when(transactionRepositry.save(Mockito.any(FinancialTransactions.class))).thenReturn(transaction);
		TransactionStatusDTO result=transactionService.withdraw("100100", 1000, 4000);
		assertEquals(result.getTransactionStatusCode(), transacionDto.getTransactionStatusCode());
	}
	@Test
	public void transferTest() {
		RefTransactionStatus refTransactionStatus = new RefTransactionStatus();
		refTransactionStatus.setTransactionStatusCode("SUCCESS");
		RefTransactionTypes refTransactionTypes = new RefTransactionTypes();
		refTransactionTypes.setTransactionTypeCode("TRANSFER");
		FinancialTransactions transaction=new FinancialTransactions();
		LocalDateTime date=LocalDateTime.now();
		transaction.setAccountId("100100");
		transaction.setAmountOfTransaction(1000);
		transaction.setRefTransactionStatus(refTransactionStatus);
		transaction.setDateOfTransaction(date);
		transaction.setRefTransactionTypes(refTransactionTypes);
		transaction.setClosingBalance(4000);
		TransactionStatusDTO transacionDto=ConvertToDTO.convertToTransactionDTO(transaction, true);
		when(transactionRepositry.save(Mockito.any(FinancialTransactions.class))).thenReturn(transaction);
		TransactionStatusDTO result=transactionService.transfer("100100","100100", 1000, 5000,6000);
		assertEquals(result.getTransactionStatusCode(), transacionDto.getTransactionStatusCode());
	}
	@Test
	public void getAccountStatementTest() {
		List<FinancialTransactions> statementList=new ArrayList<>();
		List<TransactionStatusDTO> result=new ArrayList<>();
		RefTransactionStatus refTransactionStatus = new RefTransactionStatus();
		refTransactionStatus.setTransactionStatusCode("SUCCESS");
		RefTransactionTypes refTransactionTypes = new RefTransactionTypes();
		refTransactionTypes.setTransactionTypeCode("WITHDRAW");
		FinancialTransactions transaction1=new FinancialTransactions();
		LocalDateTime date=LocalDateTime.now();
		transaction1.setAccountId("100100");
		transaction1.setAmountOfTransaction(1000);
		transaction1.setRefTransactionStatus(refTransactionStatus);
		transaction1.setDateOfTransaction(date);
		transaction1.setRefTransactionTypes(refTransactionTypes);
		transaction1.setClosingBalance(4000);
		FinancialTransactions transaction2=new FinancialTransactions();
		LocalDateTime date2=LocalDateTime.now();
		transaction2.setAccountId("100100");
		transaction2.setAmountOfTransaction(1000);
		transaction2.setRefTransactionStatus(refTransactionStatus);
		transaction2.setDateOfTransaction(date2);
		transaction2.setRefTransactionTypes(refTransactionTypes);
		transaction2.setClosingBalance(4000);
		TransactionStatusDTO transacionDto1=ConvertToDTO.convertToTransactionDTO(transaction1, true);
		TransactionStatusDTO transacionDto2=ConvertToDTO.convertToTransactionDTO(transaction2, true);
		result.add(transacionDto1);
		result.add(transacionDto2);
		when(transactionRepositry.findAllByMonth(Mockito.any(String.class))).thenReturn(statementList);
		List<TransactionStatusDTO> testResult=transactionService.getAccountStatement("100100", "2021-03-01", "2021-04-01");
		assertEquals(result.size(), testResult.size());
	}
}
