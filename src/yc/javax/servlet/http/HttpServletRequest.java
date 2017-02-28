package yc.javax.servlet.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import com.yc.tomcat.TomcatConstants;
import com.yc.tomcat.TomcatHttpSession;
import com.yc.tomcat.TomcatHttpSessionContext;
import com.yc.tomcat.TomcatServletContext;

import yc.javax.servlet.ServletRequest;

/**
 * 封装http请求: 1. 解析请求 2. 取出请求头中资源名
 */
public class HttpServletRequest implements ServletRequest {
	// 请求的资源地址 /index.html
	private String uri;
	// 输入流
	private InputStream input;
	private String method;
	private String protocalVersion;
	private Map<String, String> parameter = new Hashtable<String, String>();
	private String sessionid;

	private Map<String, String> header = new HashMap<String, String>();

	public HttpSession getSession() {
		HttpSession session = null;
		// 1. 从参数 parameter jseesionid=xxxx 中取出 sessionid
		// Set-Cookie: jsessionid=xxx, name=zy;path=/,
		// TODO: 协议部分 Set-Cookie的解析
		String value = header.get("Cookie");
		if (value != null && value.length() > 0) {
			String[] strs = value.split(",");
			for (String s : strs) {
				if (s.startsWith("jsessionid=")) {
					String[] ss = s.split("=");
					sessionid = ss[1];
					break;
				}
			}
		}
		
		session = TomcatHttpSessionContext.getInstance().getSession(sessionid);
		// 2. 没有这个sessionid
		if (session == null) {
			session = new TomcatHttpSession();
			// 3. 则创建 一个httpSession存到 TomcatHttpSessionContext中
			TomcatHttpSessionContext.getInstance().setSession(session.getId(),
					session);
			sessionid= session.getId();//TODO:xxx
		}
		// 4. 有则取出.
		// 5. 返回 session
		return session;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public String getParameter(String key) {
		return this.parameter.get(key);
	}

	/**
	 * 构造方法
	 */
	public HttpServletRequest(InputStream iis) {
		this.input = iis;
	}

	/**
	 * 解析协议: 最重要的就是uri
	 */
	public void parse() {
		// 1. 从input中读出所有的内容( http请求协议 =》 protocal)
		String protocal = null;
		// TODO: 从流中取protocal
		StringBuffer sb = new StringBuffer(1024 * 10);
		int length = -1;
		byte[] bs = new byte[1024 * 10];
		try {
			length = this.input.read(bs);
		} catch (IOException e) {
			e.printStackTrace();
			length = -1;
		}
		for (int j = 0; j < length; j++) {
			sb.append((char) bs[j]);
		}
		protocal = sb.toString();
		// 2. 从protocal中取出 uri
		parseUri(protocal);
		// 解析出 method
		parseMethod(protocal);
		// 解析出参数
		parseParameter(protocal);

		// 解析出头域中的键值对
		parseHeader(protocal);
	}

	// Content-Type: text/html;charset=utf-8;
	// Content-Length: 88
	// Cookie: jsessionid=e3920761-e3c4-4f61-9055-b35d37522fac,
	private void parseHeader(String protocal) {
		int breakindex = protocal.indexOf("\r\n\r\n");
		String pro = protocal.substring(0, breakindex);

		String[] strs = pro.split("\r\n");
		// 第一行不要，是请求行
		if (strs != null && strs.length > 1) {
			for (int i = 1; i < strs.length; i++) {
				String[] ss = strs[i].split(": ");
				header.put(ss[0], ss[1]);
			}
		}
		// Cookie: jsessionid=e3920761-e3c4-4f61-9055-b35d37522fac,
		String cookie = header.get("Cookie");
		if (cookie != null) {
			String[] strings = cookie.split(",");
			String[] sss = strings[0].split("=");
			sessionid = sss[0];
		}

	}

	// 解析参数
	// GET /servlet/Hello?name=zy&age=10 HTTP/1.1
	// POST /servlet/Hello?name=zy

	// name=zy&age=20
	// 如何解析hash值 #xxx
	private void parseParameter(String protocal) {
		String parameterString = null;
		// 取出 参数部分，存到 parameterString
		if (this.method.equals(TomcatConstants.REQUEST_METHOD_GET)) {
			// 从protocal中取出第一个空格到第二个空格之间的字符串
			String[] ss = protocal.split(" ");
			int questionindex = ss[1].indexOf("?");
			if (questionindex > 0) {
				parameterString = ss[1].substring(questionindex + 1);
			}
		} else if (this.method.equals(TomcatConstants.REQUEST_METHOD_POST)) {
			parameterString = protocal
					.substring(protocal.indexOf("\r\n\r\n") + 4);
		}
		if (parameterString != null && parameterString.length() > 0) {
			String[] keyvalues = parameterString.split("&");
			for (int i = 0; i < keyvalues.length; i++) {
				String keyvalue = keyvalues[i];
				String[] kv = keyvalue.split("=");
				this.parameter.put(kv[0], kv[1]);
			}
		}
	}

	/**
	 * 解析method
	 * 
	 * @param protocal
	 *            : GET /index.html?name=zy HTTP/1.1
	 * @return
	 */
	private void parseMethod(String protocal) {
		this.method = protocal.substring(0, protocal.indexOf(" "));
	}

	private void parseUri(String protocal) {
		if (protocal != null && protocal.length() > 0) {
			StringTokenizer st = new StringTokenizer(protocal);
			this.method = st.nextToken();
			this.uri = st.nextToken(); // index.jsp?name=zy index.jsp
			this.protocalVersion = st.nextToken();
			if (uri.indexOf("?") > 0) {
				this.uri = uri.substring(0, uri.indexOf("?"));
			}
		}
	}

	public String getRequestUri() {
		return uri;
	}

	public String getSessionid() {
		return sessionid;
	}

}
