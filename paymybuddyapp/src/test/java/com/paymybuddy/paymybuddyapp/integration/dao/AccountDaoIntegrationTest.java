package com.paymybuddy.paymybuddyapp.integration.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.paymybuddy.paymybuddyapp.dao.IAccountRepository;
import com.paymybuddy.paymybuddyapp.integration.service.DBPrepareService;
import com.paymybuddy.paymybuddyapp.model.Account;

@SpringBootTest
public class AccountDaoIntegrationTest {

	@Autowired
	private IAccountRepository accountDao;
	
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
		Account account = new Account();
		account.setEmail(emailTest);
		account.setPassword(passwordTest);
		account.setFirstName("FirstNameTest");
		account.setLastName("LastNameTest");
		
		accountDao.addAccount(account);
		
		Account accountFound = dbPrepareService.findAccount(emailTest, passwordTest);
		
		assertEquals(account.getEmail(), accountFound.getEmail());
		assertEquals(account.getPassword(), accountFound.getPassword());
		assertEquals(account.getFirstName(), accountFound.getFirstName());
		assertEquals(account.getLastName(), accountFound.getLastName());

	}
	
}
