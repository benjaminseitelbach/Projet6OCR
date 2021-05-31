package com.paymybuddy.paymybuddyapp.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Relationship;
import com.paymybuddy.paymybuddyapp.model.Transaction;
import com.paymybuddy.paymybuddyapp.service.ICustomerService;
import com.paymybuddy.paymybuddyapp.service.IAuthenticationService;
import com.paymybuddy.paymybuddyapp.service.IBankAccountService;
import com.paymybuddy.paymybuddyapp.service.IRelationshipService;
import com.paymybuddy.paymybuddyapp.service.ITransactionService;

@Controller
public class PayMyBuddyController {
	

	@Autowired
	private ICustomerService accountService;
	
	@Autowired
	private IRelationshipService relationshipService;
	
	@Autowired
	private ITransactionService transactionService;
	
	@Autowired
	private IAuthenticationService authenticationService;
	
	private Customer currentCustomer;
	
	@GetMapping("/")
	public String home() {
	    return "signIn";
	}
	
	@PostMapping("/signIn")
	public String transfer(@RequestParam(name="email") String email, @RequestParam(name="password") String password,
			Model model) {
		currentCustomer = authenticationService.authenticate(email, password);

	    //model.addAttribute("connections", authenticateUserDetails.getConnections);
		model.addAttribute("connections", currentCustomer.getConnections());
		
		//model.addAttribute("transactions", transactionService.getTransactions(currentCustomer));
		model.addAttribute("transactions", currentCustomer.getTransactions());
	    
	    
	    return "transfer";
	}
	
	@PostMapping("/sendMoney") 
	public String sendMoney(@RequestParam(name="connection") int connectionId,
			@RequestParam(name="money") BigDecimal money, Model model) {

		boolean result = transactionService.sendMoney(currentCustomer, connectionId, money);
		if(result) {
			
		}
		
		model.addAttribute("connections", currentCustomer.getConnections());
		model.addAttribute("transactions", currentCustomer.getTransactions());
		return "transfer";
	}
	
	
	
}
