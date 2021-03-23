package com.paymybuddy.paymybuddyapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.paymybuddy.paymybuddyapp.config.DBConfig;
import com.paymybuddy.paymybuddyapp.model.Account;

@Repository
public class AccountDaoImpl implements AccountDao {

	private static final Logger logger = LogManager.getLogger("AccountDaoImpl");
	
	public DBConfig dbConfig = new DBConfig();
	
	public Account addAccount(Account account) {

		Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("insert into account(EMAIL, PASSWORD, FIRSTNAME, LASTNAME) values(?,?,?,?)");
            //EMAIL, PASSWORD, FIRSTNAME, LASTNAME
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getFirstName());
            ps.setString(4, account.getLastName());
            ps.execute();
            return account;
        }catch (Exception ex){
            logger.error("Error saving new account",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return null;
	}
	
	public int getId(Account account) {
		Connection con = null;
    	PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	con = dbConfig.getConnection();
        	
        	ps = con.prepareStatement("SELECT ID FROM ACCOUNT WHERE EMAIL=? AND PASSWORD=?");
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getPassword());
        	rs = ps.executeQuery();
        	if(rs.next()) {
        		return rs.getInt(1);
        	}

        }catch (Exception ex){
            logger.error("Error getting account ID", ex);
        }finally {
        	dbConfig.closeConnection(con);
            dbConfig.closeResultSet(rs);
            dbConfig.closePreparedStatement(ps);
        }

		return 0;
				
	}
	
	public List<Account> getAccounts(List<Integer> accountsIds) {
		Connection con = null;
    	PreparedStatement ps = null;
        ResultSet rs = null;
        List<Account> accounts = new ArrayList<>();
        try {
        	con = dbConfig.getConnection();
        	for(Integer accountId : accountsIds) {
        		ps = con.prepareStatement("SELECT EMAIL, FIRSTNAME, LASTNAME FROM ACCOUNT WHERE ID=?");
                ps.setInt(1, accountId);
            	rs = ps.executeQuery();
            	if(rs.next()) {
            		Account account = new Account();
            		account.setEmail(rs.getString(1));
            		account.setFirstName(rs.getString(2));
            		account.setLastName(rs.getString(3));
            		accounts.add(account);
            	}
        	}
        	return accounts;

        }catch (Exception ex){
            logger.error("Error getting account ID", ex);
        }finally {
        	dbConfig.closeConnection(con);
            dbConfig.closeResultSet(rs);
            dbConfig.closePreparedStatement(ps);
        }

		return null;
				
	}
}
