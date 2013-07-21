package com.graphite;

import java.util.ArrayList;

import com.graphite.connector.MongoConfiguration;
import com.graphite.connector.MongoConnectionProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class BasicGraph implements Graph {
	MongoConfiguration config;
	Mongo m;
	DB db;
	DBCollection nodes, edges;
	
	/**
	 * Creates a graph over Mongo with the given set of collections
	 * It needs 2 collections. One to store nodes, other to store edges
	 * @param config A MongoConfiguration object specifying server address, collection names etc.
	 */
	public BasicGraph(MongoConfiguration config) {
		this.config = config;
		m = MongoConnectionProvider.getMongo(config);
		db = m.getDB(config.getDatabase());
		
		//If nodes and edges collection does not exists
		//create it
		nodes = db.getCollection(config.getNodesCollectionName());
		edges = db.getCollection(config.getEdgesCollectionName());
	}
	
	/**
	 * Adds a node to the nodes collection
	 * @param node A GraphNode object
	 */
	@Override
	public void addNode(GraphNode node){
		nodes.insert(node);
	}
	
	/**
	 * Adds a node to the specified collection
	 * @param node A GraphNode object
	 * @param collection The name of the collection
	 */
	public void addNode(GraphNode node, String collection){
		db.getCollection(collection).insert(node);
	}
	
	/**
	 * Adds an edge to the edges collection
	 * @param edge A GraphEdhe object
	 */
	@Override
	public void addEdge(GraphEdge edge){
		edges.insert(edge);		
	}
	
	/**
	 * Returns a list of GraphNodes matching the key-value pair from the nodes
	 * collection the graph object is configured with
	 * @param key A key to look into
	 * @param value A value to look for
	 * @return A list of GraphNodes
	 */
	@Override
	public ArrayList<GraphNode> getNodes(String key, String value){
		ArrayList<GraphNode> foundNodes = new ArrayList<GraphNode>();
		BasicDBObject query = new BasicDBObject(key, value);
		
		nodes.setObjectClass(GraphNode.class);
		DBCursor cursor = nodes.find(query);

		try {
		   while(cursor.hasNext()) {			        
		       GraphNode node = (GraphNode) cursor.next();
		       foundNodes.add(node);
		   }
		} finally {
		   cursor.close();
		}
		
		return foundNodes;
	}
	
	/**
	 * Returns a list of GraphNodes matching the key-value pair from the specified collection
	 * @param key A key to look into
	 * @param value A value to look for
	 * @param collection A collection to lookup the object(s)
	 * @return A list of GraphNodes
	 */
	public ArrayList<GraphNode> getNodes(String key, String value, String collection){
		ArrayList<GraphNode> foundNodes = new ArrayList<GraphNode>();
		BasicDBObject query = new BasicDBObject(key, value);
		DBCollection coll = db.getCollection(collection);
		coll.setObjectClass(GraphNode.class);
		DBCursor cursor = coll.find(query);

		try {
		   while(cursor.hasNext()) {		       
		       GraphNode node = (GraphNode) cursor.next();
		       foundNodes.add(node);
		   }
		} finally {
		   cursor.close();
		}
		
		return foundNodes;
	}
	
	@Override
	public ArrayList<GraphEdge> getNeighbors(String from){
		ArrayList<GraphEdge> foundEdges = new ArrayList<GraphEdge>();
		BasicDBObject query = new BasicDBObject("from", from);
		
		edges.setObjectClass(GraphEdge.class);
		DBCursor cursor = edges.find(query);
		
		try {
		   while(cursor.hasNext()) {			   		       
		       GraphEdge edge = (GraphEdge) cursor.next();
		       foundEdges.add(edge);
		   }
		} finally {
		   cursor.close();
		}
		
		return foundEdges;
	}

	@Override
	public boolean isAdjacent(GraphNode node1, GraphNode node2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteEdge(GraphNode node1, GraphNode node2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEdge(GraphEdge edge) {
		// TODO Auto-generated method stub
		
	}
}
