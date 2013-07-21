package com.graphite;

import java.util.ArrayList;

public interface Graph {
	public void addNode(GraphNode node);
	public void addEdge(GraphEdge edge);
	public ArrayList<GraphNode> getNodes(String key, String value);
	public ArrayList<GraphEdge> getEdges(String from);
	public boolean hasRelation(GraphNode node1, GraphNode node2);
}
