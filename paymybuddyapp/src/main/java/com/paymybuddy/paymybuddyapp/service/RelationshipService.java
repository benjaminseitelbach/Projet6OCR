package com.paymybuddy.paymybuddyapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddyapp.dao.ICustomerRepository;
import com.paymybuddy.paymybuddyapp.dao.IRelationshipRepository;
import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Relationship;

@Service
public class RelationshipService implements IRelationshipService {
	
	@Autowired
	private IRelationshipRepository relationshipRepository;
	
	@Autowired 
	private ICustomerRepository accountRepository;
	
	public List<Customer> getConnections(Customer account) {
		int accountId = accountRepository.getId(account);
		List<Integer> connectionsIds = relationshipRepository.getConnectionsIds(accountId);
			
		List<Customer> accounts = new ArrayList<>();
		for(Integer connectionId : connectionsIds) {
			Customer correspondingAccount = accountRepository.getAccount(connectionId);
			accounts.add(correspondingAccount);
		}
		return accounts;
		
	}
	
	public Customer addConnection(Customer account, String connectionEmail) {
		int accountId = accountRepository.getId(account);
		int connectionId = accountRepository.getId(connectionEmail);
		Relationship relationship = relationshipRepository.addConnection(accountId, connectionId);
		account.addRelationship(relationship);
		return account;
	}
}
