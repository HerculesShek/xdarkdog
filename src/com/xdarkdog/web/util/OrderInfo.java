package com.xdarkdog.web.util;

import java.util.Date;
import java.util.List;

import com.xdarkdog.pojo.Community;
import com.xdarkdog.pojo.Fruit;
import com.xdarkdog.pojo.Order;
import com.xdarkdog.pojo.OrderDetail;
import com.xdarkdog.pojo.User;
import com.xdarkdog.pojo.UserShippingAddress;

/**
 * 这个类就是为了显示信息的方便 为了JSON使用，交互数据用的
 * 
 * @author Hercules
 * 
 */
public class OrderInfo {
	private Order order;
	private User user; // 用户的详细信息
	private Community community; // 订购的社区的详细信息
	private UserShippingAddress shipAddr; // 配送地址的详细信息
	private List<DetailInfo> detail_infos; // 订单的详细信息

	public OrderInfo() {
		super();
	}

	public OrderInfo(Order order, User user, Community community,
			UserShippingAddress shipAddr, List<DetailInfo> detail_infos) {
		super();
		this.order = order;
		this.user = user;
		this.community = community;
		this.shipAddr = shipAddr;
		this.detail_infos = detail_infos;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public UserShippingAddress getShipAddr() {
		return shipAddr;
	}

	public void setShipAddr(UserShippingAddress shipAddr) {
		this.shipAddr = shipAddr;
	}

	public List<DetailInfo> getDetail_infos() {
		return detail_infos;
	}

	public void setDetail_infos(List<DetailInfo> detail_infos) {
		this.detail_infos = detail_infos;
	}

	@Override
	public String toString() {
		return "OrderInfo [order=" + order + ", user=" + user + ", community="
				+ community + ", shipAddr=" + shipAddr + ", detail_infos="
				+ detail_infos + "]";
	}

}
