package cn.tedu.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.Product;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class UpdateCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1接收参数
		String pid = request.getParameter("pid");
		int newBuyNum = Integer.parseInt(request.getParameter("newBuyNum"));
		//2、从session获取cart
		Map<Product,Integer> cart = (Map<Product,Integer>)request.getSession().getAttribute("cart");
		//3、根据id查询商品信息
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		Product prod = null;
		try {
			prod = service.findProdById(pid);
			//4、修改数量
			cart.put(prod, newBuyNum);
		} catch (MsgException e) {
			e.printStackTrace();
			Product pd =new Product();
			pd.setId(pid);
			cart.remove(pd);//从购物车删除已经下架的商品
			request.setAttribute("msg", e.getMessage());
		}
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
