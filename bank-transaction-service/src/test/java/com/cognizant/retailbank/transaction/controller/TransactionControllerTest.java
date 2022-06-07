package com.cognizant.retailbank.transaction.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cognizant.retailbank.transaction.service.TransactionServiceImpl;
import com.cognizant.retailbank.transaction.utils.DepositWithdrawalInput;
import com.cognizant.retailbank.transaction.utils.TransferInput;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TransactionController.class)
public class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wc;

	static ObjectMapper MAPPER = new ObjectMapper();

	@BeforeEach
	public void setUp() throws JsonProcessingException, Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wc).build();
	}

	@MockBean
	private TransactionServiceImpl transactionService;
	

	@Test
	public void testDepositAccountSuccess() throws Exception {
		DepositWithdrawalInput input = new DepositWithdrawalInput();
		input.setAccountId(BigDecimal.valueOf(1223123));
		input.setAmount(BigDecimal.valueOf(1000));
		input.setBalance(BigDecimal.valueOf(4000));
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/deposit").content(asJsonString(input))
//				.contentType(MediaType.APPLICATION_JSON);
//		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful())
//				.andDo(resultVal -> {
//					System.out.println("Rest");
//					System.out.println(resultVal.getResponse().getContentAsString());
//				})
//				.andReturn();
//		System.out.println("Test");
//		System.out.println(result.getResponse().getContentAsString());
	
		ResultActions actions = mockMvc.perform(post("/deposit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(input)));
		actions.andExpect(status().isOk());
		actions.andDo(res ->{
			System.out.println("Resp");
			System.out.println(res.getResponse().getContentAsString());
		});
	
	}

	@Test
	public void testDepositAccountFail() throws Exception {
		DepositWithdrawalInput input = new DepositWithdrawalInput();
		input.setAmount(BigDecimal.valueOf(1000));
		input.setBalance(BigDecimal.valueOf(4000));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/deposit").content(asJsonString(input))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError())
				.andReturn();
		System.out.println("Test");
		System.out.println(result.getResponse().getContentAsString());
	}
	 
	@Test
	public void testWithdrawAccountSuccess() throws Exception {
		DepositWithdrawalInput input = new DepositWithdrawalInput();
		input.setAccountId(BigDecimal.valueOf(1223123));
		input.setAmount(BigDecimal.valueOf(1000));
		input.setBalance(BigDecimal.valueOf(4000));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/withdraw").content(asJsonString(input))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful())
				.andReturn();
		System.out.println("Test");
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testWithdrawAccountFail() throws Exception {
		DepositWithdrawalInput input = new DepositWithdrawalInput();
		input.setAmount(BigDecimal.valueOf(1000));
		input.setBalance(BigDecimal.valueOf(4000));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/withdraw").content(asJsonString(input))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError())
				.andReturn();
		System.out.println("Test");
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testTransferSuccess() throws Exception{
		TransferInput input=new TransferInput();
		input.setAmount(BigDecimal.valueOf(1999));
		input.setSourceAccountId("1312312");
		input.setTargetAccountId("342323");
		input.setSourceClosingBalance(BigDecimal.valueOf(19995));
		input.setTargetClosingBalance(BigDecimal.valueOf(199239));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transfer").content(asJsonString(input))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful())
				.andReturn();
		System.out.println("Test");
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testTransferFail() throws Exception{
		TransferInput input=new TransferInput();
		input.setAmount(BigDecimal.valueOf(1999));
		input.setTargetAccountId("342323");
		input.setSourceClosingBalance(BigDecimal.valueOf(19995));
		input.setTargetClosingBalance(BigDecimal.valueOf(199239));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transfer").content(asJsonString(input))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError())
				.andReturn();
		System.out.println("Test");
		System.out.println(result.getResponse().getContentAsString());
	}
	  
	@Test
	public void testAccountStatementMonth() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getAccountStatement?accountId=10054546&fromDate=&toDate");
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful())
				.andReturn();
	}
	@Test
	public void testAccountStatementRange() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getAccountStatement?accountId=10054546&fromDate=2021-03-02&toDate=2021-04-01");
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful())
				.andReturn();
	}
	public static String asJsonString(final Object obj) throws JsonProcessingException {

		final ObjectMapper mapper = new ObjectMapper();
		final String jsonContent = mapper.writeValueAsString(obj);
		return jsonContent;

	}
}
