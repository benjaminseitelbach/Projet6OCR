package com.paymybuddy.paymybuddyapp.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private int id;	
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "date")
	private Date date;
	
	@ManyToOne(
			cascade = CascadeType.MERGE
			)
	@JoinColumn(name = "sender_id")
	private Customer sender;
	
	@ManyToOne(
			cascade = CascadeType.MERGE
			)
	@JoinColumn(name = "receiver_id")
	private Customer receiver;
			
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
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
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", amount=" + amount + ", description=" + description + ", date=" + date
				+ ", sender=" + sender + ", receiver=" + receiver + "]";
	}
	
	
	

	
	
}
