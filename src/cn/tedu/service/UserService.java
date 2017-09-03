package cn.tedu.service;

import cn.tedu.domain.User;
import cn.tedu.exception.MsgException;

public interface UserService  extends Service{
	/**
	 * 用户注册
	 * @param user：封装了用户信息的实体类对象
	 */
	void regist(User user) throws MsgException;
	/**
	 * 用户登录
	 * @param username：用户名
	 * @param password：密码
	 * @return 对应的用户信息，不存在则返回null
	 */
	User login(String username, String password);
	/**
	 * 检查用户名是否存在
	 * @param username：用户名
	 * @return 存在返回该对象信息，不存在这返回null
	 */
	User findByUsername(String username);

}
