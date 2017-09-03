package cn.tedu.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.OrderInfo;
import cn.tedu.domain.User;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class OrderListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1���ж��û��Ƿ��¼
		Object userObj = request.getSession().getAttribute("user");
		if(userObj==null){//δ��¼
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		//2����ȡ�û�
		User user = (User)userObj;
		//3������service����
		OrderService service = BasicFactory.getFactory().
				getInstance(OrderService.class);
		List<OrderInfo> list = service.getOrderInfosByUserId(user.getId());
		//4������
		request.setAttribute("list", list);
		//5��ת����ת
		request.getRequestDispatcher("/orderList.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
