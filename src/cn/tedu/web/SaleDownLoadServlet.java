package cn.tedu.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.domain.SaleInfo;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class SaleDownLoadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1������service������ѯ���е����۰񵥽����
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		List<SaleInfo> list = service.findAllSales();
		//2����֯csv�ļ�������
		//2.1��ʼ��ͷ
		String data = "��Ʒ���,��Ʒ����,��������\r\n";
		//2.2��������
		if(list!=null){
			for (SaleInfo saleInfo : list) {
				//data += saleInfo.getProd_id()+","+saleInfo.getProd_name()+","+saleInfo.getSale_num()+"\r\n";
				data += saleInfo.toString()+"\r\n";
			}
		}
		//3���ṩ����
		//3.1�����ļ�����
		String fileName = "EasyMall���۰�"+new Date().toLocaleString()+".csv";
		//3.2������Ӧͷ����������Ը����ķ�ʽ��
		response.setHeader("Content-Disposition", "attachment;filename="+
				URLEncoder.encode(fileName,"utf-8"));
		//3.3�����ļ�������������⣬csvֻ֧��gbk
		response.setContentType("text/html;charset=gbk");
		//3.4�����۰񵥵���������������
		response.getWriter().write(data);
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
