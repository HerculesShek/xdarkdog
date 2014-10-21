package com.xdarkdog.dao;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.pojo.Order;

public class OrderDao extends DaoSupport {
	// 生生一份订单
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
	
}
