package com.xdarkdog.pojo;

import com.alibaba.fastjson.JSON;

public class Fruit {
	private int id;
	private String name=""; // 水果名字
	private double price; // 在售价格
	private String measurement_type = "斤"; // 计量单位 是按斤卖还是按个卖 或者其他
	private int points; // 一个单位的水果会获得多少的积分
	private int communityid; // 这个水鬼所属的社区id
	private double original_price; // 进货价
	private double display_price; // 展示价格
	private int hot_tag; // 是否热卖 1表示热卖 0表示不热卖
	private int commend_tag; // 是否推荐
	private String remark=""; // 简介
	private String introduce=""; // 详细介绍
	private int hitcount = 0; // 点击次数
	private String photos=""; // 图片的url串
	private int valid_tag = 1; // 水果是否有效，就是是否在售 1表示正常 0表示不在售

	public String getMeasurement_type() {
		return measurement_type;
	}

	public void setMeasurement_type(String measurement_type) {
		this.measurement_type = measurement_type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getCommunityid() {
		return communityid;
	}

	public void setCommunityid(int communityid) {
		this.communityid = communityid;
	}

	public double getOriginal_price() {
		return original_price;
	}

	public void setOriginal_price(double original_price) {
		this.original_price = original_price;
	}

	public double getDisplay_price() {
		return display_price;
	}

	public void setDisplay_price(double display_price) {
		this.display_price = display_price;
	}

	public int getHot_tag() {
		return hot_tag;
	}

	public void setHot_tag(int hot_tag) {
		this.hot_tag = hot_tag;
	}

	public int getCommend_tag() {
		return commend_tag;
	}

	public void setCommend_tag(int commend_tag) {
		this.commend_tag = commend_tag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public int getHitcount() {
		return hitcount;
	}

	public void setHitcount(int hitcount) {
		this.hitcount = hitcount;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public int getValid_tag() {
		return valid_tag;
	}

	public void setValid_tag(int valid_tag) {
		this.valid_tag = valid_tag;
	}

	public Fruit(int id, String name, double price, String measurement_type,
			int points, int communityid, double original_price,
			double display_price, int hot_tag, int commend_tag, String remark,
			String introduce, int hitcount, String photos, int valid_tag) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.measurement_type = measurement_type;
		this.points = points;
		this.communityid = communityid;
		this.original_price = original_price;
		this.display_price = display_price;
		this.hot_tag = hot_tag;
		this.commend_tag = commend_tag;
		this.remark = remark;
		this.introduce = introduce;
		this.hitcount = hitcount;
		this.photos = photos;
		this.valid_tag = valid_tag;
	}

	public Fruit() {
		super();
	}

	@Override
	public String toString() {
		return "Fruit [id=" + id + ", name=" + name + ", price=" + price
				+ ", measurement_type=" + measurement_type + ", points="
				+ points + ", communityid=" + communityid + ", original_price="
				+ original_price + ", display_price=" + display_price
				+ ", hot_tag=" + hot_tag + ", commend_tag=" + commend_tag
				+ ", remark=" + remark + ", introduce=" + introduce
				+ ", hitcount=" + hitcount + ", photos=" + photos
				+ ", valid_tag=" + valid_tag + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + commend_tag;
		result = prime * result + communityid;
		long temp;
		temp = Double.doubleToLongBits(display_price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + hitcount;
		result = prime * result + hot_tag;
		result = prime * result + id;
		result = prime * result
				+ ((introduce == null) ? 0 : introduce.hashCode());
		result = prime
				* result
				+ ((measurement_type == null) ? 0 : measurement_type.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(original_price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((photos == null) ? 0 : photos.hashCode());
		result = prime * result + points;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + valid_tag;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fruit other = (Fruit) obj;
		if (commend_tag != other.commend_tag)
			return false;
		if (communityid != other.communityid)
			return false;
		if (Double.doubleToLongBits(display_price) != Double
				.doubleToLongBits(other.display_price))
			return false;
		if (hitcount != other.hitcount)
			return false;
		if (hot_tag != other.hot_tag)
			return false;
		if (id != other.id)
			return false;
		if (introduce == null) {
			if (other.introduce != null)
				return false;
		} else if (!introduce.equals(other.introduce))
			return false;
		if (measurement_type == null) {
			if (other.measurement_type != null)
				return false;
		} else if (!measurement_type.equals(other.measurement_type))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(original_price) != Double
				.doubleToLongBits(other.original_price))
			return false;
		if (photos == null) {
			if (other.photos != null)
				return false;
		} else if (!photos.equals(other.photos))
			return false;
		if (points != other.points)
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (valid_tag != other.valid_tag)
			return false;
		return true;
	}

	public static void main(String[] args) {
		System.out.println(JSON.toJSONString(new Fruit()));
	}
}
