package cn.tedu.service;

import java.util.List;

import cn.tedu.dao.ProdDao;
import cn.tedu.domain.Product;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.utils.Page;

public class ProdServiceImpl implements ProdService {
	private ProdDao prodDao=BasicFactory.getFactory().getInstance(ProdDao.class);
	public void addProduct(Product prod) {
		prodDao.addProduct(prod);
	}
	public List<Product> prodList() {
		return prodDao.prodList();
	}
	public void updatePnum(String id, int newNum) {
		int row  = prodDao.updatePnum(id,newNum);
		if(row<=0){
			throw new RuntimeException();
		}
	}
	public void deleteProd(String id) {
		int row  = prodDao.deleteProd(id);
		if(row<=0){
			throw new RuntimeException();
		}
		
	}
	public Page pageList(int thispage, int rowperpage, String name,
			String category, double min, double max) {
		Page<Product> page = new Page<Product>();
		page.setThispage(thispage);
		page.setRowperpage(rowperpage);
		//设置总行数
		page.setCountrow(prodDao.getProdKeyCount(name,category,min,max));
		//计算总页数
		int tmp = page.getCountrow()%rowperpage==0?0:1;
		int countpage = page.getCountrow()/rowperpage+tmp;
		page.setCountpage(countpage);
		page.setPrepage(thispage==1?thispage:thispage-1);
		page.setNextpage(thispage==page.getCountpage()?thispage:thispage+1);
		/** 
		 * 0   （1-1）*10
		 * 10  (2-1)*10
		 * 20  (3-1)*10
		 * 30  4
		 */
		page.setList(prodDao.findProdsByKeyLimit((thispage-1)*rowperpage,
				rowperpage,name,category,min,max));
		return page;
	}
	public Product findProdById(String pid) throws MsgException {
		Product prod = prodDao.findProdById(pid);
		if(prod==null){
			throw new MsgException("该商品已经下架！");
		}
		return prod;
	}

}
