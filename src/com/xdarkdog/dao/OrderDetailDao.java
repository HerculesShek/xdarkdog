package com.xdarkdog.dao;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.pojo.OrderDetail;

public class OrderDetailDao extends DaoSupport {
	public int addOrderDatail(OrderDetail detail){
		String sql = "INSERT INTO `ddcommunity`.`tbl_order_detail` (`order_id`, `fruit_id`, `fruit_count`) VALUES (?,?,?);";
		Object[] params = {detail.getOrder_id(), detail.getFruit_id(), detail.getFruit_count()};
		return execOther(sql, params);
	}
}
