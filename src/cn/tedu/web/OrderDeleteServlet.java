package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class OrderDeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1、获取订单id
		String oid = request.getParameter("oid");
		//2、调用service删除方法
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		try{
			service.delOrder(oid);
			response.getWriter().write("恭喜您订单删除成功！");
		}catch (MsgException e) {
			response.getWriter().write(e.getMessage());
		}
		response.setHeader("Refresh", "2;url="+request.getContextPath()+"/servlet/OrderListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
