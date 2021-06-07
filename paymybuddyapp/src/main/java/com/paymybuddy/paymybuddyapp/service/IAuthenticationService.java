package com.paymybuddy.paymybuddyapp.service;

import java.util.List;
import java.util.Optional;

import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Transaction;

public interface IAuthenticationService {

	public Customer authenticate(String email, String password);
	
	public List<Customer> getAllCustomers();
}
