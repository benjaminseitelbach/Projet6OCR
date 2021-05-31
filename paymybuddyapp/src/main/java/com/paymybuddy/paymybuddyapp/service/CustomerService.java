package com.paymybuddy.paymybuddyapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddyapp.dao.ICustomerRepository;
import com.paymybuddy.paymybuddyapp.dao.CustomerRepository;
import com.paymybuddy.paymybuddyapp.dao.BankAccountRepository;
import com.paymybuddy.paymybuddyapp.dao.IRelationshipRepository;
import com.paymybuddy.paymybuddyapp.dao.RelationshipRepository;
import com.paymybuddy.paymybuddyapp.dao.TransactionRepository;
import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Relationship;
import com.paymybuddy.paymybuddyapp.model.Transaction;

@Service
public class CustomerService implements ICustomerService {
	
	@Autowired
	ICustomerRepository accountRepository; 
	
	//@Autowired
	//RelationshipDao relationshipDao;
	
	CustomerRepository accountDaoImpl = new CustomerRepository();
	
	RelationshipRepository relationshipDaoImpl = new RelationshipRepository();
	
	
	TransactionRepository transactionDaoImpl = new TransactionRepository();
	
	public Customer getAccount(int id) {
		return accountDaoImpl.getAccount(id);
	}
	
	public Customer getAccount(String email, String password) {
		return accountDaoImpl.getAccount(email, password);
	}
	
	public int getId(String firstName, String lastName) {
		return accountDaoImpl.getId(firstName, lastName);
	}
	
	public int getId(Customer account) {
		return accountDaoImpl.getId(account);
	}
	
	public Customer addRelationship(Customer account, Customer relationshipAccount) {
		int accountId = accountDaoImpl.getId(account);
		int relationshipId = accountDaoImpl.getId(relationshipAccount);
		Relationship relationship = new Relationship();
		relationship.setAccountId(accountId);
		relationship.setRelationshipId(relationshipId);
		relationshipDaoImpl.addRelationship(relationship);
		account.addRelationship(relationship);
		//return accountDao.getAccount(account);
		return account;	
	}
	
	public Customer addConnection(Customer account, String connectionEmail) {
		int accountId = accountDaoImpl.getId(account);
		int connectionId = accountDaoImpl.getId(connectionEmail);
		Relationship relationship = relationshipDaoImpl.addConnection(accountId, connectionId);
		account.addRelationship(relationship);
		return account;
	}
	
	
	
	
		
	
	
	
	
	
	
	
	
}
