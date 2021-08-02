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
	
	public Customer addCustomer(Customer customer) {
		String hashedPassword = BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt());
		customer.setPassword(hashedPassword);
		return customerRepository.save(customer);
	}
	
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
	
	@Transactional
	public Customer sendToPayMyBuddy(Customer customer, double amount) {
		BankAccount bankAccount = customer.getBankAccount();
		double bankAccountAmount = bankAccount.getAmount();
		//TODO CAN BANK ACCOUNT AMOUNT BE < 0 ?
		//if(bankAccountAmount - amount >= 0) {
			double bankAccountNewAmount = bankAccountAmount - amount;
			bankAccount.setAmount(bankAccountNewAmount);
			
			double payMyBuddyNewAmount = customer.getAmount() + amount;
			customer.setAmount(payMyBuddyNewAmount);
			customerRepository.save(customer);
		//}
		return customer;
	}
	
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
			//TODO
			return false;
		}
		//return customer;
	}
	
}
