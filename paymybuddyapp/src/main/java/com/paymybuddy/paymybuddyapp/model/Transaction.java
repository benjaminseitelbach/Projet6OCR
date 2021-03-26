package com.paymybuddy.paymybuddyapp.model;

public class Transaction {
	private int id;	
	private float amount;
	private String description;
	private Relationship relationship;
	private int bankAccountId;
	private BankAccount bankAccount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
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
	
	@Override
	public String toString() {
		return "Amount: " + amount + ", description: " + description + ", Relationship id: " + relationship.getRelationshipId()
			+ ", Bank Account: " + bankAccount.toString();
	}
	
	
}
