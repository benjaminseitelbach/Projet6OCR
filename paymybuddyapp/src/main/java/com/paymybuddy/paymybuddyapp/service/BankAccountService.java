package com.paymybuddy.paymybuddyapp.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddyapp.dao.BankAccountRepository;
import com.paymybuddy.paymybuddyapp.model.BankAccount;

@Service
public class BankAccountService implements IBankAccountService {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	public BankAccount getBankAccountById(int id) {
		return bankAccountRepository.findById(id).get();
	}
	
	
}
