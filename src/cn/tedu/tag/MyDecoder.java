package cn.tedu.tag;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MyDecoder extends SimpleTagSupport {
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public void doTag() throws JspException, IOException {
		//1、获取PageContext对象
		PageContext pc = (PageContext)getJspContext();
		//2、获取cookie
		Cookie[] cs = ((HttpServletRequest)(pc.getRequest())).getCookies();
		Cookie remnameC = null;
		if(cs != null){
			for(Cookie c:cs){
				if(name.equals(c.getName())){
					remnameC = c;
					break;
				}
			}
		}
		String username = "";
		if(remnameC != null){
			username = URLDecoder.decode(remnameC.getValue(), "utf-8");
		}
		getJspContext().getOut().write(username);
	}
}
