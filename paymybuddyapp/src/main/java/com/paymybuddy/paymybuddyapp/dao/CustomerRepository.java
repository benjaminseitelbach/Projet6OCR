package com.paymybuddy.paymybuddyapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddyapp.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	public boolean existsByEmail(String email);
	
	public boolean existsByEmailAndPassword(String email, String password);
	
	public Optional<Customer> findByEmail(String email);

	public List<Customer> findAll();
}
	