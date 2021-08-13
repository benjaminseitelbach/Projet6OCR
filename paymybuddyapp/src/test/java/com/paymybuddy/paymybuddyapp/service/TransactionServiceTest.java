package com.paymybuddy.paymybuddyapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.paymybuddy.paymybuddyapp.dao.CustomerRepository;
import com.paymybuddy.paymybuddyapp.dao.TransactionRepository;
import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Transaction;

@SpringBootTest
public class TransactionServiceTest {

	@MockBean
	private TransactionRepository transactionRepository;
	
	@MockBean
	private CustomerRepository customerRepository;
	
	@Autowired
	private ITransactionService transactionService;
	
	@Test
	public void getTransactionsByCustomerJohnBoydTest() throws Exception {
		
		Customer customer = new Customer();
		customer.setId(1);
		customer.setEmail("jaboyd@email.com");
		
		String receiverEmail = "drk@email.com";
		Date transactionDate = new Date("06/08/2021");
		double transactionAmount = 10;
		String transactionDescription = "description1";
		
		Customer receiver = new Customer();
		receiver.setEmail(receiverEmail);
		
		Transaction transaction = new Transaction();
		transaction.setAmount(transactionAmount);
		transaction.setDescription(transactionDescription);
		transaction.setDate(transactionDate);
		transaction.setSender(customer);
		transaction.setReceiver(receiver);
		
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		Mockito.when(transactionRepository.findBySender(customer)).thenReturn(transactions);
		
		List<Transaction> transactionsResult = transactionService.getTransactions(customer);
		
		Transaction transactionResult = transactionsResult.get(0);
		
		Customer receiverResult = transaction.getReceiver();
		assertEquals(transactionAmount, transactionResult.getAmount());
		assertEquals(transactionDescription, transactionResult.getDescription());
		assertEquals(transactionDate, transactionResult.getDate());
		assertEquals(receiverEmail, receiverResult.getEmail());

	}
	
	@Test
	public void sendMoneyJohnBoydToTessaCarmanTest() throws Exception {
		Customer sender = new Customer();
		sender.setAmount(100);
		
		double senderNewAmount = 79.9;
		double receiverNewAmount = 320;
		
		Customer senderWithNewAmount = new Customer();
		senderWithNewAmount.setAmount(senderNewAmount);
		
		int receiverId = 3;
		Customer receiver = new Customer();
		receiver.setId(receiverId);
		receiver.setAmount(300);
		
		Customer receiverWithNewAmount = new Customer();
		senderWithNewAmount.setAmount(receiverNewAmount);
		
		double transactionAmount = 20;
		String description = "Description Test";
		
		Transaction transaction = new Transaction();
		transaction.setAmount(transactionAmount);
		transaction.setDescription(description);
		
		Optional<Customer> optReceiver = Optional.of(receiver);
		
		Mockito.when(customerRepository.findById(receiverId)).thenReturn(optReceiver);
		Mockito.when(customerRepository.save(senderWithNewAmount)).thenReturn(senderWithNewAmount);
		Mockito.when(customerRepository.save(receiverWithNewAmount)).thenReturn(receiverWithNewAmount);
		Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
		
		boolean result = transactionService.sendMoney(sender, receiverId, transactionAmount, description);
		assertEquals(true, result);
		assertEquals(senderNewAmount, sender.getAmount());
		assertEquals(receiverNewAmount, receiver.getAmount());
		
	}
	
	
	
	

}
