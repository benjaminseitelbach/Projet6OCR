package com.paymybuddy.paymybuddyapp.model;

import java.math.BigDecimal;

public class Transaction {
	private int id;	
	private BigDecimal amount;
	private String description;
	private Relationship relationship;
	private int bankAccountId;
	private BankAccount bankAccount;
	private Account connection;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Relationship getRelationship() {
		return relationship;
	}
	public void setRelationship(Relationship relationship) {
		this.relationship = relationship;
	}
	public int getBankAccountId() {
		return bankAccountId;
	}
	public void setBankAccountId(int bankAccountId) {
		this.bankAccountId = bankAccountId;
	}
	public BankAccount getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	public Account getConnection() {
		return connection;
	}
	public void setConnection(Account connection) {
		this.connection = connection;
	}
	
	@Override
	public String toString() {
		return "Amount: " + amount + ", description: " + description + ", Relationship id: " + relationship.getRelationshipId()
			+ ", Bank Account: " + bankAccount.toString();
	}
	
	
}
