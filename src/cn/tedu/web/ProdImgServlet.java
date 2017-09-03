package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class ProdImgServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*//1.��ȡ��Ʒ��id
		String id = request.getParameter("id");
		//2������service��ѯ��id��Ӧ����Ʒ��Ϣ
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		Product prod = service.findProdById(id);*/
		//��ȡ����·��
		//�ж��Ƿ���www.easymall.com
		String imgurl=request.getParameter("imgurl");
		//3����ȡ��Ʒ��Ӧ��ͼƬ��·����ת����ͼƬ��ʹͼƬ�Զ������������
		request.getRequestDispatcher(imgurl).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
