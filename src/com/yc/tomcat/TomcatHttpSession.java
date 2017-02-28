package com.yc.tomcat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import yc.javax.servlet.ServletContext;
import yc.javax.servlet.http.HttpSession;

public class TomcatHttpSession implements HttpSession {
	private Map<String,Object> attributes=new HashMap<String,Object>();
	private long creationTime;
	private String id;
	private long lastAccessedTime;
	
	
	@Override
	public Object getAttribute(String name) {
		if(   attributes.containsKey(name)){
			return attributes.get(name);
		}
		return null;
	}

	@Override
	public Set<String> getAttributeNames() {
		return attributes.keySet();
	}
    //构造的方法
	public TomcatHttpSession(     ){
		this.id=UUID.randomUUID().toString();
		Date d=new Date();
		this.creationTime=d.getTime();
		this.lastAccessedTime=creationTime;
	}
	
	@Override
	public long getCreationTime() {
		return this.creationTime;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public long getLastAccessedTime() {
		return this.lastAccessedTime;
	}


	@Override
	public void removeAttribute(String name) {
		if(   attributes.containsKey(name)){
			attributes.remove(name);
		}
		Date d=new Date();
		this.lastAccessedTime=d.getTime();
	}

	@Override
	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
		Date d=new Date();
		this.lastAccessedTime=d.getTime();
	}

	@Override
	public String toString() {
		return "TomcatHttpSession [attributes=" + attributes + ", id=" + id
				+ "]";
	}
	
	

}
