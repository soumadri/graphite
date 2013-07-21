package com.graphite;

import com.graphite.connector.MongoConfiguration;

public class GraphProvider {
	static int instanceCount;
	static BasicGraph graph;
	
	private GraphProvider(){}
	
	public static BasicGraph getGraph(MongoConfiguration config){
		if(instanceCount == 0) {
			graph = new BasicGraph(config);
		}
		
		return graph;
	}
}