package com.paymybuddy.paymybuddyapp.service;

import com.paymybuddy.paymybuddyapp.model.Customer;

public interface IAuthenticationService {

	public Customer authenticate(String email, String password);
	
}
