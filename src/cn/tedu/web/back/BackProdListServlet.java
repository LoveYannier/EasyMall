package cn.tedu.web.back;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class BackProdListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1������ProdService��ѯ������Ʒ����Ϣ
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		List<Product> list = service.prodList();
		//2������ѯ������浽request��
		request.setAttribute("list",list);
		//3����ת
		request.getRequestDispatcher("/back/manageProd.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
