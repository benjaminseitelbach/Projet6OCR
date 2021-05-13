package com.paymybuddy.paymybuddyapp.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Relationship;
import com.paymybuddy.paymybuddyapp.model.Transaction;
import com.paymybuddy.paymybuddyapp.service.AccountService;
import com.paymybuddy.paymybuddyapp.service.IAccountService;
import com.paymybuddy.paymybuddyapp.service.IRelationshipService;
import com.paymybuddy.paymybuddyapp.service.ITransactionService;

@Controller
public class MainController {

	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private ITransactionService transactionService;
	
	@Autowired
	private IRelationshipService relationshipService;
	
	private Account currentAccount;
	
	
	
	
	@PostMapping("/addConnection")
	public String addConnection(@RequestParam(name="email") String email, Model model) {
		relationshipService.addConnection(currentAccount, email);
		
		//model.addAttribute("account", currentAccount);
	    List<Account> relationships = relationshipService.getConnections(currentAccount);
	    model.addAttribute("relationships", relationships);
	    /*
	    List<BankAccount> bankAccounts = accountService.getBankAccounts(currentAccount);
	    model.addAttribute("bankAccounts", bankAccounts);
	    List<Transaction> transactions = accountService.getTransactions(currentAccount);
	    model.addAttribute("transactions", transactions);
	    */
		//System.out.println(currentAccount.toString());
		return "transfer";
	}
		
	/*
	@PostMapping("/addBankAccount")
	public String addBankAccount(@RequestParam(name="iban") String iban, @RequestParam(name="amount") float amount) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setIBAN(iban);
		bankAccount.setAmount(amount);
		accountService.addBankAccount(currentAccount, bankAccount);
		return "profile";
	}
	*/
	/*
	@PostMapping("/addAccount")
	public ModelAndView addAccount(@ModelAttribute("account") Account account) {
		ModelAndView mv = new ModelAndView();
		
	    accountService.addAccount(account);
	    mv.addObject("Account", account);
	    return mv;
	}
	*/
	
	/*
	@PostMapping("/addAccount")
	public String addAccount(@RequestBody Account account) {
	    accountService.addAccount(account);
	    return account.toString();
	}
	*/
	/*
	@GetMapping("/deleteAccount/{id}")
	public ModelAndView deleteAccount(@PathVariable("id") final int id) {
	    accountService.deleteAccount(id);
	    return new ModelAndView("redirect:/");
	}
	*/
		
	@GetMapping("/transfer")
	public String homeAccount(Model model) {
		model.addAttribute("account", currentAccount);
	    List<Account> relationships = accountService.getRelationships(currentAccount);
	    model.addAttribute("relationships", relationships);
	    List<BankAccount> bankAccounts = accountService.getBankAccounts(currentAccount);
	    model.addAttribute("bankAccounts", bankAccounts);
	    List<Transaction> transactions = accountService.getTransactions(currentAccount);
	    model.addAttribute("transactions", transactions);
	    return "transfer";
	}
	
	
	
	@PostMapping("/sendMoney") 
	public String sendMoney(@RequestParam(name="relationship") String sRelationship,
			@RequestParam(name="money") BigDecimal money, @RequestParam(name="description") String description, 
			@RequestParam(name="bankAccount") String iban) {
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
		iban = getIBANWithoutAmount(iban);
		System.out.println("IBAN:" + iban);
		
		BankAccount bankAccount = new BankAccount();
		bankAccount.setIBAN(iban);
		
		//bankAccount.setAmount(accountService.getAmount(IBAN));
		Transaction transaction = new Transaction();
		transaction.setAmount(money);
		transaction.setDescription(description);
		transaction.setBankAccount(bankAccount);
		transaction.setRelationship(relationship);
		transactionService.addTransaction(transaction);
		
		//transaction.se
		//accountService.addTransaction(transaction, null, null)
		return "transfer";
	}
	/*
	@PostMapping("/signIn")
	public ModelAndView signIn(@ModelAttribute Account account) {
		Account account1 = accountService.getAccount(1);
	    return new ModelAndView("redirect:/");
	}
	*/

	
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
	
	public String getIBANWithoutAmount(String ibanWithAmount) {
		String result = "";
		for(int iCharacter = 0; iCharacter < ibanWithAmount.length(); iCharacter ++) {
			char character = ibanWithAmount.charAt(iCharacter);
			if(character == '(') {
				iCharacter = ibanWithAmount.length();
			} else {
				result += character;
			}
			
		}
		return result;
	}
	
}
