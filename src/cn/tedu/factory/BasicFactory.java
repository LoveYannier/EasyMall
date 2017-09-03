package cn.tedu.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.tedu.annotation.Tran;
import cn.tedu.dao.Dao;
import cn.tedu.service.Service;
import cn.tedu.utils.PropUtils;
import cn.tedu.utils.TransactionManager;

public class BasicFactory {
	private static BasicFactory factory = new BasicFactory(); 
	private BasicFactory(){}
	public static BasicFactory getFactory(){
		return factory;
	}
	@SuppressWarnings("unchecked")
	public <T> T getInstance(Class<T> clz){
		//��Ҫ�ַ�����UserDao��
		//�����ݹ�������UserDao.class  **Service.class
		//�������ȡ���ƣ�clz.getName():cn.tedu.dao.UserDao
		//getSimpleName()��ȡ�ľ���"UserDao"
		//UserDao=cn.tedu.dao.UserDaoImpl
		try {
			if(Service.class.isAssignableFrom(clz)){//ҵ���ӿ�.class
				//���ɸ�Service��Ӧ��̬�������
				String daoImplStr = PropUtils.getProp(clz.getSimpleName());
				//�����ȫ·�����ƣ�ʹ�÷��䴴������
				Class daoImplClz = Class.forName(daoImplStr);
				//ί�������
				final T t = (T)daoImplClz.newInstance();
				T proxy = (T)Proxy.newProxyInstance(t.getClass().getClassLoader(), 
						t.getClass().getInterfaces(),new InvocationHandler(){
							public Object invoke(Object proxy, Method method,
									Object[] args) throws Throwable {
								//�жϸ÷������Ƿ����@Tran
								if(method.isAnnotationPresent(Tran.class)){//�÷�����@Tran
									//��Ҫ����������
									//��¼��־
									//ϸ����Ȩ�޿���
									//��������
									try{	
										TransactionManager.startTran();//��������
										Object rtObj = method.invoke(t, args);
										TransactionManager.commitTran();//�ύ����
										return rtObj;
									}catch(InvocationTargetException ite){
										TransactionManager.rollbackTran();//�ع�����
										ite.getTargetException().printStackTrace();
										//�����Զ����쳣
										throw ite.getTargetException();
									}catch (Exception e) {
										TransactionManager.rollbackTran();//�ع�����
										e.printStackTrace();
										throw new RuntimeException(e);
									}finally{
										//�ر����ݿ����ӣ��ͷ���Դ
										TransactionManager.release();
									}
								}else{//û��@Tran,����Ҫ��������
									try{
										return method.invoke(t, args);
									}catch(InvocationTargetException ite){
										ite.getTargetException().printStackTrace();
										//�����Զ����쳣
										throw ite.getTargetException();
									}catch (Exception e) {
										e.printStackTrace();
										throw new RuntimeException(e);
									}finally{
										//�ر����ݿ����ӣ��ͷ���Դ
										TransactionManager.release();
									}
								}
							}
				});
				//���ش�����Ķ���
				return proxy;
			}else if(Dao.class.isAssignableFrom(clz)){//Dao��ӿ�.class
				String daoImplStr = PropUtils.getProp(clz.getSimpleName());
				//�����ȫ·�����ƣ�ʹ�÷��䴴������
				Class daoImplClz = Class.forName(daoImplStr);
				return (T)daoImplClz.newInstance();
			}else{//�Ȳ���ҵ���ӿ�.classҲ����Dao��ӿ�.class
				throw new RuntimeException("���ҡ�����ֻ�ܻ�ȡService��Dao����");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
