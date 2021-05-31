package com.paymybuddy.paymybuddyapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.paymybuddyapp.dao.ICustomerRepository;
import com.paymybuddy.paymybuddyapp.dao.IRelationshipRepository;
import com.paymybuddy.paymybuddyapp.dao.ITransactionRepository;
import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Transaction;

@Service
public class AuthenticateCustomerDetails implements IAuthenticateCustomerDetails {

	@Autowired
	private ICustomerRepository customerRepository;
	
	public Customer getCustomerDetails(String email) {
		Customer customer = customerRepository.getCustomerInfos(email);
		List<Customer> connections = customerRepository.getConnections(customer.getId());
		List<Transaction> transactions = customerRepository.getTransactions(customer.getId());
		customer.setConnections(connections);
		customer.setTransactions(transactions);
		
		return customer;
		
	}
}
