package com.paymybuddy.paymybuddyapp.dao;

import java.math.BigDecimal;
import java.util.List;

import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.BankAccount;

public interface IBankAccountRepository {
	
	public List<BankAccount> getBankAccounts(Account account);
	
	public int getId(BankAccount bankAccount, int accountId);
	
	public BigDecimal getAmount(int id);
	
	public void updateAmount(int id, BigDecimal amount);
	
	public BankAccount getBankAccount(String IBAN, int accountId);
}
