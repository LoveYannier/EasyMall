package cn.tedu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tedu.dao.OrderDao;
import cn.tedu.dao.ProdDao;
import cn.tedu.domain.Order;
import cn.tedu.domain.OrderInfo;
import cn.tedu.domain.OrderItem;
import cn.tedu.domain.Product;
import cn.tedu.domain.SaleInfo;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;

public class OrderServiceImpl implements OrderService {
	private ProdDao prodDao = BasicFactory.getFactory().getInstance(ProdDao.class);
	private OrderDao orderDao = BasicFactory.getFactory().getInstance(OrderDao.class);
	public void addOrder(Order order, List<OrderItem> items)
			throws MsgException {
		try{
			//3.添加订单
			orderDao.addOrder(order);
			//4、循环添加订单项
			for (OrderItem item : items) {
				//4.1根据商品id查询该商品信息（用来比较库存是否充足）
				Product prod = prodDao.findProdById(item.getProduct_id());
				if(prod.getPnum()>=item.getBuynum()){//库存充足
					//从库存中减去本次购买的数量
					prodDao.updatePnum(prod.getId(),prod.getPnum()-
							item.getBuynum());
					//将订单项添加到数据库中
					orderDao.addOrderItem(item);
				}else{//库存不足
					throw new MsgException("商品库存不足！商品id:"+prod.getId()
							+",商品名："+prod.getName());
				}
			}
		}catch (MsgException me) {
			throw me;
		}catch(Exception e){
			e.printStackTrace();
	    }
	}
	public List<OrderInfo> getOrderInfosByUserId(int user_id) {
		List<OrderInfo> returnList = new ArrayList<OrderInfo>();
		//1、获取当前用户的所有order信息(查询orders表)
		List<Order> orderList = orderDao.findOrdersByUserId(user_id);
		//2、遍历orderList
		if(orderList!=null){
			for (Order order : orderList) {
				//--创建OrderInfo对象
				OrderInfo orderInfo = new OrderInfo();
				//将当前订单信息保存到orderInfo中
				orderInfo.setOrder(order);
				Map<Product,Integer> map = new HashMap<Product, Integer>();
				//查询该订单对应的所有订单项信息
				List<OrderItem> itemList = orderDao.findOrderItemsByOrderId(order.getId());
				if(itemList!=null){
					for(OrderItem item:itemList){
						Product prod = prodDao.findProdById(item.getProduct_id());
						map.put(prod, item.getBuynum());
					}
				}
				//将map保存到orderInfo
				orderInfo.setMap(map);
				//将封装信息后orderInfo保存到returnList中
				returnList.add(orderInfo);
			}
		}
		return returnList;
	}
	public void delOrder(String oid) throws MsgException {
		//1业务规则：只有未支付订单可以删除
		Order order = orderDao.findOrderById(oid);
		if(order.getPaystate()!=0){
			throw new MsgException("只有未支付的订单可以删除！");
		}
		//2、将购买的商品数量加回去
		List<OrderItem> itemList = orderDao.findOrderItemsByOrderId(oid);
		if(itemList!=null){
			for(OrderItem item:itemList){
				Product prod = prodDao.findProdById(item.getProduct_id());
				prodDao.updatePnum(item.getProduct_id(), item.getBuynum()+prod.getPnum());
			}
			//3、删除该订单对应的订单项
			orderDao.delOrderItemsByOrderId(oid);
		}
		//4、删除订单
		orderDao.delOrderByOrderId(oid);
		
	}
	public double getMoneyByOrderId(String oid) {
		return orderDao.findOrderById(oid).getMoney();
	}
	public void updatePayState(String order_id, int paystate) {
		int state = orderDao.findPayStateByOrderId(order_id);
		if(state==0){
			orderDao.updatePayState(order_id,paystate);
		}
	}
	public List<SaleInfo> findAllSales() {
		return orderDao.findAllSales();
	}

}
