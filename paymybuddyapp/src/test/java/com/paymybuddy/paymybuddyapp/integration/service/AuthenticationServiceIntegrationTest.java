package com.paymybuddy.paymybuddyapp.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.service.IAuthenticationService;

@SpringBootTest
public class AuthenticationServiceIntegrationTest {
	
	@Autowired
	private IAuthenticationService authenticationService;
	
	@Test
	public void authenticateCustomerJohnBoydTest() throws Exception {
		String email = "jaboyd@email.com";
		String password = "password1";
		String firstName = "John";
		String lastName = "Boyd";
		//double amount = 100;
		
		Customer customer = new Customer();
		customer.setId(1);
		customer.setEmail(email);
		customer.setPassword("$2a$10$alZ2ltll1Euc5utRzJSxBuLa/TB1NuJXqLRqBIRl3OukEBwP68iF.");
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		//customer.setAmount(amount);
		
		Customer result = authenticationService.authenticate(email, password);
		
		assertEquals(email, result.getEmail());
		assertEquals(firstName, result.getFirstName());
		assertEquals(lastName, result.getLastName());
		//assertEquals(amount, result.getAmount());
	}
}
