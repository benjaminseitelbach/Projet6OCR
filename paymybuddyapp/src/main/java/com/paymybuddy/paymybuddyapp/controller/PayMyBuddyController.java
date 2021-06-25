package com.paymybuddy.paymybuddyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.service.IAuthenticationService;


import com.paymybuddy.paymybuddyapp.service.ITransactionService;

@Controller
public class PayMyBuddyController {
	
	@Autowired
	private IAuthenticationService authenticationService;
	
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
	public String transfer(@RequestParam(name="email") String email, @RequestParam(name="password") String password,
			Model model) {
		currentCustomer = authenticationService.authenticate(email, password);
		
		if(currentCustomer != null) {
			model.addAttribute("connections", currentCustomer.getConnections());
			

			model.addAttribute("transactions", transactionService.getTransactions(currentCustomer));
		    
		    
		    return "transfer";
		} else {
			model.addAttribute("signInMessage", "Wrong email or password");
			
			return "signIn";
		}
		
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
			@RequestParam(name="amount") double amount, Model model) {

		boolean result = transactionService.sendMoney(currentCustomer, connectionId, amount);
		if(result) {
			model.addAttribute("message", "Transaction done");
		} else {
			model.addAttribute("message", "Transaction failed");
		}
		
		model.addAttribute("connections", currentCustomer.getConnections());
		model.addAttribute("transactions", transactionService.getTransactions(currentCustomer));
		return "transfer";
	}
	
	
	
	
}
