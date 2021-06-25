package com.paymybuddy.paymybuddyapp.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddyapp.dao.CustomerRepository;
import com.paymybuddy.paymybuddyapp.model.Customer;

@Service
public class AuthenticationService implements IAuthenticationService {

	@Autowired
	private CustomerRepository customerRepository;
	
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
	
}
