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
		//1、接收参数
		String pid = request.getParameter("pid");
		int buyNum = Integer.parseInt(request.getParameter("buyNum"));
		//2、调用service方法根据id查询该商品
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		Product prod;
		try {
			prod = service.findProdById(pid);
			//3、从session获取cart
			Object cartObj = request.getSession().getAttribute("cart");
			Map<Product,Integer> cart = null;
			if(cartObj==null){
				cart = new HashMap<Product, Integer>();
				//保存session
				request.getSession().setAttribute("cart", cart);
			}else{
				cart = (Map<Product,Integer>)cartObj;
			}
			//4、判断prod对象在cart集合中是否存在
			if(cart.containsKey(prod)){//修改Product类中hashCode和equals方法
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
		//5、页面跳转
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
