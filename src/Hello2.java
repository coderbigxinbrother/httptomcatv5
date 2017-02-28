import java.io.PrintWriter;

import yc.javax.servlet.JspWriter;
import yc.javax.servlet.ServletRequest;
import yc.javax.servlet.ServletResponse;
import yc.javax.servlet.http.HttpServlet;
import yc.javax.servlet.http.HttpServletRequest;
import yc.javax.servlet.http.HttpServletResponse;



//使用了适配器模式
public class Hello2 extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(  request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		String uname=(String)request.getSession().getAttribute("uname");
		String age=request.getSession().getAttribute("age").toString();
		
		
		String body="<html><head><title>hello world</title></head><body>unamehaha:"+uname+"</hr>age:"+ age+"</body></html>";
		
		
		JspWriter out=(JspWriter) response.getWriter();
		out.println(   body );
		
		
	}
}
