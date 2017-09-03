package cn.tedu.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeanListHandler<T> implements ResultSetHandler<List<T>> {
	private Class<T> clz;
	public BeanListHandler(Class<T> clz){
		this.clz = clz;
	}
	public List<T> handle(ResultSet rs) throws Exception {
		List<T> list = new ArrayList<T>();
		while(rs.next()){
			T t = clz.newInstance();
			//��������е���Ϣ��װ��bean������
			BeanInfo bi = Introspector.getBeanInfo(clz);
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				//��ȡ��������
				String name = pd.getName();//username,nickname
				//��ȡ�����Զ�Ӧ��setter����
				Method mtd = pd.getWriteMethod();
				Class type = pd.getPropertyType();
				try{//�ų�ʵ�����д��ڵ������ڶ�Ӧ�ı����Ҳ�����Ӧ����rs.getString("password")
					System.out.println(type);
					Object obj = null;
					if(type==Integer.TYPE){
						obj = rs.getInt(name);
					}else{
						obj = rs.getObject(name);
					}
				    mtd.invoke(t, obj);
				}catch (SQLException e) {
					continue;
				}
			}
			//����װ��һ����Ϣbean������ӵ�list������
			list.add(t);
		}
		//return list;
		return list.size()==0?null:list;
	}

}
