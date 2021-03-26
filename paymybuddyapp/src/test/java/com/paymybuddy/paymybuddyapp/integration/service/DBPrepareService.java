package com.paymybuddy.paymybuddyapp.integration.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.paymybuddy.paymybuddyapp.config.DBConfig;
import com.paymybuddy.paymybuddyapp.integration.config.DBTestConfig;
import com.paymybuddy.paymybuddyapp.model.Account;

public class DBPrepareService {

    DBTestConfig dataBaseTestConfig = new DBTestConfig();

    public void clearDataBaseEntries(){
    	System.out.println("clear data base entries");
        Connection connection = null;
        try{
            connection = dataBaseTestConfig.getConnection();

            //clear medications
            connection.prepareStatement("truncate table medication").execute();

            //clear allergies
            connection.prepareStatement("truncate table allergie").execute();         
            
            //clear persons
            connection.prepareStatement("truncate table person").execute();
            
            //clear firestations
            connection.prepareStatement("truncate table firestation").execute();
            
            //clear medical records
            connection.prepareStatement("set FOREIGN_KEY_CHECKS = 0").execute();
            connection.prepareStatement("truncate table medicalRecord").execute();
            connection.prepareStatement("set FOREIGN_KEY_CHECKS = 1").execute();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }
    
    public Account findAccount(String email, String password) {
    	Connection connection = null;

    	Account account = new Account();
    	
        try {
        	connection = dataBaseTestConfig.getConnection();
        	
        	PreparedStatement ps = connection.prepareStatement("SELECT EMAIL, PASSWORD, FIRSTNAME, LASTNAME FROM ACCOUNT"
        			+ " WHERE EMAIL = ? AND PASSWORD = ?");
            ps.setString(1, email);
            ps.setString(2, password);
        	ResultSet rs = ps.executeQuery();
        	rs.next();

        	account.setEmail(rs.getString(1));
        	account.setPassword(rs.getString(2));
        	account.setFirstName(rs.getString(3));
        	account.setLastName(rs.getString(4));
            
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);

        }

		return account;
    }

}
