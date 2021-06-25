package com.paymybuddy.paymybuddyapp.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Transaction;
import com.paymybuddy.paymybuddyapp.service.ITransactionService;

@SpringBootTest
public class TransactionServiceIntegrationTest {

	@Autowired
	private ITransactionService transactionService;
	
	@Test
	public void getTransactionsByCustomerJohnBoydTest() throws Exception {
		
		Customer customer = new Customer();
		customer.setId(1);
		customer.setEmail("jaboyd@email.com");
		
		String receiverEmail = "drk@email.com";
		double transactionAmount = 10;
		String transactionDescription = "description1";
		
		Customer receiver = new Customer();
		receiver.setEmail(receiverEmail);
		
		Transaction transaction = new Transaction();
		transaction.setAmount(transactionAmount);
		transaction.setDescription(transactionDescription);
		transaction.setSender(customer);
		transaction.setReceiver(receiver);
		
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		
		List<Transaction> transactionsResult = transactionService.getTransactions(customer);
		
		Transaction transactionResult = transactionsResult.get(0);
		
		Customer receiverResult = transaction.getReceiver();
		assertEquals(transactionAmount, transactionResult.getAmount());
		assertEquals(transactionDescription, transactionResult.getDescription());
		assertEquals(receiverEmail, receiverResult.getEmail());

	}
	
}
