package com.paymybuddy.paymybuddyapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddyapp.dao.CustomerRepository;
import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Transaction;

@Service
public class AuthenticationService implements IAuthenticationService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer authenticate(String email, String password) {

		
		if(customerRepository.existsByEmail(email)) {
			Customer customer = customerRepository.findByEmail(email).get();
			
			if(BCrypt.checkpw(password, customer.getPassword())) {
				System.out.println("Password OK");
				return customer;
			} else {
				System.out.println("Password KO");
			}
			
		}
		return null;
	}
	
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
}
