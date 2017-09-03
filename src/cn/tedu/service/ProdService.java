package cn.tedu.service;

import java.util.List;

import cn.tedu.domain.Product;
import cn.tedu.exception.MsgException;
import cn.tedu.utils.Page;

public interface ProdService  extends Service{
	/**
	 * ��Ʒ��ӷ���
	 * @param prod����װ����Ʒ��Ϣ��Productʵ����Ķ���
	 */
	void addProduct(Product prod);
	/**
	 * ��̨��ѯȫ����Ʒ�ķ���
	 * @return  ȫ����Ʒ�ļ��϶���
	 */
	List<Product> prodList();
	/**
	 * �޸���Ʒ����
	 * @param id����Ʒ��id
	 * @param newNum����Ʒ�޸ĺ������
	 */
	void updatePnum(String id, int newNum);
	/**
	 * ������Ʒidɾ������Ʒ
	 * @param id
	 */
	void deleteProd(String id);
	/**
	 * ���в�ѯ�����ķ�ҳ
	 * @param thispage:��ǰ�ڼ�ҳ
	 * @param rowperpage��mÿҳ��ʾ������
	 * @param name����Ʒ����
	 * @param category����Ʒ����
	 * @param min���۸�������Сֵ
	 * @param max���۸���������ֵ
	 * @return  ��װ�˵�ǰҳ�������Ϣ
	 */
	Page pageList(int thispage, int rowperpage, String name, String category,
			double min, double max);
	/**
	 * ������Ʒid��ѯ����Ʒ��Ϣ
	 * @param pid��Ʒid
	 * @return ��Ʒ��Ϣ
	 */
	Product findProdById(String pid) throws MsgException;

}
