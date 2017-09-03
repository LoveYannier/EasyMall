package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.User;
import cn.tedu.service.UserService;
import cn.tedu.service.UserServiceImpl;

public class AjaxCheckUsernameServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		//业务接口声明，业务实现类实例化
		UserService service = new UserServiceImpl();
		//调用对应的业务方法
		User user = service.findByUsername(username);
		//返回结果
		response.getWriter().write((user!=null)+"");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
