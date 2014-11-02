package com.xdarkdog.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.web.util.Order_Info;

@Service
public class Order_InfoDao extends DaoSupport {

	public List<Order_Info> getInfosByUsername(String username) {
		String sql = "SELECT o.order_id order_id, "
				+ "o.order_type order_type, "
				+ "o.subscribe_delivery_time subscribe_delivery_time, "
				+ "o.totalcost totalcost, "
				+ "o.create_time create_time, "
				+ "o.`status` `status`, "
				+ "f.`name` `name`, "
				+ "d.fruit_count fruit_count, "
				+ "f.photos photos "
				+ "FROM ddcommunity.tbl_order o, tbl_order_detail d, tbl_fruit f "
				+ "where o.order_id=d.order_id and d.fruit_id=f.id and o.username=? "
				+ "order by o.create_time desc;";
		Object[] params = { username };
		return executeQuery(sql, Order_Info.class, params);
	}

	public static void main(String[] args) {
		Order_InfoDao d = new Order_InfoDao();
		for (Order_Info i : d.getInfosByUsername("heihei"))
			System.out.println(i);
	}

	public List<Order_Info> getByPaging(String userName, Integer pageNo, Integer pageSize, Integer status) {

		String sql = "SELECT o.order_id order_id, "
				+ "o.order_type order_type, "
				+ "o.subscribe_delivery_time subscribe_delivery_time, "
				+ "o.totalcost totalcost, "
				+ "o.create_time create_time, "
				+ "o.`status` `status`, "
				+ "f.`name` `name`, "
				+ "d.fruit_count fruit_count, "
				+ "f.photos photos "
				+ "FROM ddcommunity.tbl_order o, tbl_order_detail d, tbl_fruit f "
				+ "where o.order_id=d.order_id and d.fruit_id=f.id and o.username=? "
				+ (status == null ? "" : " and o.`status` = ? ")
				+ "order by o.create_time desc LIMIT ?,?;";
		
		Integer offset = (pageNo - 1) * pageSize;
		Integer rows = pageSize;
		
		if(null == status) {
			return executeQuery(sql, Order_Info.class, new Object[]{userName,offset, rows});
		} else {
			return executeQuery(sql, Order_Info.class, new Object[]{userName,status, offset, rows});
		}
	}
}
