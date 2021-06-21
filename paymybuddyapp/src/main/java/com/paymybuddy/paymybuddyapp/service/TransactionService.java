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
	
	
	@Transactional
	public boolean sendMoney(Customer sender, int receiverId, double amount) {
		//int customerId = customer.getId();
		
		double amountWithTax = amount + 0.005 * amount;
		double senderAmount = sender.getAmount();
		
		if(senderAmount >= amountWithTax) {
			
			double senderNewAmount = senderAmount - amountWithTax;
			//System.out.println("customer new amount: " + customerNewAmount.floatValue());
			sender.setAmount(senderNewAmount);
			customerRepository.save(sender);
			
			Customer receiver = customerRepository.findById(receiverId).get();
			double receiverNewAmount = receiver.getAmount() + amount;
			receiver.setAmount(receiverNewAmount);
			customerRepository.save(receiver);
			
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			Date date = new Date();
			//Date date = new Date(Calendar.getInstance().getTime().getTime());
			transaction.setDescription("Transaction done on " + date);
			transaction.setDate(date);
			transaction.setSender(sender);
			transaction.setReceiver(receiver);
			//transaction.setSender(customer);
			//transaction.setReceiver(connection);
			transactionRepository.save(transaction);
			//customer.addTransaction(transaction);
			return true;
		}
		return false;
				
	}
		
	public List<Transaction> getTransactions(Customer sender) {
		return transactionRepository.findBySender(sender);
	}
	

}
