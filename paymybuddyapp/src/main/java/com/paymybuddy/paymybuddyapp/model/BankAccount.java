package com.paymybuddy.paymybuddyapp.model;

public class BankAccount {
	private String IBAN;
	private float amount;
	
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String IBAN) {
		this.IBAN = IBAN;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "IBAN: " + IBAN + ", Amount: " + amount;
	}
}
