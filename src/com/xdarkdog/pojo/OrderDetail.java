package com.xdarkdog.pojo;

public class OrderDetail {
	private int detail_id; // 订单详情id
	private String order_id; // 订单号
	private int fruit_id; // 水果id
	private double fruit_count; // 水果的数量
	private int level = 2; // 水果的大小等级 1小 2中 3大
	
	public int getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(int detail_id) {
		this.detail_id = detail_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public int getFruit_id() {
		return fruit_id;
	}
	public void setFruit_id(int fruit_id) {
		this.fruit_id = fruit_id;
	}
	public double getFruit_count() {
		return fruit_count;
	}
	public void setFruit_count(double fruit_count) {
		this.fruit_count = fruit_count;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "OrderDetail [detail_id=" + detail_id + ", order_id=" + order_id
				+ ", fruit_id=" + fruit_id + ", fruit_count=" + fruit_count
				+ ", level=" + level + "]";
	}
	public OrderDetail(int detail_id, String order_id, int fruit_id,
			double fruit_count, int level) {
		super();
		this.detail_id = detail_id;
		this.order_id = order_id;
		this.fruit_id = fruit_id;
		this.fruit_count = fruit_count;
		this.level = level;
	}
	public OrderDetail() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + detail_id;
		long temp;
		temp = Double.doubleToLongBits(fruit_count);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + fruit_id;
		result = prime * result + level;
		result = prime * result
				+ ((order_id == null) ? 0 : order_id.hashCode());
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
		OrderDetail other = (OrderDetail) obj;
		if (detail_id != other.detail_id)
			return false;
		if (Double.doubleToLongBits(fruit_count) != Double
				.doubleToLongBits(other.fruit_count))
			return false;
		if (fruit_id != other.fruit_id)
			return false;
		if (level != other.level)
			return false;
		if (order_id == null) {
			if (other.order_id != null)
				return false;
		} else if (!order_id.equals(other.order_id))
			return false;
		return true;
	}
	
}
