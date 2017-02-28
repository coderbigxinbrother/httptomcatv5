package yc.javax.servlet.http;

import java.util.Set;

import yc.javax.servlet.ServletContext;

public interface HttpSession {
	//获取属性方法.
	public Object getAttribute(String name);
	
	/**
	 * 设置属性
	 */
	public void setAttribute( String name, Object value);

	/**
	 * 获取属性名
	 * @return
	 */
	public Set<String> getAttributeNames();
	

	/**
	 * 获取session id
	 */
	public String getId();
	
	/**
	 * 创建时间
	 */
	public long getCreationTime();
	
	/**
	 * 最后一次访问时间
	 */
	public long getLastAccessedTime();
	
	
	/**
	 * 移除属性
	 */
	public void removeAttribute(String name);





}
