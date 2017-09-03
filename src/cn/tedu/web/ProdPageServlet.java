package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;
import cn.tedu.utils.Page;

public class ProdPageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1�����ղ���:
		//1.1��ǰ�ڼ�ҳ��ÿҳ��ʾ������
		int thispage = Integer.parseInt(request.getParameter("thispage"));
		int rowperpage = Integer.parseInt(request.getParameter("rowperpage"));
		//1.2���ղ�ѯ����
		String nameStr = request.getParameter("name");
		String categoryStr = request.getParameter("category");
		String minStr = request.getParameter("minprice");
		String maxStr = request.getParameter("maxprice");
		String name="";
		String category = "";
		if(nameStr!=null&&!"".equals(nameStr)){
			name = nameStr;
		}
		if(categoryStr!=null&&!"".equals(categoryStr)){
			category = categoryStr;
		}
		double min = -1;
		double max = Double.MAX_VALUE;
		if(minStr!=null&&!"".equals(minStr)){
			min = Double.parseDouble(minStr);
		}
		if(maxStr!=null&&!"".equals(maxStr)){
			max = Double.parseDouble(maxStr);
		}
		//2.����service����
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		//3����Ʋ�ѯ��ǰҳ��Ϣ�ķ���
		Page page = service.pageList(thispage,rowperpage,name,category,min,max);
		//4����ҳ�洫����Ϣ
		request.setAttribute("page", page);
		request.setAttribute("name", name);
		request.setAttribute("category", category);
		if(min!=-1){
			request.setAttribute("minprice", min);
		}
		if(max!=Double.MAX_VALUE){
			request.setAttribute("maxprice", max);
		}
		//5����ת����ҳ���б�ҳ��
		request.getRequestDispatcher("/prodList.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
