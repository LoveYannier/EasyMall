package cn.tedu.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.tedu.domain.User;
import cn.tedu.utils.DaoUtils;

public class UserDaoImpl_bakQR implements UserDao {
	public User findUserByUname(String username) {
		//1����дsql���
		String sql= "select * from user where username=?";
		try {
			//��ȡ���ݿ�����
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class), username);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void addUser(User user) {
		//1����дsql���
		String sql = "insert into user(username,password,nickname,email)" +
				" values(?,?,?,?)";
		try {
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			runner.update(sql, user.getUsername(),user.getPassword(),
					user.getNickname(),user.getEmail());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	public User findUserByUnamePwd(String username, String password) {
		//1����дsql���
		String sql= "select * from user where username=? and password=?";
		try {
			//��ȡ���ݿ�����
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class), 
					username,password);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
