package cn.tedu.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.tedu.domain.User;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.factory.UserServiceFactory;
import cn.tedu.service.UserService;
import cn.tedu.service.UserServiceImpl;
import cn.tedu.utils.MD5Utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class RegistServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//0.解决乱码问题
		/*//>>请求参数乱码
		request.setCharacterEncoding("UTF-8");
		//>>响应正文乱码
		response.setContentType("text/html;charset=utf-8");*/
		User user = new User();
		try {
			//接收表单数据并封装user对象
			BeanUtils.populate(user, request.getParameterMap());
			//2.数据校验
			user.check();
			//3.验证码是否正确的校验
			//4、创建UserService
			UserService service = (UserService)BasicFactory.getFactory().getInstance(UserService.class);
			user.setPassword(MD5Utils.md5(user.getPassword()));
			service.regist(user);
			
		}catch(MsgException e){
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			e.printStackTrace();
		}catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException();
		}
		//6.给出注册成功提示, 跳转到首页
		response.getWriter().write("恭喜注册成功, 3秒之后跳转到首页...如果没有跳转, " +
				"可以点击下面的链接:<br/><a href="+request.getContextPath()+"'/index.jsp'>http://www.easymall.com</a>");
		response.setHeader("Refresh", "3;url="+ request.getContextPath() +"/index.jsp");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
