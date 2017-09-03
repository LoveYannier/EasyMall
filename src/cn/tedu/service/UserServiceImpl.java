package cn.tedu.service;

import cn.tedu.dao.UserDao;
import cn.tedu.dao.UserDaoImpl;
import cn.tedu.domain.User;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.factory.UserDaoFactory;

public class UserServiceImpl implements UserService {
	private UserDao userDao = BasicFactory.getFactory().getInstance(UserDao.class);
	public void regist(User user) throws MsgException {
		//1�������û��Ƿ����ע��
		if(userDao.findUserByUname(user.getUsername())!=null){
			throw new MsgException("�û����Ѿ����ڣ�");
		}
		//2����������ڣ���ִ����Ӳ���
		userDao.addUser(user);
	}
	public User login(String username, String password) {
		return userDao.findUserByUnamePwd(username,password);
	}
	public User findByUsername(String username) {
		return userDao.findUserByUname(username);
	}

}
