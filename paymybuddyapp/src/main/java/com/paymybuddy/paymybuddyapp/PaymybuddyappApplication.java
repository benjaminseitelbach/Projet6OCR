package com.paymybuddy.paymybuddyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.paymybuddyapp.dao.AccountDaoImpl;
import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Relationship;
import com.paymybuddy.paymybuddyapp.model.Transaction;
import com.paymybuddy.paymybuddyapp.service.AccountService;

@SpringBootApplication
public class PaymybuddyappApplication {

	public static void main(String[] args) {
		AccountService accountService = new AccountService();
		//AccountDaoImpl accountDaoImpl = new AccountDaoImpl();
		Account account1 = new Account();
		account1.setEmail("cacahuete22@gmail.com");
		account1.setPassword("cacahuete22");
		account1.setFirstName("Florian");
		account1.setLastName("Dieu");
		
		Account account2 = new Account();
		account2.setEmail("noixdepecan33@gmail.com");
		account2.setPassword("cacahuetesalée");
		account2.setFirstName("Clément");
		account2.setLastName("Boursault");
		
		BankAccount bankAccount = new BankAccount();
		bankAccount.setIBAN("FR2012");
		bankAccount.setAmount(4000);
		
		Transaction transaction = new Transaction();
		transaction.setAmount(10);
		transaction.setDescription("Kebab");
		
		accountService.addAccount(account1);
		accountService.addAccount(account2);
		account1 = accountService.addBankAccount(account1, bankAccount);
		account1 = accountService.addRelationship(account1, account2);
		Relationship relationship = account1.getRelationships().get(0);
		accountService.addTransaction(transaction, relationship, bankAccount);
		System.out.println(accountService.getEmittedTransactions(account1).toString());
		//System.out.println(accountService.getRelationships(account1).toString());
		//accountDaoImpl.addAccount(account1);
		SpringApplication.run(PaymybuddyappApplication.class, args);
	}

}
