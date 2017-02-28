import java.io.PrintWriter;

import yc.javax.servlet.JspWriter;
import yc.javax.servlet.Servlet;
import yc.javax.servlet.ServletRequest;
import yc.javax.servlet.ServletResponse;
import yc.javax.servlet.http.Cookie;
import yc.javax.servlet.http.HttpServlet;
import yc.javax.servlet.http.HttpServletRequest;
import yc.javax.servlet.http.HttpServletResponse;
import yc.javax.servlet.http.HttpSession;


public class Hello extends HttpServlet {
	public Hello(){
		System.out.println(  "constructor");
	}
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response) {
		doPost(  request, response);
	}
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		
	//	response.addCookie(   new Cookie( "name","zy"));
	//	response.addCookie(  new Cookie("age","20"));
		
		
	
		
	
		//String uname=request.getParameter("uname");
		//int age=Integer.parseInt( request.getParameter("age") );
		
		
		HttpSession session=request.getSession(); //在tomcat中先会取  cookie中是否有  sessionid, 没有创建 session   , 有sessionid, 则取出sessionid,再到SessionContext中取是否有这个session， 有呢则取出，没有呢则创建 . 
	    
		session.setAttribute("uname","wangxin");
		session.setAttribute("age", 24);
		//session.setAttribute("uname",uname);
		//session.setAttribute("age", age);
		
		//String body="<html><head><title>hello world</title></head><body>uname:"+uname+"</hr>age:"+ age+"</body></html>";
		String body="<html><head><title>hello world</title></head><body>uname:"+"wangxin"+"</hr>age:"+ "24"+"</body></html>";
		
		//与标准tomcat不同的地方： 可以自动封装好协议的头部.
		JspWriter out=(JspWriter) response.getWriter();
		out.println(   body );
	}
	@Override
	public void init() {
		System.out.println(  "init()");
	}
	
	
	


	
}
