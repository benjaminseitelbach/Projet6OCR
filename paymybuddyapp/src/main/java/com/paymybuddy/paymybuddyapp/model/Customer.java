package com.paymybuddy.paymybuddyapp.model;

import java.util.HashSet;
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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int id;
	
	@Column(name = "email")
	@NotBlank(message = "Email is mandatory")
	private String email;
	
	@Column(name = "password")
	@NotBlank(message = "Password is mandatory")
	private String password;
	
	@Column(name = "first_name")
	@NotBlank(message = "First name is mandatory")
	private String firstName;
	
	@Column(name = "last_name")
	@NotBlank(message = "Last name is mandatory")
	private String lastName;
	
	@Column(name = "amount")
	private double amount;
	
	@ManyToMany(
			fetch = FetchType.EAGER
			)
	
	@JoinTable(
			name = "relationship",
			joinColumns = @JoinColumn(name = "customer_id"),
			inverseJoinColumns = @JoinColumn(name = "connection_id")
			)
	private Set<Customer> connections = new HashSet<>();
	
	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private BankAccount bankAccount;
	
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Set<Customer> getConnections() {
		return connections;
	}
	public void setConnections(Set<Customer> connections) {
		this.connections = connections;
	}	
	public BankAccount getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", amount=" + amount + ", connections=" + connections + ", bankAccount="
				+ bankAccount + "]";
	}
	

	
	
}
