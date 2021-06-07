package com.paymybuddy.paymybuddyapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.paymybuddyapp.model.Transaction;
import com.paymybuddy.paymybuddyapp.service.IAuthenticationService;
import com.paymybuddy.paymybuddyapp.service.ITransactionService;


@SpringBootApplication
public class PaymybuddyappApplication implements CommandLineRunner {

	@Autowired
	private IAuthenticationService authenticationService;
	
	@Autowired
	private ITransactionService transactionService;
	
	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyappApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		/*
		List<Transaction> transactions = transactionService.getAllTransactions();
		for(Transaction transaction : transactions) {
			System.out.println(transaction.toString());
		}
		*/
		
	}

}
