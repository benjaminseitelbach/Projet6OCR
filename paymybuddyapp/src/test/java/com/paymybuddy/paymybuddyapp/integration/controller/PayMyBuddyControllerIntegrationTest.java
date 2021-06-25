package com.paymybuddy.paymybuddyapp.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.paymybuddy.paymybuddyapp.model.Customer;

@SpringBootTest
@AutoConfigureMockMvc
public class PayMyBuddyControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
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
		
		mockMvc.perform(post("/sendMoney")  
	                    .param("connection", "2") 
	                    .param("amount", "10")
	                    .contentType(MediaType.APPLICATION_FORM_URLENCODED) 
	                    .accept(MediaType.APPLICATION_JSON)) //
				.andExpect(status().isOk())
	            .andExpect(view().name("transfer"))// 
	            .andReturn();
		
	}
	
}
