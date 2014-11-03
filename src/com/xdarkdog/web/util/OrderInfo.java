package com.xdarkdog.web.util;

import java.util.Date;

/**
 * 这个类就是为了显示信息的方便 为了JSON使用，交互数据用的
 * 这个类的设计其实是冗余的，完全是为了符合数据库的查询结果而设计的
 * @author Hercules
 * 
 */
public class OrderInfo {
	private String order_id; // 订单号 主键
	private int order_type; // 订单类型 1 表示 普通订单 2 表示预约订单
	private Date subscribe_delivery_time; // 预约订单的配送时间
	private double totalcost; // 总消费  客服在完成订单的时候填写用户实付款
	private Date create_time; // 订单创建时间
	private int status; // 订单的状态，目前先用此字段，
						// 上面的 paid_tag delivery_tag receipt_tag cancel_tag 暂时不用
						// status 中 1 表示未审核 是用户下单 客服还没有审核
						// 2 表示审核不通过 是客服电话联系用户之后没有给予通过审核
						// 3 表示审核通过 正在配送
						// 4 订单完成 成功送到 并且付款
						// 5 用户手动取消了订单 不管什么时候
	// 订单的详细信息
	private String name=""; // 水果名字
	private String photos=""; // 图片的url串
	private double fruit_count; // 水果的数量
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public int getOrder_type() {
		return order_type;
	}
	public void setOrder_type(int order_type) {
		this.order_type = order_type;
	}
	public Date getSubscribe_delivery_time() {
		return subscribe_delivery_time;
	}
	public void setSubscribe_delivery_time(Date subscribe_delivery_time) {
		this.subscribe_delivery_time = subscribe_delivery_time;
	}
	public double getTotalcost() {
		return totalcost;
	}
	public void setTotalcost(double totalcost) {
		this.totalcost = totalcost;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public double getFruit_count() {
		return fruit_count;
	}
	public void setFruit_count(double fruit_count) {
		this.fruit_count = fruit_count;
	}
	public OrderInfo() {
		super();
	}
	public OrderInfo(String order_id, int order_type,
			Date subscribe_delivery_time, double totalcost, Date create_time,
			int status, String name, String photos, double fruit_count) {
		super();
		this.order_id = order_id;
		this.order_type = order_type;
		this.subscribe_delivery_time = subscribe_delivery_time;
		this.totalcost = totalcost;
		this.create_time = create_time;
		this.status = status;
		this.name = name;
		this.photos = photos;
		this.fruit_count = fruit_count;
	}
	@Override
	public String toString() {
		return "Order_Info [order_id=" + order_id + ", order_type="
				+ order_type + ", subscribe_delivery_time="
				+ subscribe_delivery_time + ", totalcost=" + totalcost
				+ ", create_time=" + create_time + ", status=" + status
				+ ", name=" + name + ", photos=" + photos + ", fruit_count="
				+ fruit_count + "]";
	}
	
}
