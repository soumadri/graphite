package com.graphite;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;

import com.graphite.connector.MongoConfiguration;
import com.graphite.connector.MongoConnectionProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
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
		
		if(config.getUserName()!=null){
			//username has been provided, please authenticate
			
			if(db.authenticate(config.getUserName(), config.getPassword().toCharArray()) == false) {
	            try {
					throw new Exception("UnAuthorized Access");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }	
		}
		
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
	public String addNode(GraphNode node, String collection){
		db.getCollection(collection).insert(node);
		return  node.get( "_id" ).toString();
	}
	
	/**
	 * Adds an edge to the edges collection
	 * @param edge A GraphEdhe object
	 */
	@Override
	public String addEdge(GraphEdge edge){
		edges.insert(edge);
		return edge.get( "_id" ).toString();
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
		       node.put("_id", node.get("_id").toString());
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
	public ArrayList<GraphEdge> getNeighborsWithOutgoingProperty(String from,String property){
		ArrayList<GraphEdge> foundEdges = new ArrayList<GraphEdge>();
		BasicDBObject query = new BasicDBObject("from", from);
		query.append("property", property);
		
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
		edges.remove(edge.edgeObject);

	}

	@Override
	public ArrayList<GraphEdge> getNeighborsWithIncomingProperty(String to,
			String property) {
		ArrayList<GraphEdge> foundEdges = new ArrayList<GraphEdge>();
		BasicDBObject query = new BasicDBObject("to", to);
		query.append("property", property);
		
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
	public ArrayList<GraphNode> getNodesWithValuesStartingFrom(String key,
			String value, String collection) {
		
		ArrayList<GraphNode> foundNodes = new ArrayList<GraphNode>();
		BasicDBObject query = new BasicDBObject();
		query.put(key,java.util.regex.Pattern.compile(value));
		                   
		DBCollection coll = db.getCollection(collection);
		coll.setObjectClass(GraphNode.class);
		DBCursor cursor = coll.find(query).limit(10);

		try {
		   while(cursor.hasNext()) {		       
		       GraphNode node = (GraphNode) cursor.next();
		       node.put("_id", node.get("_id").toString());
		       foundNodes.add(node);
		   }
		} finally {
		   cursor.close();
		}
		
		return foundNodes;
		
	}

	/* (non-Javadoc)
	 * @see com.graphite.Graph#getNodeById(java.lang.String)
	 */
	@Override
	public GraphNode getNodeById(String id,String collection) {
		ArrayList<GraphNode> foundNodes = new ArrayList<GraphNode>();
		BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
		DBCollection coll = db.getCollection(collection);
		coll.setObjectClass(GraphNode.class);
		DBCursor cursor = coll.find(query);
		try {
		   while(cursor.hasNext()) {		       
		       GraphNode node = (GraphNode) cursor.next();
		       node.put("_id", node.get("_id").toString());
		       foundNodes.add(node);
		   }
		} finally {
		   cursor.close();
		}
		
		return foundNodes.get(0);

	}

	/* (non-Javadoc)
	 * @see com.graphite.Graph#getEdgeById(java.lang.String)
	 */
	@Override
	public GraphEdge getEdgeById(String id) {
		
		ArrayList<GraphEdge> foundNodes = new ArrayList<GraphEdge>();
		BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
		DBCollection coll = db.getCollection("edges");
		coll.setObjectClass(GraphEdge.class);
		DBCursor cursor = coll.find(query);
		try {
		   while(cursor.hasNext()) {		       
			   GraphEdge edge = (GraphEdge) cursor.next();
			   edge.put("_id", edge.get("_id"));
		       foundNodes.add(edge);
		   }
		} finally {
		   cursor.close();
		}
		
		return foundNodes.get(0);

	}

	public void saveEdge(GraphEdge edge){
		edges.save(edge);
	}

	
	@Override
	public ArrayList<GraphEdge> getNeighborsWithFromTo(String from, String to,String property) {
		
		ArrayList<GraphEdge> foundEdges = new ArrayList<GraphEdge>();
		BasicDBObject query = new BasicDBObject("from", from);
		query.put("to", to);
		query.put("property", property);
		DBCollection coll = db.getCollection("edges");
		coll.setObjectClass(GraphEdge.class);
		DBCursor cursor = coll.find(query);
		try {
		   while(cursor.hasNext()) {		       
			   GraphEdge edge = (GraphEdge) cursor.next();
			   edge.put("_id", edge.get("_id").toString());
		       foundEdges.add(edge);
		   }
		} finally {
		   cursor.close();
		}
		
		return foundEdges;
	}

	@Override
	public ArrayList<GraphEdge> getEdgesWithExpireDate(Date date,String property) {
		ArrayList<GraphEdge> foundEdges = new ArrayList<GraphEdge>();
		BasicDBObject query = new BasicDBObject("property", property);
		query.append("expiryDate",date);
		
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
	public ArrayList<GraphEdge> getEdgesWithOutgoingProperty(String from,
			String property, String sortBy, int limit, int order) {
		ArrayList<GraphEdge> foundEdges = new ArrayList<GraphEdge>();
		BasicDBObject query = new BasicDBObject("from", from);
		query.append("property", property);
		
		edges.setObjectClass(GraphEdge.class);
		DBCursor cursor = edges.find(query).sort(new BasicDBObject(sortBy, order)).limit(limit);
		
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
	public ArrayList<GraphEdge> getEdgesWithProperties(Map map) {
	
		ArrayList<GraphEdge> foundEdges = new ArrayList<GraphEdge>();
		BasicDBObject query = new BasicDBObject();
		
		Iterator entries = map.entrySet().iterator();
		while (entries.hasNext()) {
		  Entry thisEntry = (Entry) entries.next();
		  String key = (String) thisEntry.getKey();
		  Object value = thisEntry.getValue();
		  query.append(key, value);
		}
		
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
	public boolean deleteNodeById(String id,String collection) {
		
		BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
		DBCollection coll = db.getCollection(collection);
		coll.setObjectClass(GraphNode.class);
		DBObject doc = coll.findOne(query);
		coll.remove(doc);
		return true;
	}

	@Override
	public ArrayList<GraphEdge> getEdgesWithProperties(Map map, String sortBy,
			int limit, int order) {
		ArrayList<GraphEdge> foundEdges = new ArrayList<GraphEdge>();
		BasicDBObject query = new BasicDBObject();
		
		Iterator entries = map.entrySet().iterator();
		while (entries.hasNext()) {
		  Entry thisEntry = (Entry) entries.next();
		  String key = (String) thisEntry.getKey();
		  Object value = thisEntry.getValue();
		  query.append(key, value);
		}
		
		edges.setObjectClass(GraphEdge.class);
		DBCursor cursor = edges.find(query).sort(new BasicDBObject(sortBy, order)).limit(limit);
		
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
	public boolean updateNodeById(String id,String collection, Map map) {
		
		BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
		DBCollection coll = db.getCollection(collection);
		coll.setObjectClass(GraphNode.class);
		
		BasicDBObject newDocument = new BasicDBObject();
		
		Iterator entries = map.entrySet().iterator();
		while (entries.hasNext()) {
		  Entry thisEntry = (Entry) entries.next();
		  String key = (String) thisEntry.getKey();
		  Object value = thisEntry.getValue();
		  newDocument.append("$set", new BasicDBObject().append(key, value));
		}
		
		coll.update(query, newDocument);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.graphite.Graph#getNodesOfCollection(java.lang.String)
	 */
	@Override
	public ArrayList<GraphNode> getNodesOfCollection(String collection) {
		ArrayList<GraphNode> foundNodes = new ArrayList<GraphNode>();
		DBCollection coll = db.getCollection(collection);
		coll.setObjectClass(GraphNode.class);
		DBCursor cursor = coll.find();
		
		try {
			   while(cursor.hasNext()) {		       
			       GraphNode node = (GraphNode) cursor.next();
			       node.put("_id", node.get("_id").toString());
			       foundNodes.add(node);
			   }
			} finally {
			   cursor.close();
			}
			
			return foundNodes;
	}}
