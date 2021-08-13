package com.paymybuddy.paymybuddyapp.service;

import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddyapp.dao.CustomerRepository;
import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Customer;

@Service
public class CustomerService implements ICustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	/**
     * Add Customer: check if email doesn't already exists and add a new Customer to database 
     *
     * @param customer
     *
     * @return corresponding customer or null
     */	
	public Customer addCustomer(Customer customer) {
		
		if(customerRepository.existsByEmail(customer.getEmail())) {
			return null;
		} else {
			String hashedPassword = BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt());
			customer.setPassword(hashedPassword);
			return customerRepository.save(customer);
		}
		
		
	}
	
	/**
     * Add Connection: add a new connection to the corresponding customer if entered email exists in data base 
     *
     * @param corresponding customer
     * @param connection email
     *
     * @return boolean if connection has been added or not
     */	
	public boolean addConnection(Customer customer, String connectionEmail) {
		Set<Customer> connections = customer.getConnections();
		Optional<Customer> optConnection = customerRepository.findByEmail(connectionEmail);
		if(optConnection.isPresent()) {
			Customer connection = optConnection.get();
			connections.add(connection);
			customerRepository.save(customer);
			return true;
		} else {
			return false;
		}
				
	}
	
	/**
     * Authenticate: check if email and password corresponds 
     *
     * @param email
     * @param password
     *
     * @return corresponding customer or null
     */	
	public Customer authenticate(String email, String password) {
		
		if(customerRepository.existsByEmail(email)) {
			Customer customer = customerRepository.findByEmail(email).get();
			
			if(BCrypt.checkpw(password, customer.getPassword())) {
				return customer;
			} 
			
		}
		return null;
	}
	
	/**
     * Add Bank Account: add a bank account (iban and amount) for the corresponding customer 
     *
     * @param corresponding customer
     * @param iban
     * @param amount
     *
     * @return corresponding customer with bank account
     */	
	public Customer addBankAccount(Customer customer, String iban, double amount) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setIban(iban);
		bankAccount.setAmount(amount);
		bankAccount.setCustomer(customer);
		
		customer.setBankAccount(bankAccount);
		
		return customerRepository.save(customer);
		
	}
	
	/**
     * Send to PayMyBuddy: send amount from bank account amount to PayMyBuddy account
     * and check if there is enough money on bank account
     *
     * @param corresponding customer
     * @param amount
     *
     * @return boolean if transaction has been done or not
     */	
	@Transactional
	public boolean sendToPayMyBuddy(Customer customer, double amount) {
		BankAccount bankAccount = customer.getBankAccount();
		double bankAccountAmount = bankAccount.getAmount();

		if(bankAccountAmount - amount >= 0) {
			double bankAccountNewAmount = bankAccountAmount - amount;
			bankAccount.setAmount(bankAccountNewAmount);
			
			double payMyBuddyNewAmount = customer.getAmount() + amount;
			customer.setAmount(payMyBuddyNewAmount);
			customerRepository.save(customer);
			return true;
		} else {
			return false;
		}
	}
	
	/**
     * Recover to Bank Account: send amount from PayMyBuddy account to Bank Account
     * and check if there is enough money on bank account
     *
     * @param corresponding customer
     * @param amount
     *
     * @return boolean if transaction has been done or not
     */	
	@Transactional
	public boolean recoverToBankAccount(Customer customer, double amount) {
		double payMyBuddyAmount = customer.getAmount();

		if(payMyBuddyAmount - amount >= 0) {
			double payMyBuddyNewAmount = payMyBuddyAmount - amount;
			customer.setAmount(payMyBuddyNewAmount);
			
			BankAccount bankAccount = customer.getBankAccount();
			double bankAccountNewAmount = bankAccount.getAmount() + amount;
			bankAccount.setAmount(bankAccountNewAmount);
			
			customerRepository.save(customer);
			return true;
		} else {
			return false;
		}
	}
	
}
