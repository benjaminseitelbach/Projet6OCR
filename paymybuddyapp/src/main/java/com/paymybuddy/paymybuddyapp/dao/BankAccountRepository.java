package com.paymybuddy.paymybuddyapp.dao;

import java.math.BigDecimal;
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
import com.paymybuddy.paymybuddyapp.model.BankAccount;

@Repository
public class BankAccountRepository implements IBankAccountRepository {
	
	private static final Logger logger = LogManager.getLogger("BankAccountDaoImpl");
	
	public DBConfig dbConfig = new DBConfig();
	
	public List<BankAccount> getBankAccounts(Account account) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<BankAccount> result = new ArrayList<>();
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT IBAN, AMOUNT FROM BANKACCOUNT INNER JOIN ACCOUNT ON"
            		+ " ACCOUNT.ID=account_ID WHERE EMAIL=? AND PASSWORD=?");
            //EMAIL, PASSWORD
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getPassword());

            rs = ps.executeQuery();
            
            while(rs.next()) {
            	BankAccount bankAccount = new BankAccount();
            	bankAccount.setIBAN(rs.getString(1));
            	bankAccount.setAmount(rs.getBigDecimal(2));
            	result.add(bankAccount);
            }

        }catch (Exception ex){
            logger.error("Error getting Bank Accounts from Account",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return result;
		
	}
	
	public int getId(BankAccount bankAccount, int accountId) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT ID FROM BANKACCOUNT WHERE IBAN=? AND account_ID=?");
            //IBAN, account_ID
            ps.setString(1, bankAccount.getIBAN());
            ps.setInt(2, accountId);

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
	
	public BigDecimal getAmount(int bankAccountId) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BigDecimal result = new BigDecimal(0);
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT AMOUNT FROM BANKACCOUNT WHERE ID=?");
            //ID
            ps.setInt(1, bankAccountId);

            rs = ps.executeQuery();
            
            if(rs.next()) {
            	result = rs.getBigDecimal(1);
            }

        }catch (Exception ex){
            logger.error("Error getting amount",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return result;
		
	}
	
	public void updateAmount(int bankAccountId, BigDecimal newAmount) {
		Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("UPDATE bankaccount SET AMOUNT=? WHERE ID=?");
            //AMOUNT, ID
            ps.setBigDecimal(1, newAmount);
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
            	bankAccount.setAmount(rs.getBigDecimal(2));

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
            	bankAccount.setAmount(rs.getBigDecimal(2));

            }

        }catch (Exception ex){
            logger.error("Error getting Bank Account",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return bankAccount;
	}
	
	public List<BankAccount> getBankAccounts(int accountId) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<BankAccount> bankAccounts = new ArrayList<>();
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT IBAN, AMOUNT FROM BANKACCOUNT WHERE account_ID=?");
            //account_ID
            ps.setInt(1, accountId);

            rs = ps.executeQuery();
            
            while(rs.next()) {
            	BankAccount bankAccount = new BankAccount();
            	bankAccount.setIBAN(rs.getString(1));
            	bankAccount.setAmount(rs.getBigDecimal(2));
            	bankAccounts.add(bankAccount);

            }

        }catch (Exception ex){
            logger.error("Error getting Bank Account",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return bankAccounts;
	}
	
}
