package com.paymybuddy.paymybuddyapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddyapp.dao.IAccountRepository;
import com.paymybuddy.paymybuddyapp.dao.AccountRepository;
import com.paymybuddy.paymybuddyapp.dao.BankAccountRepository;
import com.paymybuddy.paymybuddyapp.dao.IRelationshipRepository;
import com.paymybuddy.paymybuddyapp.dao.RelationshipRepository;
import com.paymybuddy.paymybuddyapp.dao.TransactionRepository;
import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Relationship;
import com.paymybuddy.paymybuddyapp.model.Transaction;

@Service
public class AccountService implements IAccountService {
	
	@Autowired
	IAccountRepository accountRepository; 
	
	//@Autowired
	//RelationshipDao relationshipDao;
	
	AccountRepository accountDaoImpl = new AccountRepository();
	
	RelationshipRepository relationshipDaoImpl = new RelationshipRepository();
	
	BankAccountRepository bankAccountDaoImpl = new BankAccountRepository();
	
	TransactionRepository transactionDaoImpl = new TransactionRepository();
	
	public void deleteAccount(int id) {
		accountDaoImpl.deleteAccount(id);
	}
	
	public Account getAccount(int id) {
		return accountDaoImpl.getAccount(id);
	}
	
	public Account getAccount(String email, String password) {
		return accountDaoImpl.getAccount(email, password);
	}
	
	public int getId(String firstName, String lastName) {
		return accountDaoImpl.getId(firstName, lastName);
	}
	
	public int getId(Account account) {
		return accountDaoImpl.getId(account);
	}
	
	public Account addRelationship(Account account, Account relationshipAccount) {
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
	
	public Account addConnection(Account account, String connectionEmail) {
		int accountId = accountDaoImpl.getId(account);
		int connectionId = accountDaoImpl.getId(connectionEmail);
		Relationship relationship = relationshipDaoImpl.addConnection(accountId, connectionId);
		account.addRelationship(relationship);
		return account;
	}
	
	
	
	
		
	
	
	
	
	
	
	
	
}
