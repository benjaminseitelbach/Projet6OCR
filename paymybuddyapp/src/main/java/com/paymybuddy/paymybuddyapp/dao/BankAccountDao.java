package com.paymybuddy.paymybuddyapp.dao;

import com.paymybuddy.paymybuddyapp.model.BankAccount;

public interface BankAccountDao {
	public BankAccount addBankAccount(int accountId, BankAccount bankAccount);
}
