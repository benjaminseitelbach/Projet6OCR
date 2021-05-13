package com.paymybuddy.paymybuddyapp.dao;

import java.util.List;

import com.paymybuddy.paymybuddyapp.model.Transaction;

public interface ITransactionRepository {
	
	public Transaction addTransaction(Transaction transaction, int relationshipId, int bankAccountId);
	
	public List<Transaction> getTransactions(int relationshipId);
}
