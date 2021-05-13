package com.paymybuddy.paymybuddyapp.dao;

import com.paymybuddy.paymybuddyapp.model.Account;

public interface IAccountRepository {

	//public Account addAccount(Account account);
	
	public int getId(Account account);
	
	public Account getAccount(int id);
	
	public int getId(String email);
	
}
