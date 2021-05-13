package com.paymybuddy.paymybuddyapp.dao;

import java.util.List;

import com.paymybuddy.paymybuddyapp.model.Account;
import com.paymybuddy.paymybuddyapp.model.Relationship;

public interface IRelationshipRepository {
	
	public Relationship addConnection(int accountId, int connectionId);
	
	public List<Integer> getConnectionsIds(int accountId);
	
	public List<Integer> getAllRelationshipsIds(int accountId);

	public int getConnectionId(int relationshipId);
	
	public int getId(Relationship relationship);
	
	
}
