package com.graphite;

import java.util.ArrayList;

public interface Graph {
	public void addNode(GraphNode node);
	public String addEdge(GraphEdge edge);
	public void deleteEdge(GraphNode node1, GraphNode node2);
	public void deleteEdge(GraphEdge edge);
	public ArrayList<GraphNode> getNodes(String key, String value);
	public ArrayList<GraphEdge> getNeighbors(String from);
	public boolean isAdjacent(GraphNode node1, GraphNode node2);
	
	ArrayList<GraphEdge> getNeighborsWithOutgoingProperty(String from, String property);
	
	ArrayList<GraphEdge> getNeighborsWithIncomingProperty(String to, String property);
	
	public ArrayList<GraphNode> getNodesWithValuesStartingFrom(String key, String value, String collection);
	
	public GraphNode getNodeById(String id,String collection);
	
	public GraphEdge getEdgeById(String id);
	
	public void saveEdge(GraphEdge edge);
	
}
