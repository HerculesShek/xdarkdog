package com.xdarkdog.pojo;

public class OrderDetail {
	private int detail_id;;
	private String order_id;
	private int fruit_id;
	private int fruit_count;

	public int getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(int detail_id) {
		this.detail_id = detail_id;
	}

	public int getFruit_id() {
		return fruit_id;
	}

	public void setFruit_id(int fruit_id) {
		this.fruit_id = fruit_id;
	}

	public int getFruit_count() {
		return fruit_count;
	}

	public void setFruit_count(int fruit_count) {
		this.fruit_count = fruit_count;
	}

	public OrderDetail() {
		super();
	}

	@Override
	public String toString() {
		return "OrderDetail [detail_id=" + detail_id + ", order_id=" + order_id
				+ ", fruit_id=" + fruit_id + ", fruit_count=" + fruit_count
				+ "]";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public OrderDetail(int detail_id, String order_id, int fruit_id,
			int fruit_count) {
		super();
		this.detail_id = detail_id;
		this.order_id = order_id;
		this.fruit_id = fruit_id;
		this.fruit_count = fruit_count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + detail_id;
		result = prime * result + fruit_count;
		result = prime * result + fruit_id;
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
		if (fruit_count != other.fruit_count)
			return false;
		if (fruit_id != other.fruit_id)
			return false;
		if (order_id == null) {
			if (other.order_id != null)
				return false;
		} else if (!order_id.equals(other.order_id))
			return false;
		return true;
	}

}
