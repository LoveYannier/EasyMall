package cn.tedu.dao;

import java.sql.Connection;
import java.util.List;

import cn.tedu.domain.Order;
import cn.tedu.domain.OrderItem;
import cn.tedu.domain.SaleInfo;
import cn.tedu.utils.BeanHandler;
import cn.tedu.utils.BeanListHandler;
import cn.tedu.utils.DaoUtils;
import cn.tedu.utils.TransactionManager;

public class OrderDaoImpl implements OrderDao {

	public void addOrder(Order order) {
		//1°¢±‡–¥sql”Ôæ‰
		String sql= "insert into orders(id,money,receiverinfo,paystate,ordertime,user_id) " +
				"values(?,?,?,?,?,?)";
		DaoUtils.update(sql, order.getId(),order.getMoney(),order.getReceiverinfo(),
				order.getPaystate(),order.getOrdertime(),order.getUser_id());
	}

	public void addOrderItem(OrderItem item) {
		String sql = "insert into orderitem(order_id,product_id,buynum) " +
				"values(?,?,?)";
		DaoUtils.update(sql, item.getOrder_id(),item.getProduct_id(),item.getBuynum());
	}

	public List<Order> findOrdersByUserId(int user_id) {
		String sql = "select * from orders where user_id=?";
		return DaoUtils.query(sql, new BeanListHandler<Order>(Order.class), user_id);
	}

	public List<OrderItem> findOrderItemsByOrderId(String order_id) {
		String sql = "select * from orderitem where order_id = ?";
		return DaoUtils.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), order_id);
	}

	public Order findOrderById(String oid) {
		String sql = "select * from orders where id=?";
		Order order = DaoUtils.query(sql, new BeanHandler<Order>(Order.class), oid);
		return order;
	}

	public void delOrderItemsByOrderId(String oid) {
		String sql = "delete from orderitem where order_id=?";
		DaoUtils.update(sql, oid);
	}

	public void delOrderByOrderId(String oid) {
		String sql = "delete from orders where id = ?";
		DaoUtils.update(sql, oid);
	}

	public int findPayStateByOrderId(String order_id) {
		String sql = "select * from orders where id =? for update";
		return DaoUtils.query(sql, new BeanHandler<Order>(Order.class), order_id).getPaystate();
	}

	public void updatePayState(String order_id, int state) {
		String sql = "update orders set paystate=? where id=?";
		DaoUtils.update(sql, state,order_id);
	}

	public List<SaleInfo> findAllSales() {
		String sql = "select pd.id prod_id,pd.name prod_name,sum(oi.buynum) sale_num "+
				"from products pd,orders od,orderitem oi "+
				"where oi.order_id = od.id "+
				    "and oi.product_id = pd.id "+
				    "and od.paystate =1 "+
				    "group by prod_id "+
				    "order by sale_num  desc";
		return DaoUtils.query(sql, new BeanListHandler<SaleInfo>(SaleInfo.class));
	}

}
