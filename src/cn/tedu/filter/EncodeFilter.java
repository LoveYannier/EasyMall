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
		//解决响应的乱码
		response.setContentType("text/html;charset="+encode);
		//解决请求的乱码 (只能解决掉post提交方式的乱码)
		//request.setCharacterEncoding(encode);
		//放行资源
		chain.doFilter(new MyRequest((HttpServletRequest)request), response);
		//chain.doFilter(new MyRe((HttpServletRequest)request), response);
	}
	public void destroy() {
	}
	/**使用装饰者设计模式解决乱码问题
	 * 通过装饰设计模式，修改request对象中和接收请求参数有关的三个方法，在这三个方法中
	 * 分别添加处理乱码的代码。
	 * 虽然在request内部请求中乱码仍然存在，但是之后只要通过这三个方法获取请求参数，会自定进行乱码处理。
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
				}else{//其他提交方式，不知道神马情况，不去处理
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
				}else{//其他提交方式，不知道神马情况，不去处理
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
					//遍历处理
					for (Map.Entry<String,String[]> entry : map.entrySet()) {
						String vals[] = entry.getValue();
						//循环处理
						for (int i = 0; i < vals.length; i++) {
							vals[i] = new String(vals[i].getBytes("ISO8859-1"),encode);
						}
					}
					return map;
				}else{//其他提交方式，不知道神马情况，不去处理
					return request.getParameterMap();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
	}

}
