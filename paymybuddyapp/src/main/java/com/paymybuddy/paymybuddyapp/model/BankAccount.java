package com.paymybuddy.paymybuddyapp.model;

import java.math.BigDecimal;

public class BankAccount {
	private String IBAN;
	private BigDecimal amount;
	
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String IBAN) {
		this.IBAN = IBAN;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "IBAN: " + IBAN + ", Amount: " + amount;
	}
}
