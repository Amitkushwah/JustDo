package com.cts.retailbank.account;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.cts.retailbank.account.controller.AccountController;

@AutoConfigureMockMvc
@SpringBootTest
class AccountServiceApplicationTests {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private AccountController accuntController;

	
	@Test
	void contextLoads() {
		assertNotNull(accuntController);
	}
	
	@Test
	public void testGetAccount() throws Exception {
		ResultActions actions = mvc.perform(get("/account/get_acc/10054546"));
		actions.andExpect(status().isOk());
		actions.andExpect(jsonPath("$.accountId").exists());
		actions.andExpect(jsonPath("$.accountId").value(10054546));
		actions.andExpect(jsonPath("$.accountType").exists());
		actions.andExpect(jsonPath("$.accountType").value("Savings"));
		actions.andExpect(jsonPath("$.balance").exists());
		actions.andExpect(jsonPath("$.balance").value(11000));
	}
	
	@Test
	public void testGetAccountNotFoundException() throws Exception {
		ResultActions actions = mvc.perform(get("/account/get_acc/1005454"));
		actions.andExpect(status().isNotFound());
		actions.andDo(temp -> {System.out.print(temp.getResponse().getContentAsString());});
		actions.andExpect(jsonPath("$.status").value("NOT_FOUND"));
	}
	
	@Test
	public void testGetTransactionNotFoundtException() throws Exception {
		ResultActions actions = mvc.perform(get("/account/account-statement?accountId=10054546&fromDate=2022-03-01&toDate=2022-04-01"));
		actions.andExpect(status().isNotFound());
		actions.andDo(temp -> {System.out.print(temp.getResponse().getContentAsString());});
		actions.andExpect(jsonPath("$.status").value("NOT_FOUND"));
	}
	
	@Test
	public void testGetCustomerAccounts() throws Exception {
		ResultActions actions = mvc.perform(get("/account/get_cust_acc/100103"));
		actions.andExpect(status().isOk());
		
		actions.andExpect(jsonPath("$[0].accountId").exists());
		actions.andExpect(jsonPath("$[0].accountId").value(10054548));
		actions.andExpect(jsonPath("$[0].accountType").exists());
		actions.andExpect(jsonPath("$[0].accountType").value("Savings"));
		actions.andExpect(jsonPath("$[0].balance").exists());
		actions.andExpect(jsonPath("$[0].balance").value(15870));
		
		actions.andExpect(jsonPath("$[1].accountId").exists());
		actions.andExpect(jsonPath("$[1].accountId").value(10054549));
		actions.andExpect(jsonPath("$[1].accountType").exists());
		actions.andExpect(jsonPath("$[1].accountType").value("Current"));
		actions.andExpect(jsonPath("$[1].balance").exists());
		actions.andExpect(jsonPath("$[1].balance").value(1000));
	}
	
	@Test
	public void testCreateAccount() throws Exception {
		ResultActions actions = mvc.perform(post("/account/create-account")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"customerId\": 100104, \"accountType\": \"Savings\", \"accHolderName\": \"Shubham\" }"));
		actions.andExpect(status().isOk());
		actions.andExpect(jsonPath("$.accountId").exists());
		actions.andExpect(jsonPath("$.accountId").value(10054550));
		actions.andExpect(jsonPath("$.message").exists());
		actions.andExpect(jsonPath("$.message").value("Account created successfully"));
		
	}	
	
	@Test
	public void testDeposit() throws Exception {
		ResultActions actions = mvc.perform(post("/account/deposit")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"accountId\": 10054549, \"amount\": \"2000\" }"));
		actions.andExpect(status().isOk());
	}
	
	@Test
	public void testWithdraw() throws Exception {
		ResultActions actions = mvc.perform(post("/account/withdraw")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"accountId\": 10054547, \"amount\": \"7500\" }"));
		actions.andExpect(status().isOk());
	}
	
	@Test
	public void testGetAccountStatement() throws Exception {
		ResultActions actions = mvc.perform(get("/account/account-statement?accountId=10054547&fromDate=2021-03-01&toDate=2021-04-01"));
		actions.andExpect(status().isOk());
		actions.andExpect(jsonPath("$[0].success").exists());
		actions.andExpect(jsonPath("$[0].success").value(true));
		actions.andExpect(jsonPath("$[0].transactionId").exists());
		actions.andExpect(jsonPath("$[0].transactionId").value(100001));
		actions.andExpect(jsonPath("$[0].transactionStatusCode").exists());
		actions.andExpect(jsonPath("$[0].transactionStatusCode").value("SUCCESS"));
		actions.andExpect(jsonPath("$[0].transactionStatusDescription").exists());
		actions.andExpect(jsonPath("$[0].transactionStatusDescription").value("Transaction Success"));
		actions.andExpect(jsonPath("$[0].transactionDate").exists());
		actions.andExpect(jsonPath("$[0].transactionDate").value("2021-03-28T13:39:18.578187"));
		actions.andExpect(jsonPath("$[0].transactionAmount").exists());
		actions.andExpect(jsonPath("$[0].transactionAmount").value( 22200.0));
		actions.andExpect(jsonPath("$[0].transactionTypeCode").exists());
		actions.andExpect(jsonPath("$[0].transactionTypeCode").value("DEPOSIT"));
		actions.andExpect(jsonPath("$[0].transactionTypeDescription").exists());
		actions.andExpect(jsonPath("$[0].transactionTypeDescription").value("Amount Deposited"));
		actions.andExpect(jsonPath("$[0].senderAccountId").exists());
		actions.andExpect(jsonPath("$[0].senderAccountId").value("10054546"));
		actions.andExpect(jsonPath("$[0].closingBalance").exists());
		actions.andExpect(jsonPath("$[0].closingBalance").value(42200.0));
	}
	
	@Test
	public void testTransfer() throws Exception {
		ResultActions actions = mvc.perform(post("/account/transfer")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"sourceAccountId\": 10054546, \"targetAccountId\": 10054548, \"amount\": 8700 }"));
		actions.andExpect(status().isOk());
	}
}
