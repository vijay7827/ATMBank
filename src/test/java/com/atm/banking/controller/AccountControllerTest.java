package com.atm.banking.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.atm.banking.Demo1ApplicationTests;
import com.atm.banking.enums.AccountType;
import com.atm.banking.enums.TransactionType;
import com.atm.banking.model.Account;
import com.atm.banking.model.Address;
import com.atm.banking.model.TransactionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountControllerTest extends Demo1ApplicationTests{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountControllerTest.class);
	
	private MockMvc mockMvc;
	private static String baseUrl;
	private static String contentType;
	private static String userAgent;

	
	@Autowired
	private AccountsController accountController;
	
	@BeforeClass
	public static void init() {
		baseUrl = "/api/v1/accounts";
		contentType = "application/json";
		userAgent = "User-Agent";
	}
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
	}
	
	private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	@org.junit.Test
	public void addAccount_caseSuccess() throws Exception {
		StringBuffer url = new StringBuffer(baseUrl);
		Account account = new Account();
		Address address = new Address();
		address.setCity("Noida");
		address.setCountry("India");
		address.setPinCode("201005");
		account.setFirstName("Vijay");
		account.setLastName("Singh");
		account.setCurrency("INR");
		account.setAccountType(AccountType.SAVING);
		account.setAddress(address);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url.toString()).content(asJsonString(account)).contentType(contentType);
		MvcResult responseObject = mockMvc.perform(requestBuilder).andReturn();
		LOGGER.info("Responce:" + responseObject.getResponse().getStatus());
		assertEquals(201, responseObject.getResponse().getStatus());
	}
	
	@Test
	public void addAccount_caseFailWithoutAddress() throws Exception {
		StringBuffer url = new StringBuffer(baseUrl);
		Account account = new Account();
		Address address = new Address();
		address.setCity("Noida");
		address.setCountry("India");
		address.setPinCode("201005");
		account.setFirstName("Vijay");
		account.setLastName("Singh");
		account.setCurrency("INR");
		account.setAccountType(AccountType.SAVING);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url.toString()).content(asJsonString(account)).contentType(contentType);
		MvcResult responseObject = mockMvc.perform(requestBuilder).andReturn();
		LOGGER.info("Responce:" + responseObject.getResponse().getStatus());
		assertEquals(400, responseObject.getResponse().getStatus());
	}

	
	@Test
	public void addAccount_caseFailWithoutName() throws Exception {
		StringBuffer url = new StringBuffer(baseUrl);
		Account account = new Account();
		Address address = new Address();
		address.setCity("Noida");
		address.setCountry("India");
		address.setPinCode("201005");
		account.setCurrency("INR");
		account.setAccountType(AccountType.SAVING);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url.toString()).content(asJsonString(account)).contentType(contentType);
		MvcResult responseObject = mockMvc.perform(requestBuilder).andReturn();
		LOGGER.info("Responce:" + responseObject.getResponse().getStatus());
		assertEquals(400, responseObject.getResponse().getStatus());
	}
	
	@Test
	public void enquiryAccountFailWrongCredentials() throws Exception {
		TransactionRequest request = new TransactionRequest();
		request.setCardNumber("5351249752881");
		request.setPin("12345");
		request.setType(TransactionType.ENQUIRY.getValue());
		StringBuffer url = new StringBuffer(baseUrl).append("/").append("transaction-request");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url.toString()).content(asJsonString(request)).contentType(contentType);
		MvcResult responseObject = mockMvc.perform(requestBuilder).andReturn();
		LOGGER.info("Responce:" + responseObject.getResponse().getStatus());
		assertEquals(401, responseObject.getResponse().getStatus());
	}
	
	@Test
	public void enquiryAccountSuccess() throws Exception {
		TransactionRequest request = new TransactionRequest();
		request.setCardNumber("5351249752881");
		request.setPin("1234");
		request.setType(TransactionType.ENQUIRY.getValue());
		StringBuffer url = new StringBuffer(baseUrl).append("/").append("transaction-request");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url.toString()).content(asJsonString(request)).contentType(contentType);
		MvcResult responseObject = mockMvc.perform(requestBuilder).andReturn();
		LOGGER.info("Responce:" + responseObject.getResponse().getStatus());
		assertEquals(200, responseObject.getResponse().getStatus());
	}


}
