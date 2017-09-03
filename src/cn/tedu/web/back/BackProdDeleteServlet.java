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
		//1.接收商品id
		String id = request.getParameter("id");
		//2、调用service方法的删除操作
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		try{
			service.deleteProd(id);
			response.getWriter().write("删除成功！2秒之后，自动跳转，如果没有跳转，请点击<a href='"
					+request.getContextPath()+"/servlet/BackProdListServlet'>跳转</a>");
		}catch (Exception e) {
			response.getWriter().write("删除删除！2秒之后，自动跳转，如果没有跳转，请点击<a href='"
					+request.getContextPath()+"/servlet/BackProdListServlet'>跳转</a>");
		}
		response.setHeader("Refresh", "2;url="+request.getContextPath()+"/servlet/BackProdListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
