package cn.tedu.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodeFilter implements Filter{
	private String encode;
	public void init(FilterConfig filterConfig) throws ServletException {
		encode = filterConfig.getInitParameter("encode");
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//�����Ӧ������
		response.setContentType("text/html;charset="+encode);
		//������������ (ֻ�ܽ����post�ύ��ʽ������)
		//request.setCharacterEncoding(encode);
		//������Դ
		chain.doFilter(new MyRequest((HttpServletRequest)request), response);
		//chain.doFilter(new MyRe((HttpServletRequest)request), response);
	}
	public void destroy() {
	}
	/**ʹ��װ�������ģʽ�����������
	 * ͨ��װ�����ģʽ���޸�request�����кͽ�����������йص�������������������������
	 * �ֱ���Ӵ�������Ĵ��롣
	 * ��Ȼ��request�ڲ�������������Ȼ���ڣ�����֮��ֻҪͨ��������������ȡ������������Զ��������봦��
	 */
	private class MyRe{
		public MyRe(HttpServletRequest request) {
			
		}
	}
	private class MyRequest extends HttpServletRequestWrapper{
		private HttpServletRequest request;
		public MyRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		public String getParameter(String name){
			try {
				if("POST".equals(request.getMethod())){
					request.setCharacterEncoding(encode);
					return request.getParameter(name);
				}else if("GET".equals(request.getMethod())){
					String str = request.getParameter(name);
					str = str==null?null:new String(str.getBytes("ISO8859-1"),encode);
					return str;
				}else{//�����ύ��ʽ����֪�������������ȥ����
					return request.getParameter(name);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		public String[] getParameterValues(String name) {
			try {
				if("POST".equals(request.getMethod())){
					request.setCharacterEncoding(encode);
					return request.getParameterValues(name);
				}else if("GET".equals(request.getMethod())){
					String vals[] = request.getParameterValues(name);
					if(vals!=null){
						for (int i = 0; i < vals.length; i++) {
							vals[i] = new String(vals[i].getBytes("ISO8859-1"),encode);
						}
					}
					return vals;
				}else{//�����ύ��ʽ����֪�������������ȥ����
					return request.getParameterValues(name);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		public Map getParameterMap() {
			try {
				if("POST".equals(request.getMethod())){
					request.setCharacterEncoding(encode);
					return request.getParameterMap();
				}else if("GET".equals(request.getMethod())){
					Map<String,String[]> map = request.getParameterMap(); 
					//��������
					for (Map.Entry<String,String[]> entry : map.entrySet()) {
						String vals[] = entry.getValue();
						//ѭ������
						for (int i = 0; i < vals.length; i++) {
							vals[i] = new String(vals[i].getBytes("ISO8859-1"),encode);
						}
					}
					return map;
				}else{//�����ύ��ʽ����֪�������������ȥ����
					return request.getParameterMap();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
	}

}
