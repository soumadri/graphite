package com.graphite;

import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class GraphEdge implements DBObject {	
	BasicDBObject edgeObject;	
	
	public GraphEdge(){
		edgeObject = new BasicDBObject();
	}

	public GraphEdge(String from, boolean directed, String to, String property){	
		edgeObject = new BasicDBObject();
		edgeObject.put("from", from);
		edgeObject.put("directed", directed);
		edgeObject.put("to", to);
		edgeObject.put("property", property);
	}
	
	public GraphEdge(String from, boolean directed, String to, String property, float weight){	
		edgeObject = new BasicDBObject();
		edgeObject.put("from", from);
		edgeObject.put("directed", directed);
		edgeObject.put("to", to);
		edgeObject.put("property", property);
		edgeObject.put("weight", weight);
	}	
	
	@Override
	public boolean containsField(String field) {		
		return edgeObject.containsField(field);
	}

	@Override
	public boolean containsKey(String key) {
		return edgeObject.containsKey(key);
	}

	@Override
	public Object get(String key) {
		return edgeObject.get(key);
	}

	@Override
	public Set<String> keySet() {		
		return edgeObject.keySet();
	}

	@Override
	public Object put(String key, Object value) {		
		return edgeObject.put(key, value);
	}

	@Override
	public void putAll(BSONObject object) {		
		edgeObject.putAll(object);
	}

	@Override
	public void putAll(Map map) {
		edgeObject.putAll(map);
	}

	@Override
	public Object removeField(String field) {
		return edgeObject.removeField(field);
	}

	@Override
	public Map toMap() {		
		return edgeObject.toMap();
	}

	@Override
	public boolean isPartialObject() {		
		return edgeObject.isPartialObject();
	}

	@Override
	public void markAsPartialObject() {
		edgeObject.markAsPartialObject();		
	}
}
