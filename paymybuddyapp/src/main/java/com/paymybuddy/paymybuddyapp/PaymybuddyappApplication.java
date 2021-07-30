package com.paymybuddy.paymybuddyapp;

import org.mindrot.jbcrypt.BCrypt;
import static java.lang.System.out;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.service.IAuthenticationService;
import com.paymybuddy.paymybuddyapp.service.IBankAccountService;

@SpringBootApplication
public class PaymybuddyappApplication implements CommandLineRunner {
	
	@Autowired
	private IAuthenticationService authenticationService;
	
	@Autowired
	private IBankAccountService bankAccountService;
	
	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyappApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		/*
		Customer customer = new Customer();
		customer.setEmail("jaboyd@email.com");
		customer.setPassword("password1");
		*/
		//BankAccount bankAccount = bankAccountService.getBankAccountById(1);
		//System.out.println(bankAccount.toString());
		//Customer customer = authenticationService.authenticate("jaboyd@email.com", "password1");
		//BankAccount bankAccount = customer.getBankAccount();
		//String iban = bankAccount.getIban();
		//System.out.println(customer.toString());
		//System.out.println(customer.toString());
	}

}
