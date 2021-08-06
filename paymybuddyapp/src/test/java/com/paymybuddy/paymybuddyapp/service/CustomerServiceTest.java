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
	private ICustomerService customerService;
	
	String customerEmailTest = "jaboyd@email.com";
	String customerPasswordTest = "password1";
	String customerFirstNameTest = "John";
	String customerLastNameTest = "Boyd";
	double customerAmountTest = 100;
	
	@Test
	public void authenticateCustomerJohnBoydTest() throws Exception {
		Customer customer = new Customer();
		customer.setEmail(customerEmailTest);
		customer.setPassword("$2a$10$alZ2ltll1Euc5utRzJSxBuLa/TB1NuJXqLRqBIRl3OukEBwP68iF.");
		customer.setFirstName(customerFirstNameTest);
		customer.setLastName(customerLastNameTest);
		customer.setAmount(customerAmountTest);
		
		Optional<Customer> optCustomer = Optional.of(customer);
				
		Mockito.when(customerRepository.existsByEmail(customerEmailTest)).thenReturn(true);
		Mockito.when(customerRepository.findByEmail(customerEmailTest)).thenReturn(optCustomer);
		
		Customer result = customerService.authenticate(customerEmailTest, customerPasswordTest);
		assertEquals(customerEmailTest, result.getEmail());
		assertEquals(customerFirstNameTest, result.getFirstName());
		assertEquals(customerLastNameTest, result.getLastName());
		assertEquals(customerAmountTest, result.getAmount());
	}
	
	@Test
	public void addCustomerTest() throws Exception {
		String emailTest = "test@email.com";
		String passwordTest = "passwordtest";
		String firstNameTest = "FirstNameTest";
		String lastNameTest = "LastNameTest";
		
		Customer customer = new Customer();
		customer.setEmail(emailTest);
		customer.setPassword(passwordTest);
		customer.setFirstName(firstNameTest);
		customer.setLastName(lastNameTest);
		
		Mockito.when(customerRepository.existsByEmail(emailTest)).thenReturn(false);
		Mockito.when(customerRepository.save(customer)).thenReturn(customer);
		
		Customer result = customerService.addCustomer(customer); 
		assertEquals(emailTest, result.getEmail());
		assertEquals(firstNameTest, result.getFirstName());
		assertEquals(lastNameTest, result.getLastName());
		assertEquals(0, result.getAmount());
	}
	
/*	
	@Test
	public void addConnectionTest() throws Exception {
		
		
		Customer result = customerService.addConnection(customer, connectionEmail);
		
	}
	
	public Customer addBankAccount(Customer customer, String iban, double amount);
	
	public boolean sendToPayMyBuddy(Customer customer, double amount);
	
	public boolean recoverToBankAccount(Customer customer, double amount);
*/

}
