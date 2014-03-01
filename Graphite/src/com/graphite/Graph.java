package com.graphite;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

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
	
	ArrayList<GraphEdge> getNeighborsWithFromTo(String from, String to,String property);
	
	public ArrayList<GraphNode> getNodesWithValuesStartingFrom(String key, String value, String collection);
	
	public GraphNode getNodeById(String id,String collection);
	
	public GraphEdge getEdgeById(String id);
	
	public void saveEdge(GraphEdge edge);
	
	ArrayList<GraphEdge> getEdgesWithExpireDate(Date date,String property);
	
	ArrayList<GraphEdge> getEdgesWithOutgoingProperty(String from,String property,String sortBy,int limit,int order);
	
	ArrayList<GraphEdge> getEdgesWithProperties(Map map);
	
	ArrayList<GraphEdge> getEdgesWithProperties(Map map,String sortBy,int limit,int order);
	
	boolean deleteNodeById(String id,String collection);
	
	boolean updateNodeById(String id,String collection,Map map);
	
	ArrayList<GraphNode> getNodesOfCollection(String collection);
}
