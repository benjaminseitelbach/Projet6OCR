package com.paymybuddy.paymybuddyapp.service;

import com.paymybuddy.paymybuddyapp.model.Customer;

public interface IAuthenticationService {

	public Customer authenticate(String email, String password);
	
	public Customer addCustomer(Customer customer);
	
	public Customer addConnection(Customer customer, String connectionEmail);
	
	public Customer sendToPayMyBuddy(Customer customer, double amount);
	
	public Customer recoverToBankAccount(Customer customer, double amount);
	
}
