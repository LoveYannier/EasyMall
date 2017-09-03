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
		//1、接收参数:
		//1.1当前第几页和每页显示多少行
		int thispage = Integer.parseInt(request.getParameter("thispage"));
		int rowperpage = Integer.parseInt(request.getParameter("rowperpage"));
		//1.2接收查询条件
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
		//2.调用service方法
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		//3、设计查询当前页信息的方法
		Page page = service.pageList(thispage,rowperpage,name,category,min,max);
		//4、向页面传递信息
		request.setAttribute("page", page);
		request.setAttribute("name", name);
		request.setAttribute("category", category);
		if(min!=-1){
			request.setAttribute("minprice", min);
		}
		if(max!=Double.MAX_VALUE){
			request.setAttribute("maxprice", max);
		}
		//5、跳转到分页的列表页面
		request.getRequestDispatcher("/prodList.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
