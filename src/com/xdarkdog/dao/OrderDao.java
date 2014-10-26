package com.xdarkdog.dao;

import java.util.List;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.pojo.Order;

public class OrderDao extends DaoSupport {
	// 生成一份订单
	public int addOrder(Order order) {
		String sql = "INSERT INTO `ddcommunity`.`tbl_order` (`order_id`, " +
				"`order_type`, " + 
				"`username`, " + 
				"`commid`, " + 
				"`shipid`, " + 
				"`subscribe_delivery_time`, " +
				"`create_time` ) VALUES (?,?,?,?,?,?,?);";
		Object[] params = {order.getOrder_id(), 
				order.getOrder_type(), 
				order.getUsername(), 
				order.getCommid(), 
				order.getShipid(), 
				order.getSubscribe_delivery_time(),
				order.getCreate_time()};
		
		int affectRows = execOther(sql, params);
		return affectRows;
	}
	
	// 根据用户名 获取所有的订单
	public List<Order> getOrderByUsername(String username) {
		String sql = "SELECT * FROM ddcommunity.tbl_order where `username`=? order by create_time desc";
		Object[] params = { username };
		return executeQuery(sql, Order.class, params);
	}

	// 根据用户id 获取所有的订单
	public List<Order> getOrderByUserId(int user_id) {
		String sql = "SELECT o.* FROM ddcommunity.tbl_order o, ddcommunity.tbl_user u where o.`username`= u.username and u.id = ? order by create_time desc;";
		Object[] params = { user_id };
		return executeQuery(sql, Order.class, params);
	}

	// 获取所有的未审核的订单
	public List<Order> getUnauditedOrder() {
		String sql = "SELECT * FROM ddcommunity.tbl_order where status=1 order by create_time desc;";
		return executeQuery(sql, Order.class, null);
	}

	// 获取所有的正在配送的订单
	public List<Order> getShippingOrders() {
		String sql = "SELECT * FROM ddcommunity.tbl_order where status=3 order by create_time desc;";
		return executeQuery(sql, Order.class, null);
	}
	
	// 取消订单
	public int cancelOrder(String order_id) {
		return dealOrder(order_id, 5);
	}

	// 客服 审核订单
	public int auditOrder(String order_id, int status) {
		return dealOrder(order_id, status);
	}

	// 使订单完成 并且填写交易金额
	public int finishOrder(Order order) {
		String sql = "update ddcommunity.tbl_order set status=?, set totalcost=? where order_id=?";
		Object[] params = { 4, order.getTotalcost(), order.getOrder_id()};
		return execOther(sql, params);
	}

	// 私有方法 处理订单 将订单置为某个状态
	private int dealOrder(String order_id, int status) {
		String sql = "update ddcommunity.tbl_order set status=? where order_id = ?";
		Object[] params = { status, order_id };
		return execOther(sql, params);
	}
	
	public static void main(String[] args) {
		OrderDao dao = new OrderDao();
		System.out.println(dao.cancelOrder("1"));
	}
	
}
