package com.paymybuddy.paymybuddyapp.service;

import java.math.BigDecimal;
import java.util.List;

import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Transaction;

public interface ITransactionService {

	/*
	public List<Transaction> getTransactions(Customer account);
	
	public Transaction addTransaction(Transaction transaction);
	*/
	public boolean sendMoney(Customer customer, int connectionId, BigDecimal amount);
	
	public List<Transaction> getTransactions(Customer sender);
	
	public List<Transaction> getAllTransactions();
}
