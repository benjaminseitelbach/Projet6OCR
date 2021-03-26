package com.paymybuddy.paymybuddyapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.paymybuddy.paymybuddyapp.dao.AccountDao;
import com.paymybuddy.paymybuddyapp.dao.AccountDaoImpl;
import com.paymybuddy.paymybuddyapp.dao.BankAccountDaoImpl;
import com.paymybuddy.paymybuddyapp.dao.RelationshipDao;
import com.paymybuddy.paymybuddyapp.dao.RelationshipDaoImpl;
import com.paymybuddy.paymybuddyapp.dao.TransactionDaoImpl;
import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Relationship;
import com.paymybuddy.paymybuddyapp.model.Transaction;

public class AccountService {
	/*
	@Autowired
	AccountDao accountDao; 
	
	@Autowired
	RelationshipDao relationshipDao;
	*/
	AccountDaoImpl accountDaoImpl = new AccountDaoImpl();
	
	RelationshipDaoImpl relationshipDaoImpl = new RelationshipDaoImpl();
	
	BankAccountDaoImpl bankAccountDaoImpl = new BankAccountDaoImpl();
	
	TransactionDaoImpl transactionDaoImpl = new TransactionDaoImpl();
	
	public Account addAccount(Account account) {
		account  = accountDaoImpl.addAccount(account);
		BankAccount payMyBuddyBankAccount = new BankAccount();
		payMyBuddyBankAccount.setIBAN("PayMyBuddy");
		payMyBuddyBankAccount.setAmount(0);
		account = addBankAccount(account, payMyBuddyBankAccount);
		return account;
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
	
	public List<Account> getRelationships(Account account) {
		int accountId = accountDaoImpl.getId(account);
		List<Integer> relationshipsIds = relationshipDaoImpl.getRelationshipsIds(accountId);	
		List<Account> accounts = accountDaoImpl.getAccounts(relationshipsIds);
		System.out.println("accounts:" + accounts.toString());
		return accounts;
		
	}
		
	public Account addBankAccount(Account account, BankAccount bankAccount) {
		int accountId = accountDaoImpl.getId(account);
		bankAccountDaoImpl.addBankAccount(accountId, bankAccount);
		account.addBankAccount(bankAccount);
		return account;
		
	}
	
	public Transaction addTransaction(Transaction transaction, Relationship relationship, BankAccount bankAccount) {
		int relationshipId = relationshipDaoImpl.getId(relationship);
		int bankAccountId = bankAccountDaoImpl.getId(bankAccount, relationship.getAccountId());
		
		//add transaction
		transaction = transactionDaoImpl.addTransaction(transaction, relationshipId, bankAccountId);
		
		float transactionAmount = transaction.getAmount();
		float accountNewAmount = bankAccount.getAmount() - transactionAmount;
		
		//update account amount
		//bankAccount.setAmount(accountNewAmount);
		bankAccountDaoImpl.updateAmount(bankAccountId, accountNewAmount);
		
		Account relationshipAccount = accountDaoImpl.getAccount(relationship.getRelationshipId());
		int relationshipAccountId = accountDaoImpl.getId(relationshipAccount);
		BankAccount relationshipBankAccount = bankAccountDaoImpl.getBankAccount("PayMyBuddy", relationshipAccountId);
		int relationshipBankAccountId = bankAccountDaoImpl.getId(relationshipBankAccount, relationshipAccountId);
		float relationshipNewAmount = relationshipBankAccount.getAmount() + transactionAmount;
		
		bankAccountDaoImpl.updateAmount(relationshipBankAccountId, relationshipNewAmount);
		//BankAccount relationshipBankAccount = relationshipAccount.getBankAccount("PayMyBuddy");
		//relationshipBankAccount.setAmount(relationshipBankAccount.getAmount() + transactionAmount);
		return transaction;
		
	}
	
	public List<Transaction> getEmittedTransactions(Account account) {
		int accountId = accountDaoImpl.getId(account);
		List<Relationship> relationships = relationshipDaoImpl.getRelationships(accountId);
		List<Transaction> result = new ArrayList<>();
		for(Relationship relationship : relationships) {
			List<Transaction> transactions = transactionDaoImpl.getTransactions(relationship.getId());
			for(Transaction transaction : transactions) {
				transaction.setRelationship(relationship);
				BankAccount bankAccount = bankAccountDaoImpl.getBankAccount(transaction.getBankAccountId());
				transaction.setBankAccount(bankAccount);
			}
			result.addAll(transactions);			
		}
		return result;
	}
	
	
	
	
}
