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
		//0.�����������
		/*//>>�����������
		request.setCharacterEncoding("UTF-8");
		//>>��Ӧ��������
		response.setContentType("text/html;charset=utf-8");*/
		User user = new User();
		try {
			//���ձ����ݲ���װuser����
			BeanUtils.populate(user, request.getParameterMap());
			//2.����У��
			user.check();
			//3.��֤���Ƿ���ȷ��У��
			//4������UserService
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
		//6.����ע��ɹ���ʾ, ��ת����ҳ
		response.getWriter().write("��ϲע��ɹ�, 3��֮����ת����ҳ...���û����ת, " +
				"���Ե�����������:<br/><a href="+request.getContextPath()+"'/index.jsp'>http://www.easymall.com</a>");
		response.setHeader("Refresh", "3;url="+ request.getContextPath() +"/index.jsp");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
