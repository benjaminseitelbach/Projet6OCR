package com.paymybuddy.paymybuddyapp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddyapp.dao.IAccountRepository;
import com.paymybuddy.paymybuddyapp.dao.IBankAccountRepository;
import com.paymybuddy.paymybuddyapp.dao.IRelationshipRepository;
import com.paymybuddy.paymybuddyapp.dao.ITransactionRepository;
import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Relationship;
import com.paymybuddy.paymybuddyapp.model.Transaction;

@Service
public class TransactionService implements ITransactionService {
	
	@Autowired
	private IAccountRepository accountRepository;
	
	@Autowired
	private IRelationshipRepository relationshipRepository;
	
	@Autowired
	private ITransactionRepository transactionRepository;
	
	@Autowired
	private IBankAccountRepository bankAccountRepository;
	/*
	public List<Transaction> getEmittedTransactions(Account account) {
		int accountId = accountRepository.getId(account);
		List<Integer> relationshipsIds = relationshipRepository.getRelationships(accountId);
		List<Transaction> result = new ArrayList<>();
		for(Relationship relationship : relationships) {
			List<Transaction> transactions = transactionRepository.getTransactions(relationship.getId());
			for(Transaction transaction : transactions) {
				transaction.setRelationship(relationship);
				BankAccount bankAccount = bankAccountRepository.getBankAccount(transaction.getBankAccountId());
				transaction.setBankAccount(bankAccount);
			}
			result.addAll(transactions);			
		}
		return result;
	}
	*/
	public List<Transaction> getTransactions(Account account) {
		List<Transaction> result = new ArrayList<>();
		int accountId = accountRepository.getId(account);
		List<Integer> relationshipsIds = relationshipRepository.getRelationshipsIds(accountId);

		for(Integer relationshipId : relationshipsIds) {
			List<Transaction> transactions = transactionRepository.getTransactions(relationshipId);
			int connectionId = relationshipRepository.getConnectionId(relationshipId);
			Account connection = accountRepository.getAccount(connectionId);
			for(Transaction transaction : transactions) {
				transaction.setConnection(connection);
				result.add(transaction);
			}
			
		}
		return result;
	}
	
	
	//@Transactional
	public Transaction addTransaction(Transaction transaction) {
		int relationshipId = relationshipRepository.getId(transaction.getRelationship());
		int bankAccountId = bankAccountRepository.getId(transaction.getBankAccount(), transaction.getRelationship().getAccountId());
		
		//add transaction
		transaction = transactionRepository.addTransaction(transaction, relationshipId, bankAccountId);
		
		BigDecimal transactionAmount = transaction.getAmount();
		BigDecimal accountNewAmount = bankAccountRepository.getAmount(bankAccountId).subtract(transactionAmount);
		
		//update account amount
		//bankAccount.setAmount(accountNewAmount);
		bankAccountRepository.updateAmount(bankAccountId, accountNewAmount);
		
		Account connectionAccount = accountRepository.getAccount(transaction.getRelationship().getConnectionId());
		int connectionId = accountRepository.getId(connectionAccount);
		BankAccount connectionBankAccount = bankAccountRepository.getBankAccount("PayMyBuddy", connectionId);
		int connectionBankAccountId = bankAccountRepository.getId(connectionBankAccount, connectionId);
		BigDecimal connectionNewAmount = connectionBankAccount.getAmount().add(transactionAmount); 
		
		bankAccountRepository.updateAmount(connectionBankAccountId, connectionNewAmount);
		//BankAccount relationshipBankAccount = relationshipAccount.getBankAccount("PayMyBuddy");
		//relationshipBankAccount.setAmount(relationshipBankAccount.getAmount() + transactionAmount);
		return transaction;
		
	}
}
