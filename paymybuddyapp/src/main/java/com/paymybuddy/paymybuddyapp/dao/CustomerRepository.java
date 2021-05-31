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
import com.paymybuddy.paymybuddyapp.model.Customer;
import com.paymybuddy.paymybuddyapp.model.Transaction;

@Repository
public class CustomerRepository implements ICustomerRepository {

	private static final Logger logger = LogManager.getLogger("AccountDaoImpl");

	public DBConfig dbConfig = new DBConfig();

	public Customer getCustomerInfos(String email) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Customer customer = new Customer();
		try {
			con = dbConfig.getConnection();

			ps = con.prepareStatement("SELECT ID, FIRSTNAME, LASTNAME, AMOUNT FROM CUSTOMER WHERE EMAIL=?");
			//USERNAME
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				customer.setId(rs.getInt(1));
				customer.setEmail(email);
				customer.setFirstName(rs.getString(2));
				customer.setLastName(rs.getString(3));
				customer.setAmount(rs.getBigDecimal(4));
			}

		} catch (Exception ex) {
			logger.error("Error getting customer infos by email", ex);
		} finally {
			dbConfig.closeConnection(con);
			dbConfig.closeResultSet(rs);
			dbConfig.closePreparedStatement(ps);
		}

		return customer;
	}
	
	public Customer getCustomerInfos(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Customer customer = new Customer();
		try {
			con = dbConfig.getConnection();

			ps = con.prepareStatement("SELECT EMAIL, FIRSTNAME, LASTNAME, AMOUNT FROM CUSTOMER WHERE ID=?");
			//ID
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				customer.setId(id);
				customer.setEmail(rs.getString(1));
				customer.setFirstName(rs.getString(2));
				customer.setLastName(rs.getString(3));
				customer.setAmount(rs.getBigDecimal(4));
			}

		} catch (Exception ex) {
			logger.error("Error getting customer infos by ID", ex);
		} finally {
			dbConfig.closeConnection(con);
			dbConfig.closeResultSet(rs);
			dbConfig.closePreparedStatement(ps);
		}

		return customer;
	}
	
	public boolean emailExists(String email) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = dbConfig.getConnection();

			ps = con.prepareStatement("SELECT EMAIL FROM CUSTOMER WHERE EMAIL=?");
			//EMAIL
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}

		} catch (Exception ex) {
			logger.error("Error checking email exists", ex);
		} finally {
			dbConfig.closeConnection(con);
			dbConfig.closeResultSet(rs);
			dbConfig.closePreparedStatement(ps);
		}

		return false;
	}
	
	public boolean passwordCorresponds(String email, String password) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = dbConfig.getConnection();

			ps = con.prepareStatement("SELECT PASSWORD FROM CUSTOMER WHERE EMAIL=?");
			//EMAIL
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				if(rs.getString(1).equals(password)) {
					return true;
				}
			}

		} catch (Exception ex) {
			logger.error("Error checking password corresponds", ex);
		} finally {
			dbConfig.closeConnection(con);
			dbConfig.closeResultSet(rs);
			dbConfig.closePreparedStatement(ps);
		}

		return false;
	}
	
	public List<Customer> getConnections(int customerId) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Customer> result = new ArrayList<>();
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT CUSTOMER.ID, EMAIL, FIRSTNAME, LASTNAME, AMOUNT FROM CUSTOMER"
            		+ " INNER JOIN RELATIONSHIP ON connection_ID = CUSTOMER.ID WHERE customer_ID=?");
            //customer_ID
            ps.setInt(1, customerId);
            rs = ps.executeQuery();
            
            while(rs.next()) {
            	Customer connection = new Customer();
            	connection.setId(rs.getInt(1));
            	connection.setEmail(rs.getString(2));
            	connection.setFirstName(rs.getString(3));
            	connection.setLastName(rs.getString(4));
            	connection.setAmount(rs.getBigDecimal(5));
            	result.add(connection);
            }

        }catch (Exception ex){
            logger.error("Error getting connections from customer ID",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);
            dbConfig.closeResultSet(rs);
        }
        return result;
		
	}

	public List<Transaction> getTransactions(int customerId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Transaction> result = new ArrayList<>();
		try {
			con = dbConfig.getConnection();

			ps = con.prepareStatement("SELECT TRANSACTION.ID, DESCRIPTION, TRANSACTION.AMOUNT, CUSTOMER.ID,"
					+ " EMAIL, FIRSTNAME, LASTNAME, CUSTOMER.AMOUNT FROM TRANSACTION"
					+ " INNER JOIN RELATIONSHIP ON relationship_ID=RELATIONSHIP.ID"
					+ " INNER JOIN CUSTOMER ON connection_ID=CUSTOMER.ID WHERE customer_ID=?");
			ps.setInt(1, customerId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setId(rs.getInt(1));
				transaction.setDescription(rs.getString(2));
				transaction.setAmount(rs.getBigDecimal(3));
				
				Customer connection = new Customer();
				connection.setId(rs.getInt(4));
				connection.setEmail(rs.getString(5));
				connection.setFirstName(rs.getString(6));
				connection.setLastName(rs.getString(7));
				connection.setAmount(rs.getBigDecimal(8));
				transaction.setReceiver(connection);
				result.add(transaction);
			}

		} catch (Exception ex) {
			logger.error("Error getting transactions from customer ID", ex);
		} finally {
			dbConfig.closeConnection(con);
			dbConfig.closeResultSet(rs);
			dbConfig.closePreparedStatement(ps);
		}

		return result;

	}
	
	public int getId(Customer customer) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = dbConfig.getConnection();

			ps = con.prepareStatement("SELECT ID FROM CUSTOMER WHERE EMAIL=? AND PASSWORD=?");
			ps.setString(1, customer.getEmail());
			ps.setString(2, customer.getPassword());
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception ex) {
			logger.error("Error getting account ID", ex);
		} finally {
			dbConfig.closeConnection(con);
			dbConfig.closeResultSet(rs);
			dbConfig.closePreparedStatement(ps);
		}

		return 0;

	}
	
	public int getId(String email) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = dbConfig.getConnection();

			ps = con.prepareStatement("SELECT ID FROM CUSTOMER WHERE EMAIL=?");
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception ex) {
			logger.error("Error getting account ID", ex);
		} finally {
			dbConfig.closeConnection(con);
			dbConfig.closeResultSet(rs);
			dbConfig.closePreparedStatement(ps);
		}

		return 0;

	}
	
	public int getId(String firstName, String lastName) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = dbConfig.getConnection();

			ps = con.prepareStatement("SELECT ID FROM CUSTOMER WHERE FIRSTNAME=? AND LASTNAME=?");
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception ex) {
			logger.error("Error getting account ID", ex);
		} finally {
			dbConfig.closeConnection(con);
			dbConfig.closeResultSet(rs);
			dbConfig.closePreparedStatement(ps);
		}

		return 0;

	}

	public List<Customer> getAccounts(List<Integer> accountsIds) {
		Connection con = null;
    	PreparedStatement ps = null;
        ResultSet rs = null;
        List<Customer> accounts = new ArrayList<>();
        try {
        	con = dbConfig.getConnection();
        	for(Integer accountId : accountsIds) {
        		ps = con.prepareStatement("SELECT EMAIL, PASSWORD, FIRSTNAME, LASTNAME FROM CUSTOMER WHERE ID=?");
                ps.setInt(1, accountId);
            	rs = ps.executeQuery();
            	if(rs.next()) {
            		Customer account = new Customer();
            		account.setEmail(rs.getString(1));
            		account.setPassword(rs.getString(2));
            		account.setFirstName(rs.getString(3));
            		account.setLastName(rs.getString(4));
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

	public Customer getCustomer(int id) {
		Connection con = null;
    	PreparedStatement ps = null;
        ResultSet rs = null;
        Customer account = new Customer();
        try {
        	con = dbConfig.getConnection();
        	
        	ps = con.prepareStatement("SELECT EMAIL, PASSWORD, FIRSTNAME, LASTNAME, AMOUNT FROM CUSTOMER WHERE ID=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
           
            	account.setEmail(rs.getString(1));
            	account.setPassword(rs.getString(2));
            	account.setFirstName(rs.getString(3));
            	account.setLastName(rs.getString(4));
            	account.setAmount(rs.getBigDecimal(5));

            }

        }catch (Exception ex){
            logger.error("Error getting account ID", ex);
        }finally {
        	dbConfig.closeConnection(con);
            dbConfig.closeResultSet(rs);
            dbConfig.closePreparedStatement(ps);
        }

		return account;
				
	}
	
	public Customer getCustomer(String email, String password) {
		Connection con = null;
    	PreparedStatement ps = null;
        ResultSet rs = null;
        Customer account = new Customer();
        try {
        	con = dbConfig.getConnection();
        	
        	ps = con.prepareStatement("SELECT EMAIL, PASSWORD, FIRSTNAME, LASTNAME FROM CUSTOMER WHERE EMAIL=?"
        			+ " AND PASSWORD=?");
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()) {
           
            	account.setEmail(rs.getString(1));
            	account.setPassword(rs.getString(2));
            	account.setFirstName(rs.getString(3));
            	account.setLastName(rs.getString(4));

            }

        }catch (Exception ex){
            logger.error("Error getting account ID", ex);
        }finally {
        	dbConfig.closeConnection(con);
            dbConfig.closeResultSet(rs);
            dbConfig.closePreparedStatement(ps);
        }

		return account;
				
	}
	
	public boolean updateAmount(int id, BigDecimal newAmount) {
		Connection con = null;
    	PreparedStatement ps = null;

        try {
        	con = dbConfig.getConnection();
        	
        	ps = con.prepareStatement("UPDATE CUSTOMER SET AMOUNT=? WHERE ID=?");
            ps.setBigDecimal(1, newAmount);
            ps.setInt(2, id);
            if(ps.execute()) {
            	return true;
            }
            

        }catch (Exception ex){
            logger.error("Error updating amount", ex);
        }finally {
        	dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);
        }

		return false;
				
	}
}
