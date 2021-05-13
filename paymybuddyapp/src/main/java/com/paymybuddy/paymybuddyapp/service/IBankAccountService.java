package com.paymybuddy.paymybuddyapp.service;

import java.util.List;

import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.BankAccount;

public interface IBankAccountService {
	
	public List<BankAccount> getBankAccounts(Account account);
}
