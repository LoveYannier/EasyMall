package cn.tedu.factory;

import cn.tedu.service.UserService;
import cn.tedu.utils.PropUtils;

public class UserServiceFactory {
	private UserServiceFactory(){}
	private static UserServiceFactory factory=new UserServiceFactory();
	public static UserServiceFactory getFactory(){
		return factory;
	}
	public static UserService getUserService(){//��ȥproperties�����ļ�
		/*Properties prop = new Properties();
		prop.load(inStream);*/
		/**�����ô��ƣ�ÿ�ε��ø÷���ʱ���򶼻����¼��������ļ���Ч��̫�ͣ�
		 * �������ļ�ֻ��Ҫ����һ�μ��ɡ��������PropUtils
		 */
		//UserDao=cn.tedu.dao.UserDaoImpl
		String userDaoImplStr = PropUtils.getProp("UserService");
		//�����ȫ·�����ƣ�ʹ�÷��䴴������
		try {
			Class userDaoImplClz = Class.forName(userDaoImplStr);
			return (UserService)userDaoImplClz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
