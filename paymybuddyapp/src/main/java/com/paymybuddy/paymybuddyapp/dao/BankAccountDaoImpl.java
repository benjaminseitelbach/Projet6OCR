package com.paymybuddy.paymybuddyapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.paymybuddy.paymybuddyapp.config.DBConfig;
import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.BankAccount;

public class BankAccountDaoImpl implements BankAccountDao {
	
	private static final Logger logger = LogManager.getLogger("BankAccountDaoImpl");
	
	public DBConfig dbConfig = new DBConfig();
	
	public BankAccount addBankAccount(int accountId, BankAccount bankAccount) {

		Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("INSERT INTO bankaccount(IBAN, AMOUNT, account_ID) values(?,?,?)");
            //IBAN, AMOUNT, account_ID
            ps.setString(1, bankAccount.getIBAN());
            ps.setFloat(2, bankAccount.getAmount());
            ps.setInt(3, accountId);
            ps.execute();
            return bankAccount;
        }catch (Exception ex){
            logger.error("Error saving new Bank Account",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return null;
	}
	
	public int getId(BankAccount bankAccount, int accountId) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT ID FROM BANKACCOUNT WHERE IBAN=? AND AMOUNT=? AND account_ID=?");
            //IBAN, AMOUNT, account_ID
            ps.setString(1, bankAccount.getIBAN());
            ps.setFloat(2, bankAccount.getAmount());
            ps.setInt(3, accountId);

            rs = ps.executeQuery();
            
            if(rs.next()) {
            	return rs.getInt(1);
            }

        }catch (Exception ex){
            logger.error("Error getting Bank Account ID",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return 0;
		
	}
	
	public void updateAmount(int bankAccountId, float newAmount) {
		Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("UPDATE bankaccount SET AMOUNT=? WHERE ID=?");
            //AMOUNT, ID
            ps.setFloat(1, newAmount);
            ps.setInt(2, bankAccountId);

            ps.execute();

        }catch (Exception ex){
            logger.error("Error saving new Bank Account",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }

	}
	
	public BankAccount getBankAccount(String IBAN, int accountId) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BankAccount bankAccount = new BankAccount();
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT IBAN, AMOUNT FROM BANKACCOUNT WHERE IBAN=? AND account_ID=?");
            //IBAN, account_ID
            ps.setString(1, IBAN);
            ps.setFloat(2, accountId);

            rs = ps.executeQuery();
            
            if(rs.next()) {
            	bankAccount.setIBAN(rs.getString(1));
            	bankAccount.setAmount(rs.getFloat(2));

            }

        }catch (Exception ex){
            logger.error("Error getting Bank Account ID",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return bankAccount;
	}
	
	public BankAccount getBankAccount(int id) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BankAccount bankAccount = new BankAccount();
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT IBAN, AMOUNT FROM BANKACCOUNT WHERE ID=?");
            //IBAN, account_ID
            ps.setInt(1, id);

            rs = ps.executeQuery();
            
            if(rs.next()) {
            	bankAccount.setIBAN(rs.getString(1));
            	bankAccount.setAmount(rs.getFloat(2));

            }

        }catch (Exception ex){
            logger.error("Error getting Bank Account",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return bankAccount;
	}
	
}
