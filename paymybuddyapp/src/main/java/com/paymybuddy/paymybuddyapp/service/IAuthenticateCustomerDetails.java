package com.paymybuddy.paymybuddyapp.service;

import com.paymybuddy.paymybuddyapp.model.Customer;

public interface IAuthenticateCustomerDetails {

	public Customer getCustomerDetails(String username);
}
