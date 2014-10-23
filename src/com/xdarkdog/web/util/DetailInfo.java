package com.xdarkdog.web.util;

import com.xdarkdog.pojo.Fruit;
import com.xdarkdog.pojo.OrderDetail;


/**
 * 订单详情的信息 用于JSON 主要用于数据交互
 * @author Hercules
 *
 */
public class DetailInfo {
	private OrderDetail detail;
	private Fruit fruit;

	public OrderDetail getDetail() {
		return detail;
	}

	public void setDetail(OrderDetail detail) {
		this.detail = detail;
	}

	public Fruit getFruit() {
		return fruit;
	}

	public void setFruit(Fruit fruit) {
		this.fruit = fruit;
	}

	public DetailInfo(OrderDetail detail, Fruit fruit) {
		super();
		this.detail = detail;
		this.fruit = fruit;
	}

	public DetailInfo() {
		super();
	}

	@Override
	public String toString() {
		return "DetailInfo [detail=" + detail + ", fruit=" + fruit + "]";
	}

}
