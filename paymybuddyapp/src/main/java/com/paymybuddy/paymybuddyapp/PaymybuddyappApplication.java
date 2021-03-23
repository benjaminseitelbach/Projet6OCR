package com.paymybuddy.paymybuddyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.paymybuddyapp.dao.AccountDaoImpl;
import com.paymybuddy.paymybuddyapp.model.Account;
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
		//accountService.addAccount(account2);
		//accountService.addRelationship(account2, account1);
		System.out.println(accountService.getRelationships(account1).toString());
		//accountDaoImpl.addAccount(account1);
		SpringApplication.run(PaymybuddyappApplication.class, args);
	}

}
