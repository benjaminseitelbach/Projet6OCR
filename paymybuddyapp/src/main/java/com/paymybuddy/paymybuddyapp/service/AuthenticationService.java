package com.paymybuddy.paymybuddyapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
			
			if(customerRepository.existsByEmailAndPassword(email, password)) {
				return customerRepository.findByEmail(email).get();
			}
		}
		return null;
	}
	
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
}
