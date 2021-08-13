package com.paymybuddy.paymybuddyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.service.ICustomerService;


import com.paymybuddy.paymybuddyapp.service.ITransactionService;

@Controller
public class PayMyBuddyController {
	
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private ITransactionService transactionService;
	
	private Customer currentCustomer;
	
	/**
     * Returns sign in page
     *
     * @return sign in page
     */	
	@GetMapping("/")
	public String home() {
	    return "signIn";
	}
	
	/**
     * Returns sign in page
     *
     * @return sign in page
     */	
	@GetMapping("/signIn")
	public String accessSignInPage() {
	    return "signIn";
	}
	
	/**
     * Returns sign up page
     *
     * @return sign up page
     */	
	@GetMapping("/signUp")
	public String accessSignUpPage() {
		return "signUp";
	}
	
	/**
     * Access transfer page by sign up: if email doesn't already exists, customer is registered and
     * returns transfer page and if not, print message "Email already exists"
     *
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     * @param model
     *
     * @return transfer page or sign up page with message "Email already exists"
     */	
	@PostMapping("/signUp")
	public String accessTransferPageBySignUp(@RequestParam(name="email") String email, 
			@RequestParam(name="password") String password, @RequestParam(name="firstName") String firstName,
			@RequestParam(name="lastName") String lastName, Model model) {
			
		currentCustomer = new Customer();
		currentCustomer.setEmail(email);
		currentCustomer.setPassword(password);
		currentCustomer.setFirstName(firstName);
		currentCustomer.setLastName(lastName);
		currentCustomer.setAmount(0);
		currentCustomer = customerService.addCustomer(currentCustomer);
		if(currentCustomer != null) {
			model.addAttribute("customer", currentCustomer);
			model.addAttribute("connections", currentCustomer.getConnections());
			
			model.addAttribute("transactions", transactionService.getTransactions(currentCustomer));
			return "transfer";
		} else {
			model.addAttribute("signUpMessage", "Email already exists");
			return "signUp";
		}
		
				
	}
	
	/**
     * Sign in: if email and password corresponds, returns transfer page and if not, 
     * print message "Wrong email or password" 
     *
     * @param email
     * @param password
     * @param model
     *
     * @return transfer page or sign in page with message "Wrong email or password"
     */		
	@PostMapping("/signIn")
	public String login(@RequestParam(name="email") String email, @RequestParam(name="password") String password,
			Model model) {
		currentCustomer = customerService.authenticate(email, password);
		
		if(currentCustomer != null) {
			model.addAttribute("customer", currentCustomer);
			
			model.addAttribute("connections", currentCustomer.getConnections());
			
			model.addAttribute("transactions", transactionService.getTransactions(currentCustomer));
		    
		    
		    return "transfer";
		} else {
			model.addAttribute("signInMessage", "Wrong email or password");
			
			return "signIn";
		}
		
	}
	
	/**
     * Add connection: add corresponding email as a connection id order to transfer money to it
     *
     * @param connectionEmail
     * @param model
     *
     * @return transfer page
     */	
	@PostMapping("/addConnection")
	public String addConnection(@RequestParam(name="connectionEmail") String connectionEmail, Model model) {
		boolean result = customerService.addConnection(currentCustomer, connectionEmail);
		if(result) {
			model.addAttribute("addingConnectionMessage", connectionEmail + " added");
		} else {
			model.addAttribute("addingConnectionMessage", connectionEmail + " not found");
		}
		model.addAttribute("customer", currentCustomer);
		model.addAttribute("connections", currentCustomer.getConnections());
		
		model.addAttribute("transactions", transactionService.getTransactions(currentCustomer));
		return "transfer";
	}
		
	/**
     * Send money 
     *
     * @param connection ID
     * @param amount
     * @param model
     *
     * @return transfer page
     */	
	@PostMapping("/sendMoney") 
	public String sendMoney(@RequestParam(name="connection") int connectionId,
			@RequestParam(name="amount") double amount, @RequestParam(name="description") String description,
			Model model) {

		boolean result = transactionService.sendMoney(currentCustomer, connectionId, amount, description);
		if(result) {
			model.addAttribute("message", "Transaction done");
		} else {
			model.addAttribute("message", "Transaction failed");
		}
		model.addAttribute("customer", currentCustomer);
		model.addAttribute("connections", currentCustomer.getConnections());
		model.addAttribute("transactions", transactionService.getTransactions(currentCustomer));
		return "transfer";
	}
	
	/**
     * Access profile page
     *
     * @param model
     *
     * @return profile page
     */	
	@GetMapping("/profile")
	public String accessProfilePage(Model model) {
		model.addAttribute("customer", currentCustomer);
		return "profile";
	}
	
	/**
     * Add bank account: add corresponding iban and amount as a bank account for current customer
     *
     * @param iban
     * @param amount
     * @param model
     *
     * @return profile page
     */	
	@PostMapping("/addBankAccount")
	public String addBankAccount(@RequestParam(name="newBankAccountIban") String iban, 
			@RequestParam(name="newBankAccountAmount") double amount, Model model) {
		currentCustomer = customerService.addBankAccount(currentCustomer, iban, amount);
		model.addAttribute("customer", currentCustomer);
		return "profile";
		
	}
	
	/**
     * Add to PayMyBuddy: add amount entered to PayMyBuddy account
     *
     * @param amount
     * @param model
     *
     * @return profile page
     */	
	@PostMapping("/addToPayMyBuddy")
	public String addToPayMyBuddy(@RequestParam(name="amountAddedToPayMyBuddy") double amount, Model model) {
		boolean result = customerService.sendToPayMyBuddy(currentCustomer, amount);
		if(result) {
			model.addAttribute("bankAccountToPayMyBuddyMessage", "Transaction done");
		} else {
			model.addAttribute("bankAccountToPayMyBuddyMessage", "Transaction failed");
		}
		model.addAttribute("customer", currentCustomer);
		return "profile";
		
	}
	
	/**
     * Recover to bank account: recover amount entered to bank account
     *
     * @param amount
     * @param model
     *
     * @return profile page
     */	
	@PostMapping("/recoverToBankAccount")
	public String recoverToBankAccount(@RequestParam(name="amountRecoveredToBankAccount") double amount, Model model) {
		boolean result = customerService.recoverToBankAccount(currentCustomer, amount);
		if(result) {
			model.addAttribute("payMyBuddyToBankAccountMessage", "Transaction done");
		} else {
			model.addAttribute("payMyBuddyToBankAccountMessage", "Transaction failed");
		}
		model.addAttribute("customer", currentCustomer);
		return "profile";
		
	}
	
	/**
     * Access tranfer page
     *
     * @param model
     *
     * @return transfer page
     */	
	@GetMapping("/transfer")
	public String accessTransferPage(Model model) {
		model.addAttribute("customer", currentCustomer);
		model.addAttribute("connections", currentCustomer.getConnections());
		model.addAttribute("transactions", transactionService.getTransactions(currentCustomer));
		return "transfer";
	}
		
}
