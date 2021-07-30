package com.paymybuddy.paymybuddyapp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddyapp.model.BankAccount;
import com.paymybuddy.paymybuddyapp.model.Customer;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Integer> {

}
