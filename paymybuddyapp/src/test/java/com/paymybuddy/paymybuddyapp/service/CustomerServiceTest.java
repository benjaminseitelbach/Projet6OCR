package com.paymybuddy.paymybuddyapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.paymybuddy.paymybuddyapp.dao.CustomerRepository;
import com.paymybuddy.paymybuddyapp.model.BankAccount;
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
	
	
	@Test
	public void addConnectionTest() throws Exception {
		Customer customer = new Customer();
		customer.setEmail(customerEmailTest);
		
		String connectionEmail = "drk@email.com";
		
		Customer connection = new Customer();
		connection.setEmail(connectionEmail);
		
		Optional<Customer> optConnection = Optional.of(connection);
		
		Customer customerWithConnection = new Customer();
		customerWithConnection.setEmail(customerEmailTest);
		
		Set<Customer> connections = new HashSet<>();
		connections.add(connection);
		
		customerWithConnection.setConnections(connections);
		
		Mockito.when(customerRepository.findByEmail(connectionEmail)).thenReturn(optConnection);
		Mockito.when(customerRepository.save(customer)).thenReturn(customerWithConnection);
		
		boolean result = customerService.addConnection(customer, connectionEmail);
		assertEquals(true, result);
		
		boolean connectionFound = false;
		for(Customer customerConnection : customer.getConnections()) {
			if(customerConnection.getEmail().equals(connectionEmail)) {
				connectionFound = true;
			}
		}
		assertEquals(true, connectionFound);
		
	}
	
	
	@Test
	public void addBankAccountTest() throws Exception {
		String customerEmail = "newcustomer@email.com";
		
		Customer customer = new Customer();
		customer.setEmail(customerEmail);
		
		String iban = "IBANTEST";
		double amount = 100;
		
		BankAccount bankAccount = new BankAccount();
		bankAccount.setIban(iban);
		bankAccount.setAmount(amount);
		
		Customer customerWithBankAccount = new Customer();
		customerWithBankAccount.setEmail(customerEmail);	
		customerWithBankAccount.setBankAccount(bankAccount);
		
		Mockito.when(customerRepository.save(customer)).thenReturn(customerWithBankAccount);
		
		Customer result = customerService.addBankAccount(customer, iban, amount);
		BankAccount bankAccountResult = result.getBankAccount();
		assertEquals(iban, bankAccountResult.getIban());
		assertEquals(amount, bankAccountResult.getAmount());
	}
	
	@Test
	public void sendToPayMyBuddyTest() throws Exception {
		double transactionAmount = 10;
		
		BankAccount bankAccount = new BankAccount();
		bankAccount.setAmount(customerAmountTest);
		
		Customer customer = new Customer();
		customer.setEmail(customerEmailTest);
		customer.setAmount(0);
		customer.setBankAccount(bankAccount);
		
		BankAccount bankAccountAfterTransaction = new BankAccount();
		bankAccountAfterTransaction.setAmount(customerAmountTest - transactionAmount);
		
		Customer customerAfterTransaction = new Customer();
		customerAfterTransaction.setEmail(customerEmailTest);
		customerAfterTransaction.setBankAccount(bankAccountAfterTransaction);
			
		Mockito.when(customerRepository.save(customer)).thenReturn(customerAfterTransaction);
		
		boolean result = customerService.sendToPayMyBuddy(customer, transactionAmount);
		assertEquals(true, result);
	}
	
	@Test
	public void recoverToBankAccountTest() throws Exception {
		double transactionAmount = 10;
		
		BankAccount bankAccount = new BankAccount();
		bankAccount.setAmount(customerAmountTest);
		
		Customer customer = new Customer();
		customer.setEmail(customerEmailTest);
		customer.setAmount(0);
		customer.setBankAccount(bankAccount);
		
		BankAccount bankAccountAfterTransaction = new BankAccount();
		bankAccountAfterTransaction.setAmount(customerAmountTest + transactionAmount);
		
		Customer customerAfterTransaction = new Customer();
		customerAfterTransaction.setEmail(customerEmailTest);
		customerAfterTransaction.setBankAccount(bankAccountAfterTransaction);
			
		Mockito.when(customerRepository.save(customer)).thenReturn(customerAfterTransaction);
		
		boolean result = customerService.sendToPayMyBuddy(customer, transactionAmount);
		assertEquals(true, result);
	}
	
	/*
	
	public boolean recoverToBankAccount(Customer customer, double amount);
*/

}
