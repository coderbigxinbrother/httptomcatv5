package com.yc.tomcat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import yc.javax.servlet.http.HttpSession;
import yc.javax.servlet.http.HttpSessionContext;

public class TomcatHttpSessionContext implements HttpSessionContext {
	private static Map<String, HttpSession> map = new HashMap<String, HttpSession>();
	private static HttpSessionContext httpSessionContext;

	@Override
	public Set<String> getIds() {
		return map.keySet();
	}

	@Override
	public HttpSession getSession(String sessionId) {
		if (map.containsKey(sessionId)) {
			return map.get(sessionId);
		} else {
			return null;
		}
	}

	public void setSession(String sessionId, HttpSession session) {
		map.put(sessionId, session);
	}

	//单例
	private TomcatHttpSessionContext() {
	}

	public synchronized static HttpSessionContext getInstance() {
		if (httpSessionContext == null) {
			httpSessionContext = new TomcatHttpSessionContext();
		}
		System.out.println("当前  session map中的值班为:");
		for(   Map.Entry<String, HttpSession> entry: map.entrySet() ){
			System.out.println(   entry.getKey()+"      "+  entry.getValue() );
		}
		System.out.println("=============================");
		
		return httpSessionContext;
	}

}
