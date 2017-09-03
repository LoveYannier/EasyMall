package cn.tedu.service;

import cn.tedu.domain.User;
import cn.tedu.exception.MsgException;

public interface UserService  extends Service{
	/**
	 * �û�ע��
	 * @param user����װ���û���Ϣ��ʵ�������
	 */
	void regist(User user) throws MsgException;
	/**
	 * �û���¼
	 * @param username���û���
	 * @param password������
	 * @return ��Ӧ���û���Ϣ���������򷵻�null
	 */
	User login(String username, String password);
	/**
	 * ����û����Ƿ����
	 * @param username���û���
	 * @return ���ڷ��ظö�����Ϣ���������ⷵ��null
	 */
	User findByUsername(String username);

}
