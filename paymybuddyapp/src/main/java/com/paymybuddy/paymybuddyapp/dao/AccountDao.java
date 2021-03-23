package com.paymybuddy.paymybuddyapp.dao;

import com.paymybuddy.paymybuddyapp.model.Account;

public interface AccountDao {

	public Account addAccount(Account account);
	
	public int getId(Account account);
	
}
