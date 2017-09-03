package cn.tedu.dao;

import cn.tedu.domain.User;

public interface UserDao extends Dao{
	/**
	 * 根据用户名查询该用户的信息
	 * @param username:用户名
	 * @return 该用户名对应用户的信息，存在返回，反之返回null
	 */
	User findUserByUname(String username);
	/**
	 * 添加用户
	 * @param user：封装了用户信息的实体类对象
	 */
	void addUser(User user);
	/**
	 * 根据用户名和密码查询该用户的信息
	 * @param username用户名
	 * @param password密码
	 * @return 对应的用户信息，或null
	 */
	User findUserByUnamePwd(String username, String password);

}
