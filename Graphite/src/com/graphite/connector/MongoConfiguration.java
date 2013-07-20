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
}
