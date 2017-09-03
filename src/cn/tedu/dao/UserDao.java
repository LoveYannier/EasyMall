package cn.tedu.dao;

import cn.tedu.domain.User;

public interface UserDao extends Dao{
	/**
	 * �����û�����ѯ���û�����Ϣ
	 * @param username:�û���
	 * @return ���û�����Ӧ�û�����Ϣ�����ڷ��أ���֮����null
	 */
	User findUserByUname(String username);
	/**
	 * ����û�
	 * @param user����װ���û���Ϣ��ʵ�������
	 */
	void addUser(User user);
	/**
	 * �����û����������ѯ���û�����Ϣ
	 * @param username�û���
	 * @param password����
	 * @return ��Ӧ���û���Ϣ����null
	 */
	User findUserByUnamePwd(String username, String password);

}
