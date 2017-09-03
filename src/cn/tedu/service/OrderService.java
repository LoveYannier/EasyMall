package cn.tedu.service;

import java.util.List;

import cn.tedu.annotation.Tran;
import cn.tedu.domain.Order;
import cn.tedu.domain.OrderInfo;
import cn.tedu.domain.OrderItem;
import cn.tedu.domain.SaleInfo;
import cn.tedu.exception.MsgException;

public interface OrderService extends Service{
	/**
	 * ��Ӷ����ķ���
	 * @param order����װ������Ϣ��Order����
	 * @param items����װ�������List<OrderItem>
	 */
	@Tran
	void addOrder(Order order, List<OrderItem> items) throws MsgException;
	/**
	 * �����û�id��ѯ��ǰ�û����еĶ�����Ϣ
	 * @param user_id���û�id
	 * @return  ���û����еĶ����Ϣ
	 */
	List<OrderInfo> getOrderInfosByUserId(int user_id);
	/**
	 * ���ݶ���idɾ���ö�����Ӧ����Ϣ�Լ��ö����������Ʒ�����ӻ�ȥ
	 * @param oid����id
	 */
	@Tran
	void delOrder(String oid) throws MsgException;
	/**
	 * ���ݶ���id��ѯ�������
	 * @param oid
	 * @return �ö������
	 */
	double getMoneyByOrderId(String oid);
	/**
	 * �޸Ķ�����֧��״̬
	 * @param order_id������id
	 * @param paystate������״̬  0����ʾδ���1��ʾ�Ѹ���  
	 */
	@Tran
	void updatePayState(String order_id, int paystate);
	/**
	 * ��ѯ���۰�
	 * @return ȫ������Ʒ������Ϣ
	 */
	List<SaleInfo> findAllSales();
	

}
