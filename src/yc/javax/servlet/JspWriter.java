package yc.javax.servlet;

import java.io.OutputStream;
import java.io.PrintWriter;

import yc.javax.servlet.http.Cookie;
import yc.javax.servlet.http.HttpServletRequest;
import yc.javax.servlet.http.HttpServletResponse;
import yc.javax.servlet.http.HttpSession;

/**
 * 拼接http响应头协议
 *
 */
public class JspWriter extends PrintWriter {
	private ServletResponse response;

	public JspWriter(OutputStream out,   ServletResponse response) {
		super(out);
		this.response=response;
	}

	public void println(String content) {
		long contentLength = content.getBytes().length; // 要输出的内容长度
		StringBuffer protocal = new StringBuffer(
				"HTTP/1.1 200 OK\r\nContent-Type: ").append(response.getContentType()).append(";\r\nContent-Length: "+ contentLength+"\r\n");
		
		//TODO:  其它响应头部信息
		HttpServletResponse resp=(HttpServletResponse)response;
		Cookie[] cookies=resp.cookies;
		int cookiesize=resp.i;
		
		//先看是否有session要写入
		HttpServletRequest request= (HttpServletRequest) response.getRequest();
		HttpSession session=request.getSession();
		protocal.append( "Set-Cookie: ");
		protocal.append("jsessionid="+ session.getId()+",");
		if(   cookiesize>0){
			for( int i=0;i<cookiesize;i++){
				Cookie c=cookies[i];
				protocal.append(  c.toString()   );
			}
		}
		
		
		
		
		protocal.append("\r\n\r\n");
		protocal.append(  content );
		super.println( protocal.toString());
		super.flush();
	}

}
