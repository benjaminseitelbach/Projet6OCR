package com.paymybuddy.paymybuddyapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.paymybuddy.paymybuddyapp.dao.CustomerRepository;
import com.paymybuddy.paymybuddyapp.model.Customer;

@SpringBootTest
public class CustomerServiceTest {

	@MockBean
	private CustomerRepository customerRepository;
	
	@Autowired
	private ICustomerService authenticationService;
	
	@Test
	public void authenticateCustomerJohnBoydTest() throws Exception {
		String email = "jaboyd@email.com";
		String password = "password1";
		String firstName = "John";
		String lastName = "Boyd";
		double amount = 100;
		
		Customer customer = new Customer();
		customer.setId(1);
		customer.setEmail(email);
		customer.setPassword("$2a$10$alZ2ltll1Euc5utRzJSxBuLa/TB1NuJXqLRqBIRl3OukEBwP68iF.");
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setAmount(amount);
		
		Optional<Customer> optCustomer = Optional.of(customer);
		
		
		Mockito.when(customerRepository.existsByEmail(email)).thenReturn(true);
		Mockito.when(customerRepository.findByEmail(email)).thenReturn(optCustomer);
		
		Customer result = authenticationService.authenticate(email, password);
		assertEquals(email, result.getEmail());
		assertEquals(firstName, result.getFirstName());
		assertEquals(lastName, result.getLastName());
		assertEquals(amount, result.getAmount());
	}
}
