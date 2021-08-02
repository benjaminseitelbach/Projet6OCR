package com.paymybuddy.paymybuddyapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.paymybuddyapp.dao.CustomerRepository;
import com.paymybuddy.paymybuddyapp.dao.TransactionRepository;
import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Transaction;

@Service
public class TransactionService implements ITransactionService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	/**
     * Send money: take amount plus tax from sender and give amount to receiver
     *
     * @param sender
     * @param receiverId
     * @param amount
     *
     * @return true if transaction OK and false if not
     */	
	@Transactional
	public boolean sendMoney(Customer sender, int receiverId, double amount) {
		
		double amountWithTax = amount + 0.005 * amount;
		double senderAmount = sender.getAmount();
		
		if(senderAmount >= amountWithTax) {
			
			double senderNewAmount = senderAmount - amountWithTax;
			sender.setAmount(senderNewAmount);
			customerRepository.save(sender);
			
			Customer receiver = customerRepository.findById(receiverId).get();
			double receiverNewAmount = receiver.getAmount() + amount;
			receiver.setAmount(receiverNewAmount);
			customerRepository.save(receiver);
			
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			Date date = new Date();
			transaction.setDescription("Transaction done on " + date);
			transaction.setDate(date);
			transaction.setSender(sender);
			transaction.setReceiver(receiver);
			transactionRepository.save(transaction);
			return true;
		}
		return false;
				
	}
	
	/**
     * Get all transactions from a customer
     *
     * @param customer
     *
     * @return the list of transactions
     */	
	public List<Transaction> getTransactions(Customer customer) {
		return transactionRepository.findBySender(customer);
	}
	
}
