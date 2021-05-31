package com.paymybuddy.paymybuddyapp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Customer {
	int id;
	private String username;
	private String password;
	private String email;	
	private String firstName;
	private String lastName;
	private BigDecimal amount;
	private List<Customer> connections;
	private List<BankAccount> bankAccounts;
	private List<Transaction> transactions;
	
	public Customer() {
		connections = new ArrayList<>();
		transactions = new ArrayList<>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public List<Customer> getConnections() {
		return connections;
	}
	public void setConnections(List<Customer> connections) {
		this.connections = connections;
	}
	public List<BankAccount> getBankAccounts() {
		return bankAccounts;
	}
	public void setBankAccounts(List<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}
	public void addConnection(Customer connection) {
		this.connections.add(connection);
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

	@Override
	public String toString() {
		return "Email: " + email + "\nPassword: " + password + "\nFirst name: " + firstName + "\nLast name: " + lastName;
	}
	
}
