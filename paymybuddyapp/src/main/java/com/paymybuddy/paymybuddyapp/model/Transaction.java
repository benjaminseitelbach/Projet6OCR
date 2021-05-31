package com.paymybuddy.paymybuddyapp.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Transaction {
	private int id;	
	private BigDecimal amount;
	private String description;
	private Date date;
	private Customer sender;
	private Customer receiver;
	private int relationshipId;	
		
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Customer getSender() {
		return sender;
	}
	public void setSender(Customer sender) {
		this.sender = sender;
	}
	public Customer getReceiver() {
		return receiver;
	}
	public void setReceiver(Customer receiver) {
		this.receiver = receiver;
	}
	public int getRelationshipId() {
		return relationshipId;
	}
	public void setRelationshipId(int relationshipId) {
		this.relationshipId = relationshipId;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", amount=" + amount + ", description=" + description + ", date=" + date
				+ ", sender=" + sender + ", receiver=" + receiver + ", relationshipId=" + relationshipId + "]";
	}
	

	
	
}
