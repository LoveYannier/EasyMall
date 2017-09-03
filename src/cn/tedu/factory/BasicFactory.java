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
		//需要字符串“UserDao”
		//而传递过来的是UserDao.class  **Service.class
		//我们想获取名称：clz.getName():cn.tedu.dao.UserDao
		//getSimpleName()获取的就是"UserDao"
		//UserDao=cn.tedu.dao.UserDaoImpl
		try {
			if(Service.class.isAssignableFrom(clz)){//业务层接口.class
				//生成该Service对应动态代理对象
				String daoImplStr = PropUtils.getProp(clz.getSimpleName());
				//有类的全路径名称，使用反射创建对象
				Class daoImplClz = Class.forName(daoImplStr);
				//委托类对象
				final T t = (T)daoImplClz.newInstance();
				T proxy = (T)Proxy.newProxyInstance(t.getClass().getClassLoader(), 
						t.getClass().getInterfaces(),new InvocationHandler(){
							public Object invoke(Object proxy, Method method,
									Object[] args) throws Throwable {
								//判断该方法上是否添加@Tran
								if(method.isAnnotationPresent(Tran.class)){//该方法有@Tran
									//需要添加事务控制
									//记录日志
									//细粒度权限控制
									//控制事务
									try{	
										TransactionManager.startTran();//开启事务
										Object rtObj = method.invoke(t, args);
										TransactionManager.commitTran();//提交事务
										return rtObj;
									}catch(InvocationTargetException ite){
										TransactionManager.rollbackTran();//回滚事务
										ite.getTargetException().printStackTrace();
										//处理自定义异常
										throw ite.getTargetException();
									}catch (Exception e) {
										TransactionManager.rollbackTran();//回滚事务
										e.printStackTrace();
										throw new RuntimeException(e);
									}finally{
										//关闭数据库连接，释放资源
										TransactionManager.release();
									}
								}else{//没有@Tran,不需要处理事务
									try{
										return method.invoke(t, args);
									}catch(InvocationTargetException ite){
										ite.getTargetException().printStackTrace();
										//处理自定义异常
										throw ite.getTargetException();
									}catch (Exception e) {
										e.printStackTrace();
										throw new RuntimeException(e);
									}finally{
										//关闭数据库连接，释放资源
										TransactionManager.release();
									}
								}
							}
				});
				//返回代理类的对象
				return proxy;
			}else if(Dao.class.isAssignableFrom(clz)){//Dao层接口.class
				String daoImplStr = PropUtils.getProp(clz.getSimpleName());
				//有类的全路径名称，使用反射创建对象
				Class daoImplClz = Class.forName(daoImplStr);
				return (T)daoImplClz.newInstance();
			}else{//既不是业务层接口.class也不是Dao层接口.class
				throw new RuntimeException("别捣乱。。。只能获取Service或Dao。。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
