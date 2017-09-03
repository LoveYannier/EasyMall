package cn.tedu.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cn.tedu.domain.User;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;

public class AutoLoginFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		//1、只用未登录的用户，才执行自动登录
		if(req.getSession(false)==null||
				req.getSession().getAttribute("user")==null){
			//2、只用带了自动登录cookie才自动登录
			Cookie[] cs = req.getCookies();
			Cookie findC = null;
			if(cs!=null){
				for (Cookie cookie : cs) {
					if("autologin".equals(cookie.getName())){
						findC = cookie;
						break;
					}
				}
			}
			if(findC!=null){
				//3、只用cookie保存的用户名和密码正确了才能自动登录
				String decUP = URLDecoder.decode(findC.getValue(), "utf-8");
				String username = decUP.split(":")[0];
				String password = decUP.split(":")[1];
				UserService service = BasicFactory.getFactory().
						getInstance(UserService.class);
				User user = service.login(username, password);
				if(user!=null){
					//未登录  带了自动登录的cookie   cookie中的用户名和密码正确
					req.getSession().setAttribute("user", user);
				}
			}
		}
		//无论是否自动登录，都要放行资源
		chain.doFilter(request, response);
	}
	public void destroy() {
	}

}
