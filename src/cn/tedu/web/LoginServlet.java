package cn.tedu.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.User;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;
import cn.tedu.service.UserServiceImpl;
import cn.tedu.utils.MD5Utils;

public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.解决乱码
		/*request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");*/
		//2.获取请求参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remname = request.getParameter("remname");
		
		if("true".equals(remname)){
			//>>记住用户名  
			Cookie remnameC = new Cookie("username", URLEncoder.encode(username, "utf-8"));
			remnameC.setPath(request.getContextPath());
			remnameC.setMaxAge(60*60*24*30);
			response.addCookie(remnameC);
		}else{
			Cookie remnameC = new Cookie("username", "");
			remnameC.setPath(request.getContextPath());
			remnameC.setMaxAge(0);
			response.addCookie(remnameC);
		}
		try{
			UserService service =BasicFactory.getFactory().getInstance(UserService.class);
			password = MD5Utils.md5(password);
			User user = service.login(username,password);
			//如果不存在
			if(user==null){
				throw new MsgException("用户名或密码错误！");
			}
			//存在，登录：将该用户的信息保存到session
			request.getSession().setAttribute("user", user);
			//处理30天自动登录
			if("true".equals(request.getParameter("autologin"))){
				Cookie autologinC = new Cookie("autologin", URLEncoder.encode(username+":"+password, "utf-8"));
				autologinC.setPath(request.getContextPath());
				autologinC.setMaxAge(60*60*24*30);
				response.addCookie(autologinC);
			}
			//提示登录成功，回到主页面
			response.getWriter().write("恭喜登录成功, 3秒之后跳转到首页...如果没有跳转, " +
					"可以点击下面的链接:<br/><a href="+request.getContextPath()+"'/index.jsp'>http://www.easymall.com</a>");
			response.setHeader("Refresh", "3;url="+ request.getContextPath() +"/index.jsp");
		}catch (MsgException me) {
			request.setAttribute("msg", me.getMessage());
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
