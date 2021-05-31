package com.paymybuddy.paymybuddyapp.dao;

import java.math.BigDecimal;
import java.util.List;

import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Transaction;

public interface ICustomerRepository {

	public boolean emailExists(String email);
	
	public boolean passwordCorresponds(String email, String password);
	
	public Customer getCustomerInfos(String username);
	
	public Customer getCustomerInfos(int id);
	
	public List<Customer> getConnections(int customerId);
	
	public List<Transaction> getTransactions(int customerId);
	
	public int getId(Customer account);
	
	public int getId(String firstName, String lastName);
	
	public Customer getCustomer(int id);
	
	public int getId(String email);
	
	public boolean updateAmount(int id, BigDecimal newAmount);
	
}
