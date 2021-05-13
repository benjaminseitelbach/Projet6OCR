package com.paymybuddy.paymybuddyapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Transaction;
import com.paymybuddy.paymybuddyapp.service.IAccountService;
import com.paymybuddy.paymybuddyapp.service.IBankAccountService;
import com.paymybuddy.paymybuddyapp.service.IRelationshipService;
import com.paymybuddy.paymybuddyapp.service.ITransactionService;

@Controller
public class LoginController {
	

	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IRelationshipService relationshipService;
	
	@Autowired
	private IBankAccountService bankAccountService;
	
	@Autowired
	private ITransactionService transactionService;
	
	private Account currentAccount;
	
	@GetMapping("/")
	public String signIn() {
	    return "signIn";
	}
	
	@PostMapping("/signIn")
	public String homeAccount(@RequestParam(name="email") String email, @RequestParam(name="password") String password,
			Model model) {
		currentAccount = accountService.getAccount(email, password);
		model.addAttribute("account", currentAccount);
	    List<Account> relationships = relationshipService.getConnections(currentAccount);
	    model.addAttribute("relationships", relationships);
	    
	    List<BankAccount> bankAccounts = bankAccountService.getBankAccounts(currentAccount);
	    model.addAttribute("bankAccounts", bankAccounts);
	    
	    List<Transaction> transactions = transactionService.getTransactions(currentAccount);
	    model.addAttribute("transactions", transactions);
	    
	    return "transfer";
	}
	
}
