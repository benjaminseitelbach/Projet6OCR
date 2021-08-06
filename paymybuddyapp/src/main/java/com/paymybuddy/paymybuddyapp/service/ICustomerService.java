package com.paymybuddy.paymybuddyapp.service;

import com.paymybuddy.paymybuddyapp.model.Customer;

public interface ICustomerService {

	public Customer authenticate(String email, String password);
	
	public Customer addCustomer(Customer customer);
	
	public boolean addConnection(Customer customer, String connectionEmail);
	
	public Customer addBankAccount(Customer customer, String iban, double amount);
	
	public boolean sendToPayMyBuddy(Customer customer, double amount);
	
	public boolean recoverToBankAccount(Customer customer, double amount);
	
}
