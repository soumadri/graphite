package com.graphite;

import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class GraphNode implements DBObject {
	BasicDBObject nodeObject;			
	
	public GraphNode() {
		nodeObject = new BasicDBObject();
	}
	
	public GraphNode(String key, Object value){
		nodeObject = new BasicDBObject(key,value);			
	}
	
	public GraphNode append(String key, Object value){
		nodeObject.append(key, value);
		return this;
	}

	@Override
	public boolean containsField(String field) {		
		return nodeObject.containsField(field);
	}

	@Override
	public boolean containsKey(String key) {
		return nodeObject.containsKey(key);
	}

	@Override
	public Object get(String key) {
		return nodeObject.get(key);
	}

	@Override
	public Set<String> keySet() {		
		return nodeObject.keySet();
	}

	@Override
	public Object put(String key, Object value) {		
		return nodeObject.put(key, value);
	}

	@Override
	public void putAll(BSONObject object) {		
		nodeObject.putAll(object);
	}

	@Override
	public void putAll(Map map) {
		nodeObject.putAll(map);
	}

	@Override
	public Object removeField(String field) {
		return nodeObject.removeField(field);
	}

	@Override
	public Map toMap() {		
		return nodeObject.toMap();
	}

	@Override
	public boolean isPartialObject() {		
		return nodeObject.isPartialObject();
	}

	@Override
	public void markAsPartialObject() {
		nodeObject.markAsPartialObject();		
	}
}
