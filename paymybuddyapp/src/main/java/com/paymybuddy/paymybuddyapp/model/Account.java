package com.paymybuddy.paymybuddyapp.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
	int id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private int amount;
	private List<BankAccount> bankAccounts;
	private List<Relationship> relationships;
	
	public Account() {
		bankAccounts = new ArrayList<>();
		relationships = new ArrayList<>();
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public List<BankAccount> getbankAccounts() {
		return bankAccounts;
	}
	public void setBankAccounts(List<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}
	public List<Relationship> getRelationships() {
		return relationships;
	}
	public void setRelationships(List<Relationship> relationships) {
		this.relationships = relationships;
	}
	
	public void addBankAccount(BankAccount bankAccount) {
		bankAccounts.add(bankAccount);
	}
	public void addRelationship(Relationship relationship) {
		relationships.add(relationship);
	}
	
	public BankAccount getBankAccount(String IBAN) {
		for(BankAccount bankAccount : bankAccounts) {
			if(bankAccount.getIBAN().equals(IBAN)) {
				return bankAccount;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "Email: " + email + "\nPassword: " + password + "\nFirst name: " + firstName + "\nLast name: " + lastName;
	}
	
}
