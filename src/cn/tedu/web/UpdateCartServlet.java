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
		//1���ղ���
		String pid = request.getParameter("pid");
		int newBuyNum = Integer.parseInt(request.getParameter("newBuyNum"));
		//2����session��ȡcart
		Map<Product,Integer> cart = (Map<Product,Integer>)request.getSession().getAttribute("cart");
		//3������id��ѯ��Ʒ��Ϣ
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		Product prod = null;
		try {
			prod = service.findProdById(pid);
			//4���޸�����
			cart.put(prod, newBuyNum);
		} catch (MsgException e) {
			e.printStackTrace();
			Product pd =new Product();
			pd.setId(pid);
			cart.remove(pd);//�ӹ��ﳵɾ���Ѿ��¼ܵ���Ʒ
			request.setAttribute("msg", e.getMessage());
		}
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
