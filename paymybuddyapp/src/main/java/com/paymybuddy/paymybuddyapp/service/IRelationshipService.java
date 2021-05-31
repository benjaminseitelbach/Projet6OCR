package com.paymybuddy.paymybuddyapp.service;

import java.util.List;

import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Relationship;

public interface IRelationshipService {

	public List<Customer> getConnections(Customer account);
	
	public Customer addConnection(Customer account, String email);
	
}
