package com.paymybuddy.paymybuddyapp.controller;

import java.math.BigDecimal;
import java.util.List;

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
	
	@GetMapping("/")
	public String home() {
	    return "signIn";
	}
	
	@GetMapping("/list")
	public void list() {
	    List<Customer> customers = authenticationService.getAllCustomers();
		for(Customer customer : customers) {
			System.out.println(customer.toString());
		}
	}
	
	@PostMapping("/signIn")
	public String transfer(@RequestParam(name="email") String email, @RequestParam(name="password") String password,
			Model model) {
		currentCustomer = authenticationService.authenticate(email, password);
		
		model.addAttribute("connections", currentCustomer.getConnections());
		

		model.addAttribute("transactions", transactionService.getTransactions(currentCustomer));
	    
	    
	    return "transfer";
	}
	
	
	@PostMapping("/sendMoney") 
	public String sendMoney(@RequestParam(name="connection") int connectionId,
			@RequestParam(name="money") BigDecimal money, Model model) {

		boolean result = transactionService.sendMoney(currentCustomer, connectionId, money);
		if(result) {
			
		}
		
		model.addAttribute("connections", currentCustomer.getConnections());
		model.addAttribute("transactions", transactionService.getTransactions(currentCustomer));
		return "transfer";
	}
	
	
	
	
}
