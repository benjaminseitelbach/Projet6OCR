package com.paymybuddy.paymybuddyapp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
	public List<Transaction> findBySender(Customer sender);
	
	public List<Transaction> findAll();
}
