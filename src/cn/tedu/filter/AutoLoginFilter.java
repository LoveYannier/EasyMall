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
		//1��ֻ��δ��¼���û�����ִ���Զ���¼
		if(req.getSession(false)==null||
				req.getSession().getAttribute("user")==null){
			//2��ֻ�ô����Զ���¼cookie���Զ���¼
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
				//3��ֻ��cookie������û�����������ȷ�˲����Զ���¼
				String decUP = URLDecoder.decode(findC.getValue(), "utf-8");
				String username = decUP.split(":")[0];
				String password = decUP.split(":")[1];
				UserService service = BasicFactory.getFactory().
						getInstance(UserService.class);
				User user = service.login(username, password);
				if(user!=null){
					//δ��¼  �����Զ���¼��cookie   cookie�е��û�����������ȷ
					req.getSession().setAttribute("user", user);
				}
			}
		}
		//�����Ƿ��Զ���¼����Ҫ������Դ
		chain.doFilter(request, response);
	}
	public void destroy() {
	}

}
