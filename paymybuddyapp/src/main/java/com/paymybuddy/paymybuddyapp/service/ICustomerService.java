package com.paymybuddy.paymybuddyapp.service;

import com.paymybuddy.paymybuddyapp.model.Customer;

public interface ICustomerService {
	
	public Customer getCustomer(String email, String password);
	
	public int getId(Customer account);
	
	public int getId(String email, String password);
}
