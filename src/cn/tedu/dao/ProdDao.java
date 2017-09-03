package cn.tedu.dao;

import java.sql.Connection;
import java.util.List;

import cn.tedu.domain.Product;

public interface ProdDao extends Dao{
	/**
	 * ��Ʒ��ӵķ���
	 * @param prod
	 */
	void addProduct(Product prod);
	/**
	 * ��̨��ѯȫ����Ʒ����Ϣ
	 * @return ������Ʒ�ļ��϶���
	 */
	List<Product> prodList();
	/**
	 * �޸���Ʒ����
	 * @param id����Ʒ��id
	 * @param newNum��Ʒ�޸ĺ������
	 * @return��Ӱ�������
	 */
	int updatePnum(String id, int newNum);
	/**
	 * ������Ʒ��idɾ������Ʒ
	 * @param id����Ʒid
	 * @return Ӱ�������
	 */
	int deleteProd(String id);
	/**��ҳ��ѯ֮����ѯ������������Ʒ����
	 * @param name����Ʒ����
	 * @param category����
	 * @param min �۸��������Сֵ
	 * @param max�۸���������ֵ
	 * @return ������������Ʒ������
	 */
	int getProdKeyCount(String name, String category, double min, double max);
	/**��ҳ��ѯ֮����ѯ������������Ʒ����
	 * @param start������һ����ʼ��ѯ
	 * @param rowperpage����ѯ������
	 * @param name����Ʒ��
	 * @param category����
	 * @param min�۸��������Сֵ
	 * @param max�۸���������ֵ
	 * @return ������������Ʒ����
	 */
	List<Product> findProdsByKeyLimit(int start, int rowperpage, String name,
			String category, double min, double max);
	/**
	 * ������Ʒid��ѯ��Ӧ����Ʒ��Ϣ
	 * @param pid��Ʒid
	 * @return ��Ʒ��Ϣ����
	 */
	Product findProdById(String pid);
}
