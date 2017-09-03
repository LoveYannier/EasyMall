package cn.tedu.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.Order;
import cn.tedu.domain.OrderItem;
import cn.tedu.domain.Product;
import cn.tedu.domain.User;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class OrderAddServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1���ж��û��Ƿ��¼
		Object userObj = request.getSession().getAttribute("user");
		if(userObj==null){//�û�δ��¼
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		//�û��Ѿ���¼
		User user = (User)userObj;
		//2�������ﳵ����Ϣ��װһ��Order��N��OrderItem����
		//2.1��װOrder����
		Order order= new Order();
		order.setId(UUID.randomUUID().toString());
		order.setUser_id(user.getId());
		order.setReceiverinfo(request.getParameter("receiverinfo"));
		order.setPaystate(0);
		order.setOrdertime(new Timestamp(new Date().getTime()));
		//2.2��session�л�ȡ���ﳵ
		Map<Product,Integer> cart = (Map<Product,Integer>)request.
				getSession().getAttribute("cart");
		//2.3�������ﳵ�����㶩���ܽ�����OrderItem���󲢱��漯����
		double money =0;
		List<OrderItem> items = new ArrayList<OrderItem>();
		for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
			//2.3.1�����ܽ��
			money +=entry.getKey().getPrice()*entry.getValue();
			//2.3.2��ȡ������ת����OrderItem���󣨶����
			OrderItem item= new OrderItem();
			item.setOrder_id(order.getId());
			item.setProduct_id(entry.getKey().getId());
			item.setBuynum(entry.getValue());
			//2.3.3������
			items.add(item);
		}
		//3���������ܽ���װorder
		order.setMoney(money);
		//4������service����
		OrderService service = BasicFactory.getFactory().
				getInstance(OrderService.class);
		try{
			service.addOrder(order,items);
			//5����չ��ﳵ
			cart.clear();
			//6����ʾ��ӳɹ�
			response.getWriter().write("������ӳɹ���");
			response.setHeader("Refresh", "2;url="+request.getContextPath()+"/servlet/OrderListServlet");
		}catch (MsgException e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		}
		
		
	}

}
