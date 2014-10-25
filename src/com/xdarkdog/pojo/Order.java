package com.xdarkdog.pojo;

import java.util.Date;

public class Order {
	private String order_id; // 订单号 主键
	private int order_type; // 订单类型 1 表示 普通订单 2 表示预约订单
	private Date subscribe_delivery_time; // 预约订单的配送时间
	private String username; // 用户名
	private int commid; // 水果店id
	private int shipid; // 配送地址id
	private double totalcost; // 总消费 TODO 客服在完成订单的时候填写用户实付款
	private Date create_time; // 订单创建时间
	private int pay_style; // 支付类型 1表示在线支付 2表示到付
	private int paid_tag; // 是不是支付 0未支付 1已支付
	private int delivery_tag; // 是否配送 0未配送 1 已配送
	private int receipt_tag; // 是否收到 0未收到 1 已收到
	private int cancel_tag; // 是否取消 0 未取消 1 取消
	private int status; // 订单的状态，目前先用此字段，
						// 上面的 paid_tag delivery_tag receipt_tag cancel_tag 暂时不用
						// status 中 1 表示未审核 是用户下单 客服还没有审核
						// 2 表示审核不通过 是客服电话联系用户之后没有给予通过审核
						// 3 表示审核通过 正在配送
						// 4 订单完成 成功送到 并且付款
						// 5 用户手动取消了订单 不管什么时候
	private Date audit_time;   // 订单的审核时间
	private Date finish_time;  // 订单的完成时间 
	
	public int getOrder_type() {
		return order_type;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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

	public int getCommid() {
		return commid;
	}

	public void setCommid(int commid) {
		this.commid = commid;
	}

	public int getShipid() {
		return shipid;
	}

	public void setShipid(int shipid) {
		this.shipid = shipid;
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

	public int getPay_style() {
		return pay_style;
	}

	public void setPay_style(int pay_style) {
		this.pay_style = pay_style;
	}

	public int getPaid_tag() {
		return paid_tag;
	}

	public void setPaid_tag(int paid_tag) {
		this.paid_tag = paid_tag;
	}

	public int getDelivery_tag() {
		return delivery_tag;
	}

	public void setDelivery_tag(int delivery_tag) {
		this.delivery_tag = delivery_tag;
	}

	public int getReceipt_tag() {
		return receipt_tag;
	}

	public void setReceipt_tag(int receipt_tag) {
		this.receipt_tag = receipt_tag;
	}

	public int getCancel_tag() {
		return cancel_tag;
	}

	public void setCancel_tag(int cancel_tag) {
		this.cancel_tag = cancel_tag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getAudit_time() {
		return audit_time;
	}

	public void setAudit_time(Date audit_time) {
		this.audit_time = audit_time;
	}

	public Date getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}

	public Order() {
		super();
	}

	public Order(String order_id, int order_type, Date subscribe_delivery_time,
			String username, int commid, int shipid, double totalcost,
			Date create_time, int pay_style, int paid_tag, int delivery_tag,
			int receipt_tag, int cancel_tag, int status, Date audit_time,
			Date finish_time) {
		super();
		this.order_id = order_id;
		this.order_type = order_type;
		this.subscribe_delivery_time = subscribe_delivery_time;
		this.username = username;
		this.commid = commid;
		this.shipid = shipid;
		this.totalcost = totalcost;
		this.create_time = create_time;
		this.pay_style = pay_style;
		this.paid_tag = paid_tag;
		this.delivery_tag = delivery_tag;
		this.receipt_tag = receipt_tag;
		this.cancel_tag = cancel_tag;
		this.status = status;
		this.audit_time = audit_time;
		this.finish_time = finish_time;
	}

	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", order_type=" + order_type
				+ ", subscribe_delivery_time=" + subscribe_delivery_time
				+ ", username=" + username + ", commid=" + commid + ", shipid="
				+ shipid + ", totalcost=" + totalcost + ", create_time="
				+ create_time + ", pay_style=" + pay_style + ", paid_tag="
				+ paid_tag + ", delivery_tag=" + delivery_tag
				+ ", receipt_tag=" + receipt_tag + ", cancel_tag=" + cancel_tag
				+ ", status=" + status + ", audit_time=" + audit_time
				+ ", finish_time=" + finish_time + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((audit_time == null) ? 0 : audit_time.hashCode());
		result = prime * result + cancel_tag;
		result = prime * result + commid;
		result = prime * result
				+ ((create_time == null) ? 0 : create_time.hashCode());
		result = prime * result + delivery_tag;
		result = prime * result
				+ ((finish_time == null) ? 0 : finish_time.hashCode());
		result = prime * result
				+ ((order_id == null) ? 0 : order_id.hashCode());
		result = prime * result + order_type;
		result = prime * result + paid_tag;
		result = prime * result + pay_style;
		result = prime * result + receipt_tag;
		result = prime * result + shipid;
		result = prime * result + status;
		result = prime
				* result
				+ ((subscribe_delivery_time == null) ? 0
						: subscribe_delivery_time.hashCode());
		long temp;
		temp = Double.doubleToLongBits(totalcost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		Order other = (Order) obj;
		if (audit_time == null) {
			if (other.audit_time != null)
				return false;
		} else if (!audit_time.equals(other.audit_time))
			return false;
		if (cancel_tag != other.cancel_tag)
			return false;
		if (commid != other.commid)
			return false;
		if (create_time == null) {
			if (other.create_time != null)
				return false;
		} else if (!create_time.equals(other.create_time))
			return false;
		if (delivery_tag != other.delivery_tag)
			return false;
		if (finish_time == null) {
			if (other.finish_time != null)
				return false;
		} else if (!finish_time.equals(other.finish_time))
			return false;
		if (order_id == null) {
			if (other.order_id != null)
				return false;
		} else if (!order_id.equals(other.order_id))
			return false;
		if (order_type != other.order_type)
			return false;
		if (paid_tag != other.paid_tag)
			return false;
		if (pay_style != other.pay_style)
			return false;
		if (receipt_tag != other.receipt_tag)
			return false;
		if (shipid != other.shipid)
			return false;
		if (status != other.status)
			return false;
		if (subscribe_delivery_time == null) {
			if (other.subscribe_delivery_time != null)
				return false;
		} else if (!subscribe_delivery_time
				.equals(other.subscribe_delivery_time))
			return false;
		if (Double.doubleToLongBits(totalcost) != Double
				.doubleToLongBits(other.totalcost))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
