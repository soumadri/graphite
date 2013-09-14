package com.graphite.connector;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.ServerAddress;

public class MongoConfiguration {
	//Arrays of Mongo replica set addresses
	ArrayList<ServerAddress> addresses;
	String database;
	String nodesCollectionName;
	String edgesCollectionName;
	String userName;
	String password;
	
	public MongoConfiguration(){
		addresses = new ArrayList<ServerAddress>();
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getNodesCollectionName() {
		return nodesCollectionName;
	}

	public void setNodesCollectionName(String nodesCollectionName) {
		this.nodesCollectionName = nodesCollectionName;
	}

	public String getEdgesCollectionName() {
		return edgesCollectionName;
	}

	public void setEdgesCollectionName(String edgesCollectionName) {
		this.edgesCollectionName = edgesCollectionName;
	}

	public ArrayList<ServerAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(ArrayList<ServerAddress> addresses) {
		this.addresses = addresses;
	}
	
	public void addServer(String host, int port) throws UnknownHostException {
		addresses.add(new ServerAddress(host, port));
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
