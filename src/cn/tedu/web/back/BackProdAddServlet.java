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
		//һ׼������
		//���������Ϣ    <name���Ե�ֵ,value���Ե�ֵ>
		Map<String,String> map = new HashMap<String,String>();
		//������Ʒid
		map.put("id", UUID.randomUUID().toString());
		try {
			//������ȡ���е���Ϣ�Լ��ļ��ϴ�
			//1������DiskFileItemFactory�Ķ��󣬲����û�������С����ʱ�ļ��ı���·��
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//1.1���û������Ĵ�С
			factory.setSizeThreshold(1024*20);//20kb
			//1.2������ʱ�ļ��ı���·��
			factory.setRepository(new File(getServletContext().
					getRealPath("/WEB-INF/tmp/")));
			//2.����ServletFileUpload����,�����ļ������룬�ϴ���С������
			ServletFileUpload upload = new ServletFileUpload(factory);
			//2.1���ô����ļ�������
			upload.setHeaderEncoding("utf-8");
			//2.2���õ����ļ���С�����ֵ
			upload.setFileSizeMax(1024*1024);//1MB
			//2.3�����ļ��ܴ�С�����ֵ
			upload.setSizeMax(1024*1024);//1MB
			//3����ȡ���е�������
			List<FileItem> list = upload.parseRequest(request);
			//3.1ѭ������
			for (FileItem item : list) {
				//3.1.1�жϵ�ǰitem��Ӧ�ı�������ͨ������ļ��ϴ�����
				if(item.isFormField()){//��ͨ����
					String name = item.getFieldName();//name���Ե�ֵ
					String value = item.getString("utf-8");//��value���ԡ���ֵ
					map.put(name, value);
				}else{//�ļ��ϴ�����
					//4.�����ļ��ϴ�
					//4.1����ȡ�ļ���
					String fileName = item.getName();
					//4.2ie���ְ汾���������ȡ�����ļ����а����ͻ��˵ı���·������ȡ�ļ���
					if(fileName.indexOf("\\")!=-1){
						fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					}
					//4.3�����ļ����ظ�
					fileName = UUID.randomUUID().toString()+fileName;
					//4.4������Ŀ¼
					String path = "/WEB-INF/upload/";
					//--�ļ���Ӧhashcode���ɶ�Ӧ��·��
					String hash = Integer.toHexString(fileName.hashCode());
					char chs[] = hash.toCharArray();
					for (int i = 0; i < chs.length; i++) {
						path += chs[i]+"/";
					}
					//---����Ŀ¼
					new File(getServletContext().getRealPath(path)).mkdirs();
					//5.ִ��IO����
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
					//6�ر���
					out.close();
					in.close();
					//7�������ʱ�ļ�
					item.delete();
				}
			}
			//��������Ʒ��Ϣ���浽���ݿ�
			Product prod = new Product();
			BeanUtils.populate(prod, map);
			ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
			service.addProduct(prod);
			//�ġ���ʾ����ת����ʾ��Ʒ��ӳɹ�����ת����̨��Ʒ��ѯSerlvet
			response.getWriter().write("�����Ʒ�ɹ���");
			response.setHeader("Refresh", "2;url="+request.getContextPath()+"/servlet/BackProdListServlet");
		}catch (FileSizeLimitExceededException e) {
			request.setAttribute("msg","�ļ���С�������ƣ���ƷͼƬ���ܴ���1M");	
			request.getRequestDispatcher("/back/manageAddProd.jsp");
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg",e.getMessage());	
			request.getRequestDispatcher("/back/manageAddProd.jsp");
		}
		
	}

}
