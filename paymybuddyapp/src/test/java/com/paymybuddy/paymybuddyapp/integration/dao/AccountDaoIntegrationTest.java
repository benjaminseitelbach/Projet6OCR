package com.paymybuddy.paymybuddyapp.integration.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.paymybuddy.paymybuddyapp.dao.ICustomerRepository;
import com.paymybuddy.paymybuddyapp.integration.service.DBPrepareService;
import com.paymybuddy.paymybuddyapp.model.Customer;

@SpringBootTest
public class AccountDaoIntegrationTest {

	@Autowired
	private ICustomerRepository accountDao;
	
	private static DBPrepareService dbPrepareService;
	
	private static String emailTest;
	private static String passwordTest;
	
	@BeforeAll
	private static void setUp() throws Exception {
		System.out.println("Set up");
		/*dataBasePrepareService = new DataBasePrepareService();
		dataBasePrepareService.clearDataBaseEntries();
		
		*/
		dbPrepareService = new DBPrepareService();
		emailTest = "EmailTest";
		passwordTest = "PasswordTest";
	}
	
	@Test
	public void saveAccountIT() {
		Customer account = new Customer();
		account.setEmail(emailTest);
		account.setPassword(passwordTest);
		account.setFirstName("FirstNameTest");
		account.setLastName("LastNameTest");
		
		accountDao.addAccount(account);
		
		Customer accountFound = dbPrepareService.findAccount(emailTest, passwordTest);
		
		assertEquals(account.getEmail(), accountFound.getEmail());
		assertEquals(account.getPassword(), accountFound.getPassword());
		assertEquals(account.getFirstName(), accountFound.getFirstName());
		assertEquals(account.getLastName(), accountFound.getLastName());

	}
	
}
