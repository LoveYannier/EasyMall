package cn.tedu.utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DaoUtils {
	private static ComboPooledDataSource source = new ComboPooledDataSource();
	private DaoUtils(){}
	public static DataSource getSource(){
		return source;
	}
	public static Connection getConn() throws SQLException{
		return source.getConnection();
	}
	/**
	 * ʵ�ֲ�ѯ
	 * @param sql��sql���
	 * @param rsh��ResultSetHandlerʵ����Ķ���ͨ��ʹ��BeanHandler��BeanListHandler
	 * @param params:��ѯ�����õ���ֵ�����������Ĳ���д����
	 * @return 
	 * @throws SQLException
	 */
	public static <T> T query(String sql, ResultSetHandler<T> rsh, 
			Object... params){
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try{
			conn = TransactionManager.getConn();
			pstat = conn.prepareStatement(sql);
			//Ϊռλ����ֵ
			for(int i=1;i<=params.length;i++){
				pstat.setObject(i, params[i-1]);
			}
			//ִ�в�ѯ
			rs = pstat.executeQuery();
			return rsh.handle(rs);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(rs, pstat, null);//�м�conn�����ڴ˹ر�
		}
		return null;
	}
	/**
	 * ʵ����ӣ��޸ģ�ɾ������
	 * @param sql��sql���
	 * @param params���������ֵ�����飨����������
	 * @return Ӱ������
	 * @throws SQLException
	 */
	public static int update(String sql, Object... params){
		Connection conn = null;
		PreparedStatement pstat = null;
		try{
			conn = TransactionManager.getConn();
			pstat = conn.prepareStatement(sql);
			//Ϊռλ����ֵ
			for(int i=1;i<=params.length;i++){
				pstat.setObject(i, params[i-1]);
			}
			//ִ�в�ѯ
			return pstat.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(null, pstat, null);//�м�conn�����ڴ˹ر�
		}
		return 0;
	}
	
	/**
	 * �ر���Դ
	 * @param rs ���������
	 * @param stat 
	 * @param conn
	 */
	public static void close(ResultSet rs,Statement stat,Connection conn){
		if(rs != null){
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				rs = null;
			}
		}
		if(stat != null){
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				stat = null;
			}
		}
		if(conn != null){
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				conn = null;
			}
		}
	}
}
