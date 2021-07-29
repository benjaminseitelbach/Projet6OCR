package com.paymybuddy.paymybuddyapp;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymybuddyappApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyappApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		//String pw = BCrypt.hashpw("admin123", BCrypt.gensalt());
		//System.out.println("password:" + pw);
	}

}
