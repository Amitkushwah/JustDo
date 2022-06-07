package com.cts.retailbank.account;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.retailbank.account.model.Account;
import com.cts.retailbank.account.model.AccountCreationStatus;
import com.cts.retailbank.account.model.Transaction;
import com.cts.retailbank.account.model.TransactionAmount;
import com.cts.retailbank.account.model.dto.CustomerAccountDto;
import com.cts.retailbank.account.repository.AccountRepo;
import com.cts.retailbank.account.service.AccountService;

//@RunWith(SpringRunner.class)
@SpringBootTest
class AccountServiceTests {

	@Autowired
	private AccountService accountService;
	
	@MockBean
	private AccountRepo accountRepo;
	
	@Test
	public void createAccountTest() {
		Account account = new Account(10054550, 100104, 500.00, "current", "Chintu");
		AccountCreationStatus accountCreationStatus = new AccountCreationStatus(10054550, "Account created successfully");
		when(accountRepo.save(account))
		.thenReturn(account);
		assertEquals(accountCreationStatus, accountService.createAccount(account));
	}
	
	@Test
	public void getCustomerAccountsTest() {
		Account account = new Account(100545, 100, 4000.00, "Savings", "Rajdeep");
		when(accountRepo.getAccountByCustomerId(100))
		.thenReturn(Stream.of(account).collect(Collectors.toList()));
		assertEquals(1, accountService.getCustomerAccounts(100).size());
	}
	
	@Test
	public void getAccountTest() throws Exception {
		Account account = new Account(10077, 101, 7000.00, "Current", "Vijay");
		CustomerAccountDto customerAccountDto = new CustomerAccountDto(10077, "Current", 7000.00);
		when(accountRepo.getAccountByAccountId(10077))
		.thenReturn(account);
		assertEquals(customerAccountDto, accountService.getAccount(10077)); 
	}
	
//	@Test
//	public void depositTest() {
//		Account account = new Account(10054546, 104, 4200.00, "Current", "Ajay");
//		TransactionAmount transactionAmount = new TransactionAmount(10054546, 200.00);
//		Transaction transaction = new Transaction(true, 1L, "t", "d", LocalDateTime.now(), 200.00f, 4400.00f, "code", "des", "12", "45", 255.00f);
//		when(accountRepo.getAccountByAccountId(10054546))
//		.thenReturn(account);
//		assertEquals(transaction, accountService.deposit(transactionAmount));
//	}
	
//	@Test
//	public void getAccountStatementTest() throws Exception {
//		assertEquals(1, accountService.getAccountStatement(10054546, "2021-03-01", "2021-04-01").size());
//	}

}
