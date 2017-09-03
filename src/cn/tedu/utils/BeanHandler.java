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
			//����clz��Ӧ��Ķ���
			T t = clz.newInstance();
			//��������е���Ϣ��װ��bean������
			BeanInfo bi = Introspector.getBeanInfo(clz);
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				//��ȡ��������
				String name = pd.getName();//username,nickname
				//��ȡ�����Զ�Ӧ��setter����
				Method mtd = pd.getWriteMethod();
				try{//�ų�ʵ�����д��ڵ������ڶ�Ӧ�ı����Ҳ�����Ӧ����rs.getString("password")
				    mtd.invoke(t, rs.getObject(name));
				}catch (SQLException e) {
					continue;
				}
				//user.setUsername(rs.getString("username"));
			}
			//����װ����Ϣ��bean����t����
			return t;
		}
		return null;
	}

}
