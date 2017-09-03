package cn.tedu.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import cn.tedu.domain.Product;
import cn.tedu.utils.BeanHandler;
import cn.tedu.utils.BeanListHandler;
import cn.tedu.utils.DaoUtils;
import cn.tedu.utils.ResultSetHandler;
import cn.tedu.utils.TransactionManager;

public class ProdDaoImpl implements ProdDao {
	public void addProduct(Product prod) {
		String sql = "insert into products(id,name,price,category,pnum," +
				"imgurl,description) values(?,?,?,?,?,?,?)";
		DaoUtils.update(sql, prod.getId(),prod.getName(),prod.getPrice(),
				prod.getCategory(),prod.getPnum(),prod.getImgurl(),prod.getDescription());
	}

	public List<Product> prodList() {
		String sql = "select * from products";
		return DaoUtils.query(sql, new BeanListHandler<Product>(Product.class));
	}
	/*public Product findProdById(String id) {
		String sql = "select * from products where id=?";
		return DaoUtils.query(sql, new BeanHandler<Product>(Product.class),id);
	}*/
	public int updatePnum(String id, int newNum) {
		String sql = "update products set pnum=? where id =?";
		return DaoUtils.update(sql, newNum,id);
	}

	public int deleteProd(String id) {
		String sql = "delete from products where id = ?";
		return DaoUtils.update(sql, id);
	}
	public int getProdKeyCount(String name, String category, double min,
			double max) {
		String sql = "select count(*) from products where name like ? " +
				"and category like ? and price>=? and price<=?";
		return DaoUtils.query(sql, new ResultSetHandler<Integer>() {
			public Integer handle(ResultSet rs) throws Exception {
				rs.next();
				return rs.getInt(1);
			}
			
		}, "%"+name+"%","%"+category+"%",min,max);
	}

	public List<Product> findProdsByKeyLimit(int start, int rowperpage,
			String name, String category, double min, double max) {
		String sql = "select * from products where name like ? and " +
				"category like ? and price>=? and price<=? limit ?,?";
		return DaoUtils.query(sql, new BeanListHandler<Product>(Product.class), 
				"%"+name+"%","%"+category+"%",min,max,start,rowperpage);
	}

	public Product findProdById(String pid) {
		String sql= "select * from products where id = ?";
		return DaoUtils.query(sql, new BeanHandler<Product>(Product.class), pid);
	}
}
