package com.paymybuddy.paymybuddyapp.controller;

import java.math.BigDecimal;
import java.util.Date;
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
public class TransferController {

	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private ITransactionService transactionService;
	
	@Autowired
	private IRelationshipService relationshipService;
	
	private Account currentAccount;
	
	
		

	
	
}
