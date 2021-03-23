package com.paymybuddy.paymybuddyapp.config;

import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.paymybuddy.paymybuddyapp.files.FilesReader;

public class DBConfig {

	private static final Logger logger = LogManager.getLogger("Controller");

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		logger.info("Create DB connection");

		Class.forName("com.mysql.cj.jdbc.Driver");
		String username = FilesReader.getDBParameter("username");
		String password = FilesReader.getDBParameter("password");
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/paymybuddyprod?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC", username,
				password);
	}

	public void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
				logger.info("Closing DB connection");
			} catch (SQLException e) {
				logger.error("Error while closing connection", e);
			}
		}
	}

	public void closePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
				logger.info("Closing Prepared Statement");
			} catch (SQLException e) {
				logger.error("Error while closing prepared statement", e);
			}
		}
	}

	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				logger.info("Closing Result Set");
			} catch (SQLException e) {
				logger.error("Error while closing result set", e);
			}
		}
	}
}
