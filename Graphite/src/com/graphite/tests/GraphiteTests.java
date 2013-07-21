package com.graphite.tests;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.graphite.BasicGraph;
import com.graphite.GraphEdge;
import com.graphite.GraphNode;
import com.graphite.GraphProvider;
import com.graphite.connector.MongoConfiguration;
import com.graphite.connector.MongoConnectionProvider;
import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;

public class GraphiteTests{
	static MongoConfiguration config;
	
	@BeforeClass
    public static void oneTimeSetUp() throws UnknownHostException {
        // one-time initialization code   
    	System.out.println("Setting up DB config");
    	config = new MongoConfiguration();
		config.addServer("localhost", 27017);
		config.setDatabase("saola");
		config.setNodesCollectionName("nodes");
		config.setEdgesCollectionName("edges");
    }
 
    @AfterClass
    public static void oneTimeTearDown() {
        // one-time cleanup code   
    	System.out.println("Cleaning up the DB");
    	Mongo m = MongoConnectionProvider.getMongo(config);
    	m.dropDatabase("saola");
    }
		
	@Test
	public void testConnection() throws UnknownHostException {		
		Mongo m = MongoConnectionProvider.getMongo(config);
		assertNotNull(m.getDB("saola"));		
	}
	
	@Test
	public void testNodeInsert() throws UnknownHostException {				
		//Data 
		GraphNode node = new GraphNode("name", "MongoDB").
                append("type", "database").
                append("count", 1).
                append("info", new BasicDBObject("x", 203).append("y", 102));
		//GraphNode node = new GraphNode(doc);
		
		//Create a graph
		BasicGraph g = GraphProvider.getGraph(config);
		g.addNode(node);	//Add the node
		g.addNode(node, "AnotherCollection");

		assertTrue(g.getNodes("name", "MongoDB").size() > 0);
		assertTrue(g.getNodes("name", "MongoDB", "AnotherCollection").size() > 0);
	}
	
	@Test
	public void testEdgeInsert() throws UnknownHostException {
		//Data 
		GraphNode node1 = new GraphNode("_id","soumadri@gmail.com").
				append("name", "MongoDB").
                append("type", "database").
                append("count", 1).
                append("info", new GraphNode("x", 203).append("y", 102));
		GraphNode node2 = new GraphNode("_id","gouravkakkar@gmail.com").
				append("name", "MongoDB").
                append("type", "database").
                append("count", 2).
                append("info", new GraphNode("x", 203).append("y", 102));
		
		//Create a graph
		BasicGraph g = GraphProvider.getGraph(config);

		//Add the nodes
		g.addNode(node1);	
		g.addNode(node2);
		
		//Create an edge
		GraphEdge edge = new GraphEdge("soumadri@gmail.com", false, "gouravkakkar@gmail.com", "friend");
		g.addEdge(edge);

		assertTrue(g.getNodes("_id", "soumadri@gmail.com").size() > 0);
		assertTrue(g.getNodes("_id", "gouravkakkar@gmail.com").size() > 0);
		assertTrue(g.getEdges("soumadri@gmail.com").size() > 0);	
	}
	
}
