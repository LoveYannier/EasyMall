package cn.tedu.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class BackProdDeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.������Ʒid
		String id = request.getParameter("id");
		//2������service������ɾ������
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		try{
			service.deleteProd(id);
			response.getWriter().write("ɾ���ɹ���2��֮���Զ���ת�����û����ת������<a href='"
					+request.getContextPath()+"/servlet/BackProdListServlet'>��ת</a>");
		}catch (Exception e) {
			response.getWriter().write("ɾ��ɾ����2��֮���Զ���ת�����û����ת������<a href='"
					+request.getContextPath()+"/servlet/BackProdListServlet'>��ת</a>");
		}
		response.setHeader("Refresh", "2;url="+request.getContextPath()+"/servlet/BackProdListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
