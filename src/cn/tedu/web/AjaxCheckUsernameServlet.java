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
		//ҵ��ӿ�������ҵ��ʵ����ʵ����
		UserService service = new UserServiceImpl();
		//���ö�Ӧ��ҵ�񷽷�
		User user = service.findByUsername(username);
		//���ؽ��
		response.getWriter().write((user!=null)+"");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
