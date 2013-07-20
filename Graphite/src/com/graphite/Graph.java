package com.graphite;

import java.util.ArrayList;

import com.graphite.connector.MongoConfiguration;
import com.graphite.connector.MongoConnectionProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class Graph {
	MongoConfiguration config;
	Mongo m;
	DBCollection nodes, edges;
	
	public Graph(MongoConfiguration config) {
		this.config = config;
		m = MongoConnectionProvider.getMongo(config);
		DB db = m.getDB(config.getDatabase());
		
		//If nodes and edges collection does not exists
		//create it
		nodes = db.getCollection(config.getNodesCollectionName());
		edges = db.getCollection(config.getEdgesCollectionName());
	}
	
	public void addNode(GraphNode node){
		nodes.insert(node.getDBObject());
	}
	
	public void addEdge(GraphEdge edge){
		edges.insert(edge.getDBObject());
	}
	
	public ArrayList<GraphNode> getNodes(String key, String value){
		ArrayList<GraphNode> foundNodes = new ArrayList<GraphNode>();
		BasicDBObject query = new BasicDBObject(key, value);
		
		DBCursor cursor = nodes.find(query);

		try {
		   while(cursor.hasNext()) {
		       DBObject item = cursor.next();
		       GraphNode node = new GraphNode(item);
		       foundNodes.add(node);
		   }
		} finally {
		   cursor.close();
		}
		
		return foundNodes;
	}
}
