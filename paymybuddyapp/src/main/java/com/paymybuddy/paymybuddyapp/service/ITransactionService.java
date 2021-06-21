package com.paymybuddy.paymybuddyapp.service;

import java.util.List;

import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Transaction;

public interface ITransactionService {

	public boolean sendMoney(Customer customer, int connectionId, double amount);
	
	public List<Transaction> getTransactions(Customer sender);
	

}
