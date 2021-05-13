package com.paymybuddy.paymybuddyapp.service;

import java.util.List;

import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.Transaction;

public interface ITransactionService {

	public List<Transaction> getTransactions(Account account);
	
	public Transaction addTransaction(Transaction transaction);
}
