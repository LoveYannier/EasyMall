package cn.tedu.service;

import java.util.List;

import cn.tedu.domain.Product;
import cn.tedu.exception.MsgException;
import cn.tedu.utils.Page;

public interface ProdService  extends Service{
	/**
	 * 商品添加方法
	 * @param prod：封装了商品信息的Product实体类的对象
	 */
	void addProduct(Product prod);
	/**
	 * 后台查询全部商品的方法
	 * @return  全部商品的集合对象
	 */
	List<Product> prodList();
	/**
	 * 修改商品数量
	 * @param id：商品的id
	 * @param newNum：商品修改后的数量
	 */
	void updatePnum(String id, int newNum);
	/**
	 * 根据商品id删除该商品
	 * @param id
	 */
	void deleteProd(String id);
	/**
	 * 带有查询条件的分页
	 * @param thispage:当前第几页
	 * @param rowperpage：m每页显示的行数
	 * @param name：商品名称
	 * @param category：商品分类
	 * @param min：价格区间最小值
	 * @param max：价格区间的最大值
	 * @return  封装了当前页的相关信息
	 */
	Page pageList(int thispage, int rowperpage, String name, String category,
			double min, double max);
	/**
	 * 根据商品id查询该商品信息
	 * @param pid商品id
	 * @return 商品信息
	 */
	Product findProdById(String pid) throws MsgException;

}
