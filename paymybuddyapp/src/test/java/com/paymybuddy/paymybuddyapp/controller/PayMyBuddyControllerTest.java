package com.paymybuddy.paymybuddyapp.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.service.AuthenticationService;
import com.paymybuddy.paymybuddyapp.service.ITransactionService;

@SpringBootTest
@AutoConfigureMockMvc
public class PayMyBuddyControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthenticationService authenticationService;
	
	@MockBean
	private ITransactionService transactionService;
	
	@Test
    public void signInPageTest() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("signIn"));
    }
	
	
	@Test
	public void signInTest() throws Exception {
		String email = "jaboyd@email.com";
		String password = "password1";
		
		Customer customer = new Customer();
		customer.setId(1);
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setFirstName("John");
		customer.setLastName("Boyd");
		customer.setAmount(100);
		
		
		Mockito.when(authenticationService.authenticate(email, password)).thenReturn(customer);
		
		mockMvc.perform(post("/signIn") 
	                    .param("email", email) 
	                    .param("password", password)                 
	                    .contentType(MediaType.APPLICATION_FORM_URLENCODED) 
	                    .accept(MediaType.APPLICATION_JSON)) //
				.andExpect(status().isOk())
	            .andExpect(view().name("transfer"))// 
	            .andReturn();
		
	}
	
	@Test
	public void sendMoneyTest() throws Exception {
		String email = "jaboyd@email.com";
		String password = "password1";
		
		Customer customer = new Customer();
		customer.setId(1);
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setFirstName("John");
		customer.setLastName("Boyd");
		customer.setAmount(100);
		
		
		Mockito.when(transactionService.sendMoney(customer, 2, 10)).thenReturn(true);
		
		mockMvc.perform(post("/sendMoney")  
	                    .param("connection", "2") 
	                    .param("amount", "10")
	                    .contentType(MediaType.APPLICATION_FORM_URLENCODED) 
	                    .accept(MediaType.APPLICATION_JSON)) //
				.andExpect(status().isOk())
	            .andExpect(view().name("transfer"))// 
	            .andReturn();
		
	}
	/*
	@Test
	public void getSignIn() {
		
		try {
			mvc.perform(get("/")
				.contentType("application/json"))
				.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	*/
	
}
