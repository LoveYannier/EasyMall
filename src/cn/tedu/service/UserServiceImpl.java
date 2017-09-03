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
		//1、检查该用户是否可以注册
		if(userDao.findUserByUname(user.getUsername())!=null){
			throw new MsgException("用户名已经存在！");
		}
		//2、如果不存在，则执行添加操作
		userDao.addUser(user);
	}
	public User login(String username, String password) {
		return userDao.findUserByUnamePwd(username,password);
	}
	public User findByUsername(String username) {
		return userDao.findUserByUname(username);
	}

}
