package com.paymybuddy.paymybuddyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddyapp.dao.ICustomerRepository;
import com.paymybuddy.paymybuddyapp.model.Customer;

@Service
public class AuthenticationService implements IAuthenticationService {

	@Autowired
	private ICustomerRepository customerRepository;
	
	@Autowired
	private IAuthenticateCustomerDetails authenticateUserDetails;
	
	public Customer authenticate(String email, String password) {
		Customer result = new Customer();
		if(customerRepository.emailExists(email)) {
			if(customerRepository.passwordCorresponds(email, password)) {
				result = authenticateUserDetails.getCustomerDetails(email);
			}
		}
		return result;
	}
	
}
