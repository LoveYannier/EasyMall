package cn.tedu.domain;

import java.util.Date;

public class Product {
	private String id;//��Ʒid
	private String name;//��Ʒ��
	private double price;//�۸�
	private String category;//����
	private int pnum;//���
	private String imgurl;//��ƷͼƬ�Ĳ��֡�ȫ·������
	private String description;//����
	
	
	public int hashCode(){
		new Date().toLocaleString();
		return 31+(id==null?0:id.hashCode());
	}
	@Override
	public boolean equals(Object obj) {
		if(this==obj){
			return true;
		}
		if(obj==null){
			return false;
		}
		if(this.getClass()!=obj.getClass()){
			return false;
		}
		Product other = (Product)obj;
		if(id==null){
			if(other.getId()!=null){
				return false;
			}
		}else if(!id.equals(other.getId())){
			return false;
		}
		return true;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
