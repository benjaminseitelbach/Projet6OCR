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
import com.paymybuddy.paymybuddyapp.model.Transaction;

public class TransactionDaoImpl implements TransactionDao {
	private static final Logger logger = LogManager.getLogger("AccountDaoImpl");

	public DBConfig dbConfig = new DBConfig();

	public Transaction addTransaction(Transaction transaction, int relationshipId, int bankAccountId) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dbConfig.getConnection();
			ps = con.prepareStatement(
					"INSERT INTO TRANSACTION(AMOUNT, DESCRIPTION, relationship_ID, bankaccount_ID) values(?,?,?,?)");
			// AMOUNT, DESCRIPTION, relationship_ID, bankaccount_ID
			ps.setFloat(1, transaction.getAmount());
			ps.setString(2, transaction.getDescription());
			ps.setInt(3, relationshipId);
			ps.setInt(4, bankAccountId);
			ps.execute();
			return transaction;
		} catch (Exception ex) {
			logger.error("Error saving new transaction", ex);
		} finally {
			dbConfig.closeConnection(con);
			dbConfig.closePreparedStatement(ps);
		}
		return null;
	}

	public List<Transaction> getTransactions(int relationshipId) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Transaction> transactions = new ArrayList<>();
		try {
			con = dbConfig.getConnection();

			ps = con.prepareStatement("SELECT ID, AMOUNT, DESCRIPTION, bankaccount_ID FROM TRANSACTION"
					+ " WHERE relationship_ID=?");
			ps.setInt(1, relationshipId);
			rs = ps.executeQuery();
			while (rs.next()) {

				Transaction transaction = new Transaction();
				transaction.setId(rs.getInt(1));
				transaction.setAmount(rs.getFloat(2));
				transaction.setDescription(rs.getString(3));
				transaction.setBankAccountId(rs.getInt(4));
				transactions.add(transaction);

			}

		} catch (Exception ex) {
			logger.error("Error getting account ID", ex);
		} finally {
			dbConfig.closeConnection(con);
			dbConfig.closeResultSet(rs);
			dbConfig.closePreparedStatement(ps);
		}

		return transactions;

	}
	/*
	public int getBankAccountId(Transaction transaction) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = new Account();
		try {
			con = dbConfig.getConnection();

			ps = con.prepareStatement("SELECT bankaccount_ID FROM TRANSACTION WHERE ");
			ps.setInt(1, accountId);
			rs = ps.executeQuery();
			if (rs.next()) {

				account.setEmail(rs.getString(1));
				account.setPassword(rs.getString(2));
				account.setFirstName(rs.getString(3));
				account.setLastName(rs.getString(4));

			}

		} catch (Exception ex) {
			logger.error("Error getting account ID", ex);
		} finally {
			dbConfig.closeConnection(con);
			dbConfig.closeResultSet(rs);
			dbConfig.closePreparedStatement(ps);
		}

		return account;

	}
	*/

}
