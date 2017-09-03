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
			//3.��Ӷ���
			orderDao.addOrder(order);
			//4��ѭ����Ӷ�����
			for (OrderItem item : items) {
				//4.1������Ʒid��ѯ����Ʒ��Ϣ�������ȽϿ���Ƿ���㣩
				Product prod = prodDao.findProdById(item.getProduct_id());
				if(prod.getPnum()>=item.getBuynum()){//������
					//�ӿ���м�ȥ���ι��������
					prodDao.updatePnum(prod.getId(),prod.getPnum()-
							item.getBuynum());
					//����������ӵ����ݿ���
					orderDao.addOrderItem(item);
				}else{//��治��
					throw new MsgException("��Ʒ��治�㣡��Ʒid:"+prod.getId()
							+",��Ʒ����"+prod.getName());
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
		//1����ȡ��ǰ�û�������order��Ϣ(��ѯorders��)
		List<Order> orderList = orderDao.findOrdersByUserId(user_id);
		//2������orderList
		if(orderList!=null){
			for (Order order : orderList) {
				//--����OrderInfo����
				OrderInfo orderInfo = new OrderInfo();
				//����ǰ������Ϣ���浽orderInfo��
				orderInfo.setOrder(order);
				Map<Product,Integer> map = new HashMap<Product, Integer>();
				//��ѯ�ö�����Ӧ�����ж�������Ϣ
				List<OrderItem> itemList = orderDao.findOrderItemsByOrderId(order.getId());
				if(itemList!=null){
					for(OrderItem item:itemList){
						Product prod = prodDao.findProdById(item.getProduct_id());
						map.put(prod, item.getBuynum());
					}
				}
				//��map���浽orderInfo
				orderInfo.setMap(map);
				//����װ��Ϣ��orderInfo���浽returnList��
				returnList.add(orderInfo);
			}
		}
		return returnList;
	}
	public void delOrder(String oid) throws MsgException {
		//1ҵ�����ֻ��δ֧����������ɾ��
		Order order = orderDao.findOrderById(oid);
		if(order.getPaystate()!=0){
			throw new MsgException("ֻ��δ֧���Ķ�������ɾ����");
		}
		//2�����������Ʒ�����ӻ�ȥ
		List<OrderItem> itemList = orderDao.findOrderItemsByOrderId(oid);
		if(itemList!=null){
			for(OrderItem item:itemList){
				Product prod = prodDao.findProdById(item.getProduct_id());
				prodDao.updatePnum(item.getProduct_id(), item.getBuynum()+prod.getPnum());
			}
			//3��ɾ���ö�����Ӧ�Ķ�����
			orderDao.delOrderItemsByOrderId(oid);
		}
		//4��ɾ������
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
