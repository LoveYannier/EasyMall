package cn.tedu.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeanHandler<T> implements ResultSetHandler<T> {
	//User.class
	private Class<T> clz;
	public BeanHandler(Class<T> clz){
		this.clz = clz;
	}
	public T handle(ResultSet rs) throws Exception{
		if(rs.next()){
			//创建clz对应类的对象
			T t = clz.newInstance();
			//将结果集中的信息封装到bean对象中
			BeanInfo bi = Introspector.getBeanInfo(clz);
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				//获取属性名称
				String name = pd.getName();//username,nickname
				//获取该属性对应的setter方法
				Method mtd = pd.getWriteMethod();
				try{//排除实体类中存在的属性在对应的表中找不到对应的列rs.getString("password")
				    mtd.invoke(t, rs.getObject(name));
				}catch (SQLException e) {
					continue;
				}
				//user.setUsername(rs.getString("username"));
			}
			//将封装好信息的bean对象t返回
			return t;
		}
		return null;
	}

}
