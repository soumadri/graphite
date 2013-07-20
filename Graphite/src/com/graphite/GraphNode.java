package com.graphite;

import com.mongodb.DBObject;

public class GraphNode {
	DBObject data;
	
	public GraphNode(DBObject data){
		this.data = data;
	}
	
	public DBObject getDBObject(){
		return data;
	}
}
