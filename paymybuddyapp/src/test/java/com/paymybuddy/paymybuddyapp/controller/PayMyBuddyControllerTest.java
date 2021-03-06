package com.paymybuddy.paymybuddyapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.service.ICustomerService;
import com.paymybuddy.paymybuddyapp.service.ITransactionService;

@SpringBootTest
@AutoConfigureMockMvc
public class PayMyBuddyControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ICustomerService customerService;
	
	@MockBean
	private ITransactionService transactionService;
	
	private String emailTest = "test@email.com";
	private String passwordTest = "passwordTest";
	
	@Test
    public void signInPageTest() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("signIn"));
    }
		
	@Test
	public void signInTest() throws Exception {
		
		Customer customer = new Customer();
		customer.setId(1);
		customer.setEmail(emailTest);
		customer.setPassword(passwordTest);
	
		Mockito.when(customerService.authenticate(emailTest, passwordTest)).thenReturn(customer);
		
		mockMvc.perform(post("/signIn") 
	           .param("email", emailTest) 
	           .param("password", passwordTest)                 
	           .contentType(MediaType.APPLICATION_FORM_URLENCODED) 
	           .accept(MediaType.APPLICATION_JSON)) //
			   .andExpect(status().isOk())
	           .andExpect(view().name("transfer"))// 
	           .andReturn();
		
	}
	
	@Test
	public void signUpPageTest() throws Exception {
		mockMvc.perform(get("/signUp"))
        .andExpect(status().isOk())
        .andExpect(view().name("signUp"));
	}
	
	@Test
	public void signUpTest() throws Exception {

		String firstName = "FirstNameTest";
		String lastName = "LastNameTest";
		
		Customer customer = new Customer();
		customer.setEmail(emailTest);
		customer.setPassword(passwordTest);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		
		Mockito.when(customerService.addCustomer(Mockito.any(Customer.class))).thenReturn(customer);
		
		mockMvc.perform(post("/signUp") 
               .param("email", emailTest) 
               .param("password", passwordTest) 
               .param("firstName", firstName)
               .param("lastName", lastName)
               .contentType(MediaType.APPLICATION_FORM_URLENCODED) 
               .accept(MediaType.APPLICATION_JSON)) 
		       .andExpect(status().isOk())
               .andExpect(view().name("transfer")) 
               .andReturn();
	}
	
	@Test
	public void addConnectionTest() throws Exception {
		
		Customer customer = new Customer();
		customer.setEmail(emailTest);
		customer.setPassword(passwordTest);
		
		String connectionEmail = "connectionTest@email.com";
		
		Mockito.when(customerService.addConnection(customer, connectionEmail)).thenReturn(true);
		
		mockMvc.perform(post("/addConnection")
			   .param("connectionEmail", connectionEmail)
			   .contentType(MediaType.APPLICATION_FORM_URLENCODED) 
               .accept(MediaType.APPLICATION_JSON)) 
			   .andExpect(status().isOk())
			   .andExpect(view().name("transfer")) 
			   .andReturn();
		
	}
	
	@Test
	public void sendMoneyTest() throws Exception {
		
		Customer customer = new Customer();
		customer.setId(1);
		customer.setEmail(emailTest);
		customer.setPassword(passwordTest);
		customer.setFirstName("John");
		customer.setLastName("Boyd");
		customer.setAmount(100);
		
		int connectionId = 2;
		String description = "Description Test";
		double amount = 10;
		
		Mockito.when(transactionService.sendMoney(customer, connectionId, amount, description)).thenReturn(true);
		
		mockMvc.perform(post("/sendMoney")  
	           .param("connection", String.valueOf(connectionId)) 
	           .param("amount", String.valueOf(amount))
	           .param("description", description)
	           .contentType(MediaType.APPLICATION_FORM_URLENCODED) 
	           .accept(MediaType.APPLICATION_JSON)) 
			   .andExpect(status().isOk())
	           .andExpect(view().name("transfer")) 
	           .andReturn();
		
	}
	
	
	@Test
	public void addToPayMyBuddyTest() throws Exception {
		double amount = 20;
		
		Customer customer = new Customer();
		customer.setEmail(emailTest);
		customer.setAmount(0);
		
		Mockito.when(customerService.sendToPayMyBuddy(Mockito.any(Customer.class), Mockito.anyDouble())).thenReturn(true);
		
		mockMvc.perform(post("/addToPayMyBuddy")
			   .param("amountAddedToPayMyBuddy", String.valueOf(amount))
			   .contentType(MediaType.APPLICATION_FORM_URLENCODED) 
	           .accept(MediaType.APPLICATION_JSON)) 
			   .andExpect(status().isOk())
	           .andExpect(view().name("profile")) 
	           .andReturn();
		
	}
	
	@Test
	public void recoverToBankAccountTest() throws Exception {
		double amount = 20;
		
		Customer customer = new Customer();
		customer.setEmail(emailTest);
		customer.setAmount(0);
		
		Mockito.when(customerService.recoverToBankAccount(Mockito.any(Customer.class), Mockito.anyDouble()))
			   .thenReturn(true);
		
		mockMvc.perform(post("/recoverToBankAccount")
			   .param("amountRecoveredToBankAccount", String.valueOf(amount))
			   .contentType(MediaType.APPLICATION_FORM_URLENCODED) 
	           .accept(MediaType.APPLICATION_JSON)) 
			   .andExpect(status().isOk())
	           .andExpect(view().name("profile")) 
	           .andReturn();
		
	}
	
	
	
}
