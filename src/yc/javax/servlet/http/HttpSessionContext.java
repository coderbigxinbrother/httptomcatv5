package yc.javax.servlet.http;

import java.util.Set;

public interface HttpSessionContext {
	
	public Set<String> getIds();
	
	
	public HttpSession getSession(String sessionId);
	
	public void setSession(String sessionId, HttpSession session);
	
	
}
