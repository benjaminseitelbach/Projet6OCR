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
import com.paymybuddy.paymybuddyapp.model.Relationship;

@Repository
public class RelationshipRepository implements IRelationshipRepository {
	
	private static final Logger logger = LogManager.getLogger("RelationshiptDaoImpl");
	
	public DBConfig dbConfig = new DBConfig();
	
	public List<Integer> getConnectionsIds(int accountId) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Integer> connectionsIds = new ArrayList<>();
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT connection_ID FROM RELATIONSHIP WHERE account_ID=?");
            //account_ID, relationship_ID
            ps.setInt(1, accountId);

            rs = ps.executeQuery();
            
            while(rs.next()) {
            	connectionsIds.add(rs.getInt(1));
            }
            return connectionsIds;
        }catch (Exception ex){
            logger.error("Error getting connections IDs from account ID",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return null;
		
	}
	
	public Relationship addRelationship(Relationship relationship) {

		Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("insert into relationship(account_ID, connection_ID) values(?,?)");
            //account_ID, relationship_ID
            ps.setInt(1, relationship.getAccountId());
            ps.setInt(2, relationship.getRelationshipId());
            ps.execute();
            return relationship;
        }catch (Exception ex){
            logger.error("Error saving new account",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return null;
	}
	
	public Relationship addConnection(int accountId, int connectionId) {

		Connection con = null;
        PreparedStatement ps = null;
        Relationship relationship = new Relationship();
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("insert into relationship(account_ID, connection_ID) values(?,?)");
            //account_ID, relationship_ID
            ps.setInt(1, accountId);
            ps.setInt(2, connectionId);
            ps.execute();
            relationship.setAccountId(accountId);
            relationship.setConnectionId(connectionId);
            return relationship;
        }catch (Exception ex){
            logger.error("Error saving new account",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return null;
	}
	
	public List<Integer> getRelationshipsIds(int accountId) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Integer> relationshipsIds = new ArrayList<>();
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT connection_ID FROM RELATIONSHIP WHERE account_ID=?");
            //account_ID, relationship_ID
            ps.setInt(1, accountId);

            rs = ps.executeQuery();
            
            while(rs.next()) {
            	relationshipsIds.add(rs.getInt(1));
            }
            return relationshipsIds;
        }catch (Exception ex){
            logger.error("Error saving new account",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return null;
		
	}
	
	public List<Integer> getAllRelationshipsIds(int accountId) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Integer> relationshipsIds = new ArrayList<>();
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT ID FROM RELATIONSHIP "
            		+ "WHERE account_ID=? OR connection_ID=?");
            //account_ID, relationship_ID
            ps.setInt(1, accountId);
            ps.setInt(2, accountId);

            rs = ps.executeQuery();
            
            while(rs.next()) {
            	relationshipsIds.add(rs.getInt(1));
            }
            return relationshipsIds;
        }catch (Exception ex){
            logger.error("Error getting all relationships IDs",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return null;
		
	}
	
	public List<Relationship> getRelationships(int accountId) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Relationship> relationships = new ArrayList<>();
        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT ID, account_ID, connection_ID FROM RELATIONSHIP WHERE account_ID=?");
            //account_ID
            ps.setInt(1, accountId);

            rs = ps.executeQuery();
            
            while(rs.next()) {
            	Relationship relationship = new Relationship();
            	relationship.setId(rs.getInt(1));
            	relationship.setAccountId(rs.getInt(2));
            	relationship.setRelationshipId(rs.getInt(3));
            	relationships.add(relationship);
            }

        }catch (Exception ex){
            logger.error("Error getting relationships",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return relationships;
		
	}
	
	public int getId(Relationship relationship) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT ID FROM RELATIONSHIP WHERE account_ID=? AND connection_ID=?");
            //account_ID, relationship_ID
            ps.setInt(1, relationship.getAccountId());
            ps.setInt(2, relationship.getRelationshipId());

            rs = ps.executeQuery();
            
            if(rs.next()) {
            	return rs.getInt(1);
            }

        }catch (Exception ex){
            logger.error("Error saving new account",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return 0;
		
	}
	
	public int getConnectionId(int id) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = dbConfig.getConnection();
            ps = con.prepareStatement("SELECT connection_ID FROM RELATIONSHIP WHERE ID=?");
            //ID
            ps.setInt(1, id);

            rs = ps.executeQuery();
            
            if(rs.next()) {
            	return rs.getInt(1);
            }

        }catch (Exception ex){
            logger.error("Error getting relationship ID",ex);
        }finally {
            dbConfig.closeConnection(con);
            dbConfig.closePreparedStatement(ps);            
        }
        return 0;
		
	}
}
