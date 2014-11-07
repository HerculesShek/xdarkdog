package com.xdarkdog.web.util.data;

import java.util.Date;
import java.util.List;

/**
 * 实际上是把OrderInformation封装的
 * 
 * @author Hercules
 * 
 */
public class OrderData {
	// 订单的信息
	private String order_id; // 订单号 主键
	private int order_type; // 订单类型 1 表示 普通订单 2 表示预约订单
	private Date subscribe_delivery_time; // 预约订单的配送时间
	private Date create_time; // 订单创建时间
	private int status; // 订单的状态，目前先用此字段，
						// 上面的 paid_tag delivery_tag receipt_tag cancel_tag 暂时不用
						// 1 表示待审核是用户下单 客服还没有审核
						// 2 表示审核不通过 是客服电话联系用户之后没有给予通过审核
						// 3 表示审核通过 正在配送
						// 4 订单完成 成功送到 并且付款
						// 5 用户手动取消了订单 不管什么时候

	// 配送地址有关
	private String realname; // 用户真实姓名
	private int gender; // 用户性别 1表示先生 0表示女士
	private String phone; // 用户联系电话
	private String location; // 用户详细地址
	// 水果店信息有关
	private String fruit_shop_name; // 水果店名字
	private String comm_name; // 社区名字
	List<OrderDetailData> details;

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

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFruit_shop_name() {
		return fruit_shop_name;
	}

	public void setFruit_shop_name(String fruit_shop_name) {
		this.fruit_shop_name = fruit_shop_name;
	}

	public String getComm_name() {
		return comm_name;
	}

	public void setComm_name(String comm_name) {
		this.comm_name = comm_name;
	}

	public List<OrderDetailData> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetailData> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "OrderData [order_id=" + order_id + ", order_type=" + order_type
				+ ", subscribe_delivery_time=" + subscribe_delivery_time
				+ ", create_time=" + create_time + ", status=" + status
				+ ", realname=" + realname + ", gender=" + gender + ", phone="
				+ phone + ", location=" + location + ", fruit_shop_name="
				+ fruit_shop_name + ", comm_name=" + comm_name + ", details="
				+ details + "]";
	}

}
