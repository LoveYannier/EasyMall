package cn.tedu.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.Product;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class AddCartServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1�����ղ���
		String pid = request.getParameter("pid");
		int buyNum = Integer.parseInt(request.getParameter("buyNum"));
		//2������service��������id��ѯ����Ʒ
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		Product prod;
		try {
			prod = service.findProdById(pid);
			//3����session��ȡcart
			Object cartObj = request.getSession().getAttribute("cart");
			Map<Product,Integer> cart = null;
			if(cartObj==null){
				cart = new HashMap<Product, Integer>();
				//����session
				request.getSession().setAttribute("cart", cart);
			}else{
				cart = (Map<Product,Integer>)cartObj;
			}
			//4���ж�prod������cart�������Ƿ����
			if(cart.containsKey(prod)){//�޸�Product����hashCode��equals����
				cart.put(prod, cart.get(prod)+buyNum);
			}else{
				cart.put(prod, buyNum);
			}
		} catch (MsgException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
			return;
		}
		//5��ҳ����ת
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
