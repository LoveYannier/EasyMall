package cn.tedu.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class AjaxChangePnumServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			//1�����ղ���
			String id = request.getParameter("id");
			int newNum = Integer.parseInt(request.getParameter("newNum"));
			//2������service�������޸���Ʒ������
			ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
			service.updatePnum(id,newNum);
			//3������true
			response.getWriter().write("true");
		}catch (Exception e) {
			response.getWriter().write("false");
		}
	}

}
