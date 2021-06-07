package com.paymybuddy.paymybuddyapp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "FIRSTNAME")
	private String firstName;
	
	@Column(name = "LASTNAME")
	private String lastName;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@ManyToMany(
			fetch = FetchType.EAGER
			)
	
	@JoinTable(
			name = "relationship",
			joinColumns = @JoinColumn(name = "customer_ID"),
			inverseJoinColumns = @JoinColumn(name = "connection_ID")
			)
	private Set<Customer> connections = new HashSet<>();
	
	@OneToMany(
			cascade = CascadeType.MERGE,
			orphanRemoval = true,
			fetch = FetchType.EAGER
			)
	@JoinColumn(name = "sender_ID")
	private List<Transaction> transactions = new ArrayList<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Set<Customer> getConnections() {
		return connections;
	}
	public void setConnections(Set<Customer> connections) {
		this.connections = connections;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", amount=" + amount + ", connections=" + connections + "]";
	}
	
}
