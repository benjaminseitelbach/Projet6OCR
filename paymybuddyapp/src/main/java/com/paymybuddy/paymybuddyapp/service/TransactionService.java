package com.paymybuddy.paymybuddyapp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.paymybuddyapp.dao.CustomerRepository;
import com.paymybuddy.paymybuddyapp.dao.TransactionRepository;
import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Transaction;

@Service
public class TransactionService implements ITransactionService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	
	@Transactional
	public boolean sendMoney(Customer customer, int connectionId, BigDecimal amount) {
		//int customerId = customer.getId();
		
		BigDecimal amountWithTax = amount.add(new BigDecimal(0.005).multiply(amount));
		BigDecimal customerAmount = customer.getAmount();
		
		if(customerAmount.compareTo(amountWithTax) == 1) {
			
			BigDecimal customerNewAmount = customerAmount.subtract(amountWithTax);
			System.out.println("customer new amount: " + customerNewAmount.floatValue());
			customer.setAmount(customerNewAmount);
			customerRepository.save(customer);
			
			Customer connection = customerRepository.findById(connectionId).get();
			BigDecimal connectionNewAmount = connection.getAmount().add(amount);
			connection.setAmount(connectionNewAmount);
			customerRepository.save(connection);
			
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			//Date date = new Date();
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			transaction.setDescription("Transaction done on " + date);
			transaction.setDate(date);
			transaction.setSender(customer);
			transaction.setReceiver(connection);
			//transaction.setSender(customer);
			//transaction.setReceiver(connection);
			transactionRepository.save(transaction);
			//customer.addTransaction(transaction);
			return true;
		}
		return false;
				
	}
	
	
	public List<Transaction> getTransactions(Customer sender) {
		return transactionRepository.findBySender(sender);
	}
	
	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}
	

}
