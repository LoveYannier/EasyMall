package cn.tedu.utils;

import java.util.List;

public class Page<T>{
	private List<T> list;//��ǰҳ�Ľ����
	private int thispage;//��ǰ�ڼ�ҳ
	private int rowperpage;//ÿҳ��ʾ������
	private int countrow;//������
	private int countpage;//��ҳ��
	private int prepage;//��һҳ
	private int nextpage;//��һҳ
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getThispage() {
		return thispage;
	}
	public void setThispage(int thispage) {
		this.thispage = thispage;
	}
	public int getRowperpage() {
		return rowperpage;
	}
	public void setRowperpage(int rowperpage) {
		this.rowperpage = rowperpage;
	}
	public int getCountrow() {
		return countrow;
	}
	public void setCountrow(int countrow) {
		this.countrow = countrow;
	}
	public int getCountpage() {
		return countpage;
	}
	public void setCountpage(int countpage) {
		this.countpage = countpage;
	}
	public int getPrepage() {
		return prepage;
	}
	public void setPrepage(int prepage) {
		this.prepage = prepage;
	}
	public int getNextpage() {
		return nextpage;
	}
	public void setNextpage(int nextpage) {
		this.nextpage = nextpage;
	}
}
