package cn.tedu.web.back;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.tedu.domain.Product;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class BackProdAddServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//一准备工作
		//保存表单项信息    <name属性的值,value属性的值>
		Map<String,String> map = new HashMap<String,String>();
		//设置商品id
		map.put("id", UUID.randomUUID().toString());
		try {
			//二、获取表单中的信息以及文件上传
			//1、创建DiskFileItemFactory的对象，并配置缓存区大小和临时文件的保存路径
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//1.1设置缓存区的大小
			factory.setSizeThreshold(1024*20);//20kb
			//1.2设置临时文件的保存路径
			factory.setRepository(new File(getServletContext().
					getRealPath("/WEB-INF/tmp/")));
			//2.创建ServletFileUpload对象,处理文件名乱码，上传大小的限制
			ServletFileUpload upload = new ServletFileUpload(factory);
			//2.1设置处理文件名乱码
			upload.setHeaderEncoding("utf-8");
			//2.2设置单个文件大小的最大值
			upload.setFileSizeMax(1024*1024);//1MB
			//2.3设置文件总大小的最大值
			upload.setSizeMax(1024*1024);//1MB
			//3、获取所有的输入项
			List<FileItem> list = upload.parseRequest(request);
			//3.1循环处理
			for (FileItem item : list) {
				//3.1.1判断当前item对应的表单项是普通表单项还是文件上传表单项
				if(item.isFormField()){//普通表单项
					String name = item.getFieldName();//name属性的值
					String value = item.getString("utf-8");//“value属性”的值
					map.put(name, value);
				}else{//文件上传表单项
					//4.处理文件上传
					//4.1、获取文件名
					String fileName = item.getName();
					//4.2ie部分版本的浏览器获取到的文件名中包含客户端的本地路径：截取文件名
					if(fileName.indexOf("\\")!=-1){
						fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					}
					//4.3避免文件名重复
					fileName = UUID.randomUUID().toString()+fileName;
					//4.4处理保存目录
					String path = "/WEB-INF/upload/";
					//--文件对应hashcode生成对应的路径
					String hash = Integer.toHexString(fileName.hashCode());
					char chs[] = hash.toCharArray();
					for (int i = 0; i < chs.length; i++) {
						path += chs[i]+"/";
					}
					//---创建目录
					new File(getServletContext().getRealPath(path)).mkdirs();
					//5.执行IO操作
					InputStream in = item.getInputStream();
					OutputStream out = new FileOutputStream(
							getServletContext().getRealPath(path+fileName));
					byte[] bts = new byte[1024];
					int len = -1;
					while((len=in.read(bts))!=-1){
						out.write(bts, 0, len);
					}
					//map.put("imgurl", path+fileName);
					map.put(item.getFieldName(), path+fileName);
					//6关闭流
					out.close();
					in.close();
					//7、清除临时文件
					item.delete();
				}
			}
			//三、将商品信息保存到数据库
			Product prod = new Product();
			BeanUtils.populate(prod, map);
			ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
			service.addProduct(prod);
			//四、提示并跳转：提示商品添加成功，跳转到后台商品查询Serlvet
			response.getWriter().write("添加商品成功！");
			response.setHeader("Refresh", "2;url="+request.getContextPath()+"/servlet/BackProdListServlet");
		}catch (FileSizeLimitExceededException e) {
			request.setAttribute("msg","文件大小超过限制！商品图片不能大于1M");	
			request.getRequestDispatcher("/back/manageAddProd.jsp");
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg",e.getMessage());	
			request.getRequestDispatcher("/back/manageAddProd.jsp");
		}
		
	}

}
