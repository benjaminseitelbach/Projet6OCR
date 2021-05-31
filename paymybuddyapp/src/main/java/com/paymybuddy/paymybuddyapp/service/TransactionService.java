package com.paymybuddy.paymybuddyapp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.paymybuddyapp.dao.ICustomerRepository;
import com.paymybuddy.paymybuddyapp.dao.CustomerRepository;
import com.paymybuddy.paymybuddyapp.dao.IRelationshipRepository;
import com.paymybuddy.paymybuddyapp.dao.ITransactionRepository;
import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Relationship;
import com.paymybuddy.paymybuddyapp.model.Transaction;

@Service
public class TransactionService implements ITransactionService {
	
	@Autowired
	private ICustomerRepository accountRepository;
	
	@Autowired
	private IRelationshipRepository relationshipRepository;
	
	@Autowired
	private ITransactionRepository transactionRepository;
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	//@Transactional
	public boolean sendMoney(Customer customer, int connectionId, BigDecimal amount) {
		int customerId = customerRepository.getId(customer.getEmail());
		
		BigDecimal amountWithTax = amount.add(new BigDecimal(0.005).multiply(amount));
		BigDecimal customerAmount = customer.getAmount();
		
		if(customerAmount.compareTo(amountWithTax) == 1) {

			int relationshipId = relationshipRepository.getId(customerId, connectionId);

			BigDecimal customerNewAmount = customerAmount.subtract(amountWithTax);
			System.out.println("customer new amount: " + customerNewAmount.floatValue());
			customerRepository.updateAmount(customerId, customerNewAmount);
			
			Customer connection = customerRepository.getCustomer(connectionId);
			BigDecimal connectionNewAmount = connection.getAmount().add(amount);
			customerRepository.updateAmount(connectionId, connectionNewAmount);
			
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			//Date date = new Date();
			Date date = new Date(Calendar.getInstance().getTime().getTime());
			transaction.setDescription("Transaction done on " + date);
			transaction.setDate(date);
			transaction.setRelationshipId(relationshipId);
			transaction.setSender(customer);
			transaction.setReceiver(connection);
			transactionRepository.addTransaction(transaction);
			customer.addTransaction(transaction);
			return true;
		}
		return false;
		
		
	}
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
	public List<Transaction> getTransactions(Customer account) {
		List<Transaction> result = new ArrayList<>();
		int accountId = accountRepository.getId(account);
		List<Integer> relationshipsIds = relationshipRepository.getRelationshipsIds(accountId);

		for(Integer relationshipId : relationshipsIds) {
			List<Transaction> transactions = transactionRepository.getTransactions(relationshipId);
			int connectionId = relationshipRepository.getConnectionId(relationshipId);
			Customer connection = accountRepository.getAccount(connectionId);
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
		
		Customer connectionAccount = accountRepository.getAccount(transaction.getRelationship().getConnectionId());
		int connectionId = accountRepository.getId(connectionAccount);
		BankAccount connectionBankAccount = bankAccountRepository.getBankAccount("PayMyBuddy", connectionId);
		int connectionBankAccountId = bankAccountRepository.getId(connectionBankAccount, connectionId);
		BigDecimal connectionNewAmount = connectionBankAccount.getAmount().add(transactionAmount); 
		
		bankAccountRepository.updateAmount(connectionBankAccountId, connectionNewAmount);
		//BankAccount relationshipBankAccount = relationshipAccount.getBankAccount("PayMyBuddy");
		//relationshipBankAccount.setAmount(relationshipBankAccount.getAmount() + transactionAmount);
		return transaction;
		
	}
	
	public String getFirstName(String fullName) {
		String firstName = "";
		for(int iCharacter = 0; iCharacter < fullName.length(); iCharacter++) {
			char character = fullName.charAt(iCharacter);
			if(character != ' ') {
				firstName += character;
			} else {
				iCharacter = fullName.length();
			}
		}
		return firstName;
	}
	
	public String getLastName(String fullName) {
		String lastName = "";
		boolean space = false;
		for(int iCharacter = 0; iCharacter < fullName.length(); iCharacter++) {
			char character = fullName.charAt(iCharacter);
			if(character != ' ') {
				if(space) {
					lastName += character;
				}
				
			} else {
				space = true;				
			}
		}
		return lastName;
	}
}
