package com.paymybuddy.paymybuddyapp.service;

import java.util.List;

import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.Relationship;

public interface IRelationshipService {

	public List<Account> getConnections(Account account);
	
	public Account addConnection(Account account, String email);
	
}
