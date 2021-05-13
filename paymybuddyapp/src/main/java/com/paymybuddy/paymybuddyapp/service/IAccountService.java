package com.paymybuddy.paymybuddyapp.service;

import com.paymybuddy.paymybuddyapp.model.Account;

public interface IAccountService {
	
	public Account getAccount(String email, String password);
	
	public int getId(Account account);
	
	public int getId(String email, String password);
}
