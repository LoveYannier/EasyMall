package cn.tedu.factory;

import cn.tedu.service.UserService;
import cn.tedu.utils.PropUtils;

public class UserServiceFactory {
	private UserServiceFactory(){}
	private static UserServiceFactory factory=new UserServiceFactory();
	public static UserServiceFactory getFactory(){
		return factory;
	}
	public static UserService getUserService(){//对去properties属性文件
		/*Properties prop = new Properties();
		prop.load(inStream);*/
		/**如果这么设计，每次调用该方法时，则都会重新加载属性文件，效率太低；
		 * 该属性文件只需要加载一次即可。所有设计PropUtils
		 */
		//UserDao=cn.tedu.dao.UserDaoImpl
		String userDaoImplStr = PropUtils.getProp("UserService");
		//有类的全路径名称，使用反射创建对象
		try {
			Class userDaoImplClz = Class.forName(userDaoImplStr);
			return (UserService)userDaoImplClz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
