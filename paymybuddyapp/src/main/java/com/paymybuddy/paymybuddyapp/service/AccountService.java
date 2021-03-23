package com.paymybuddy.paymybuddyapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.paymybuddy.paymybuddyapp.dao.AccountDao;
import com.paymybuddy.paymybuddyapp.dao.AccountDaoImpl;
import com.paymybuddy.paymybuddyapp.dao.RelationshipDao;
import com.paymybuddy.paymybuddyapp.dao.RelationshipDaoImpl;
import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.Relationship;

public class AccountService {
	/*
	@Autowired
	AccountDao accountDao; 
	
	@Autowired
	RelationshipDao relationshipDao;
	*/
	AccountDaoImpl accountDaoImpl = new AccountDaoImpl();
	
	RelationshipDaoImpl relationshipDaoImpl = new RelationshipDaoImpl();
	
	public Account addAccount(Account account) {
		return accountDaoImpl.addAccount(account);
	}
	
	public Account addRelationship(Account account, Account relationshipAccount) {
		int accountId = accountDaoImpl.getId(account);
		int relationshipId = accountDaoImpl.getId(relationshipAccount);
		Relationship relationship = new Relationship();
		relationship.setAccountId(accountId);
		relationship.setRelationshipId(relationshipId);
		relationshipDaoImpl.addRelationship(relationship);
		//return accountDao.getAccount(account);
		return account;	
	}
	
	public List<Account> getRelationships(Account account) {
		int accountId = accountDaoImpl.getId(account);
		List<Integer> relationshipsIds = relationshipDaoImpl.getRelationshipsIds(accountId);	
		List<Account> accounts = accountDaoImpl.getAccounts(relationshipsIds);
		System.out.println("accounts:" + accounts.toString());
		return accounts;
		
	}
	/*
	public BankAccount addBankAccount(Account account, BankAccount bankAccount) {
		
		
	}
	*/
	
	
	
}
