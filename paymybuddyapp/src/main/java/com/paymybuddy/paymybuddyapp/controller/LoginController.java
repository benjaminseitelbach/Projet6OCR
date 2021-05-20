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

import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Relationship;
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
	public String home() {
	    return "signIn";
	}
	
	@PostMapping("/signIn")
	public String transfer(@RequestParam(name="email") String email, @RequestParam(name="password") String password,
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
	
	@PostMapping("/addConnection")
	public String addConnection(@RequestParam(name="email") String email, Model model) {
		relationshipService.addConnection(currentAccount, email);
		
		//model.addAttribute("account", currentAccount);
	    List<Account> relationships = relationshipService.getConnections(currentAccount);
	    model.addAttribute("relationships", relationships);
	    /*
	    List<BankAccount> bankAccounts = accountService.getBankAccounts(currentAccount);
	    model.addAttribute("bankAccounts", bankAccounts);
	    */
	    
	    List<Transaction> transactions = transactionService.getTransactions(currentAccount);
	    model.addAttribute("transactions", transactions);
	    
		//System.out.println(currentAccount.toString());
		return "transfer";
	}
	
	@PostMapping("/sendMoney") 
	public String sendMoney(@RequestParam(name="relationship") String sRelationship,
			@RequestParam(name="money") BigDecimal money, Model model) {
		//System.out.println("relationship:" + relationship);
		//System.out.println("first name:" + getFirstName(relationship));
		//String firstName = getFirstName(relationship)
		//System.out.println("last name:" + getLastName(relationship));
		String firstName = getFirstName(sRelationship);
		String lastName = getLastName(sRelationship);
		int connectionId = accountService.getId(firstName, lastName);
		Relationship relationship = new Relationship();
		relationship.setConnectionId(connectionId);
		relationship.setAccountId(accountService.getId(currentAccount));
		//System.out.println("IBAN:" + iban);
		
		BankAccount bankAccount = new BankAccount();
		bankAccount.setIBAN("PayMyBuddy");
		
		//bankAccount.setAmount(accountService.getAmount(IBAN));
		Transaction transaction = new Transaction();
		transaction.setAmount(money);
		transaction.setDescription("Transaction done on " + new Date());
		transaction.setBankAccount(bankAccount);
		transaction.setRelationship(relationship);
		transactionService.addTransaction(transaction);
		List<Account> relationships = relationshipService.getConnections(currentAccount);
	    model.addAttribute("relationships", relationships);
		List<Transaction> transactions = transactionService.getTransactions(currentAccount);
	    model.addAttribute("transactions", transactions);
		//transaction.se
		//accountService.addTransaction(transaction, null, null)
		return "transfer";
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
