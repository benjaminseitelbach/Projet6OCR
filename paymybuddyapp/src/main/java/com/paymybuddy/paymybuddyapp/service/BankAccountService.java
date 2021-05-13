package com.paymybuddy.paymybuddyapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddyapp.dao.IAccountRepository;
import com.paymybuddy.paymybuddyapp.dao.IBankAccountRepository;
import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.BankAccount;

@Service
public class BankAccountService implements IBankAccountService {
	
	@Autowired
	private IBankAccountRepository bankAccountRepository;
	
	
	public List<BankAccount> getBankAccounts(Account account) {
		List<BankAccount> bankAccounts = bankAccountRepository.getBankAccounts(account);
		return bankAccounts;
	}
}
