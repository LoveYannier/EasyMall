package cn.tedu.factory;

import cn.tedu.dao.UserDao;
import cn.tedu.utils.PropUtils;

public class UserDaoFactory {
	private UserDaoFactory(){}
	private static UserDaoFactory factory=new UserDaoFactory();
	public static UserDaoFactory getFactory(){
		return factory;
	}
	public static UserDao getUserDao(){//��ȥproperties�����ļ�
		/*Properties prop = new Properties();
		prop.load(inStream);*/
		/**�����ô��ƣ�ÿ�ε��ø÷���ʱ���򶼻����¼��������ļ���Ч��̫�ͣ�
		 * �������ļ�ֻ��Ҫ����һ�μ��ɡ��������PropUtils
		 */
		//UserDao=cn.tedu.dao.UserDaoImpl
		String userDaoImplStr = PropUtils.getProp("UserDao");
		//�����ȫ·�����ƣ�ʹ�÷��䴴������
		try {
			Class userDaoImplClz = Class.forName(userDaoImplStr);
			return (UserDao)userDaoImplClz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
