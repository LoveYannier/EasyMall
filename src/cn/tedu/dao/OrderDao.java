package cn.tedu.dao;

import java.util.List;
import cn.tedu.domain.Order;
import cn.tedu.domain.OrderItem;
import cn.tedu.domain.SaleInfo;

public interface OrderDao extends Dao{

	public void addOrder(Order order) ;

	public void addOrderItem(OrderItem item);
	/**
	 * �����û�id��ѯ���û����еĶ�����Ϣ
	 * @param user_id�û�id
	 * @return ���û����еĶ�����Ϣ��orders��
	 */
	public List<Order> findOrdersByUserId(int user_id);
	/**
	 * ���ݶ���id��ѯ�ö��������еĶ�������Ϣ
	 * @param order_id������id
	 * @return �ö��������еĶ�������Ϣ
	 */
	public List<OrderItem> findOrderItemsByOrderId(String order_id);
	/**
	 * ���ݶ���id��ѯ������Ϣ
	 * @param oid������id
	 * @return  �ö���id��Ӧ�Ķ�����Ϣ
	 */
	public Order findOrderById(String oid);
	/**
	 * ���ݶ���idɾ���ö��������еĶ�����
	 * @param oid������id
	 */
	public void delOrderItemsByOrderId(String oid);
	/**
	 * ���ݶ���idɾ���ö�����orders���е���Ϣ
	 * @param oid������id
	 */
	public void delOrderByOrderId(String oid);
	/**
	 * ���ݶ���id��ѯ�ö�����֧��״̬���������汾��
	 * @param order_id������id
	 * @return  �ö�����֧��״̬  0��ʾδ֧����1��ʾ��֧��
	 */
	public int findPayStateByOrderId(String order_id);
	/**
	 *�޸Ķ�����֧��״̬
	 * @param order_id����id
	 * @param state֧��״̬    0��ʾδ֧����1��ʾ��֧��
	 */
	public void updatePayState(String order_id, int state);
	/**
	 * ��ѯ���۰�
	 * @return ȫ������Ʒ������Ϣ
	 */
	public List<SaleInfo> findAllSales();
}
