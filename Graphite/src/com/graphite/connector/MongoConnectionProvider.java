package com.graphite.connector;

import com.mongodb.Mongo;

public class MongoConnectionProvider {
	static int instanceCount;
	static Mongo m;
	
	private MongoConnectionProvider(){}
	
	public static Mongo getMongo(MongoConfiguration config){
		if(instanceCount == 0) {
			m = new Mongo(config.getAddresses());
		}
		
		return m;
	}
}
