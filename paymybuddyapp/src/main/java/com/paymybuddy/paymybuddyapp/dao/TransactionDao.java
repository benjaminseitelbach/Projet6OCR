package com.paymybuddy.paymybuddyapp.dao;

import com.paymybuddy.paymybuddyapp.model.Transaction;

public interface TransactionDao {
	
	public Transaction addTransaction(Transaction transaction, int relationshipId, int bankAccountId);
}
