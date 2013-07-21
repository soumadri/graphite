package com.graphite;

import java.util.ArrayList;

public interface Graph {
	public void addNode(GraphNode node);
	public void addEdge(GraphEdge edge);
	public void deleteEdge(GraphNode node1, GraphNode node2);
	public void deleteEdge(GraphEdge edge);
	public ArrayList<GraphNode> getNodes(String key, String value);
	public ArrayList<GraphEdge> getNeighbors(String from);
	public boolean isAdjacent(GraphNode node1, GraphNode node2);
}
