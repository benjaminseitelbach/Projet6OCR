package com.paymybuddy.paymybuddyapp.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddyapp.config.DBConfig;
import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Transaction;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	public boolean existsByEmail(String email);
	
	public boolean existsByEmailAndPassword(String email, String password);
	
	public Optional<Customer> findByEmail(String email);

	public List<Customer> findAll();
}
	