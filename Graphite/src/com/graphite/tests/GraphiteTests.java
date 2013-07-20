package com.graphite.tests;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.Test;

import com.graphite.Graph;
import com.graphite.GraphNode;
import com.graphite.connector.MongoConfiguration;
import com.graphite.connector.MongoConnectionProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;

public class GraphiteTests {

	@Test
	public void testConnection() throws UnknownHostException {
		MongoConfiguration config = new MongoConfiguration();
		config.addServer("localhost", 27017);
		Mongo m = MongoConnectionProvider.getMongo(config);
		assertNotNull(m.getDB("saola"));		
	}
	
	@Test
	public void testNodeInsert() throws UnknownHostException {
		MongoConfiguration config = new MongoConfiguration();
		config.addServer("localhost", 27017);
		config.setDatabase("saola");
		config.setNodesCollectionName("nodes");
		config.setEdgesCollectionName("edges");
		
		//Data 
		BasicDBObject doc = new BasicDBObject("name", "MongoDB").
                append("type", "database").
                append("count", 1).
                append("info", new BasicDBObject("x", 203).append("y", 102));
		GraphNode node = new GraphNode(doc);
		
		//Create a graph
		Graph g = new Graph(config);
		g.addNode(node);	//Add the node

		assertTrue(g.getNodes("name", "MongoDB").size() > 0);	
	}
}
