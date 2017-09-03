package cn.tedu.dao;

import java.sql.Connection;
import java.util.List;

import cn.tedu.domain.Product;

public interface ProdDao extends Dao{
	/**
	 * 商品添加的方法
	 * @param prod
	 */
	void addProduct(Product prod);
	/**
	 * 后台查询全部商品的信息
	 * @return 所有商品的集合对象
	 */
	List<Product> prodList();
	/**
	 * 修改商品数量
	 * @param id：商品的id
	 * @param newNum商品修改后的数量
	 * @return：影响的行数
	 */
	int updatePnum(String id, int newNum);
	/**
	 * 根据商品的id删除该商品
	 * @param id：商品id
	 * @return 影响的行数
	 */
	int deleteProd(String id);
	/**分页查询之：查询符合条件的商品数量
	 * @param name：商品名称
	 * @param category分类
	 * @param min 价格区间的最小值
	 * @param max价格区间的最大值
	 * @return 符合条件的商品总数量
	 */
	int getProdKeyCount(String name, String category, double min, double max);
	/**分页查询之：查询符合条件的商品集合
	 * @param start：从哪一个开始查询
	 * @param rowperpage：查询多少条
	 * @param name：商品名
	 * @param category分类
	 * @param min价格区间的最小值
	 * @param max价格区间的最大值
	 * @return 符合条件的商品集合
	 */
	List<Product> findProdsByKeyLimit(int start, int rowperpage, String name,
			String category, double min, double max);
	/**
	 * 根据商品id查询对应的商品信息
	 * @param pid商品id
	 * @return 商品信息对象
	 */
	Product findProdById(String pid);
}
