package com.paymybuddy.paymybuddyapp.dao;

import java.util.List;

import com.paymybuddy.paymybuddyapp.model.Transaction;

public interface ITransactionRepository {
	
	public Transaction addTransaction(Transaction transaction);
	
	public List<Transaction> getTransactions(int relationshipId);
}
