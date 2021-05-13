package com.paymybuddy.paymybuddyapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddyapp.dao.IAccountRepository;
import com.paymybuddy.paymybuddyapp.dao.IRelationshipRepository;
import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.Relationship;

@Service
public class RelationshipService implements IRelationshipService {
	
	@Autowired
	private IRelationshipRepository relationshipRepository;
	
	@Autowired 
	private IAccountRepository accountRepository;
	
	public List<Account> getConnections(Account account) {
		int accountId = accountRepository.getId(account);
		List<Integer> connectionsIds = relationshipRepository.getConnectionsIds(accountId);
			
		List<Account> accounts = new ArrayList<>();
		for(Integer connectionId : connectionsIds) {
			Account correspondingAccount = accountRepository.getAccount(connectionId);
			accounts.add(correspondingAccount);
		}
		return accounts;
		
	}
	
	public Account addConnection(Account account, String connectionEmail) {
		int accountId = accountRepository.getId(account);
		int connectionId = accountRepository.getId(connectionEmail);
		Relationship relationship = relationshipRepository.addConnection(accountId, connectionId);
		account.addRelationship(relationship);
		return account;
	}
}
