package com.graphite;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class GraphEdge {
	String from; 
	boolean directed; 
	String to;
	String property;
	float weight;
	
	public GraphEdge(String from, boolean directed, String to, String property){
		this.from = from;
		this.directed = directed;
		this.to = to;
		this.property = property;
	}
	
	public GraphEdge(String from, boolean directed, String to, String property, float weight){
		this.from = from;
		this.directed = directed;
		this.to = to;
		this.property = property;
		this.weight = weight;
	}
	
	public DBObject getDBObject(){
		return new BasicDBObject("from",from)
								.append("directed", directed)
								.append("property", property)
								.append("to", to)
								.append("weight", weight);
	}
}
