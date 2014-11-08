package com.xdarkdog.dao;

import java.util.List;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.web.util.OrderInformation;

public class OrderInformationDao extends DaoSupport {
	// 获取未审核订单详情
	public List<OrderInformation> getUnauditedOrderInfos(){
		String sql = "select o.order_id order_id, " +
				"o.order_type order_type, " +
				"o.subscribe_delivery_time subscribe_delivery_time, " +
				"o.create_time create_time, " +
				"o.`status` `status`, " +
				"a.realname realname, " +
				"a.gender gender, " +
				"a.phone phone, " +
				"a.location location, " +
				"c.comm_name comm_name, " +
				"c.fruit_shop_name fruit_shop_name, " +
				"f.`name` `name`, " +
				"f.price price, " +
				"f.measurement_type  measurement_type, " +
				"d.fruit_count fruit_count, " +
				"d.`level` `level` " +
				"from tbl_order o, tbl_order_detail d, tbl_community c, tbl_fruit f, tbl_user_shipping_address a " +
				"where o.order_id = d.order_id and o.commid = c.id and d.fruit_id = f.id and o.shipid = a.id " +
				"and o.`status` = 1";
		List<OrderInformation> infos = executeQuery(sql, OrderInformation.class, null);
		return infos;
	}
	
	// 获取正在配送订单详情
	public List<OrderInformation> getShippingOrderInfos(){
		String sql = "select o.order_id order_id, " +
				"o.order_type order_type, " +
				"o.subscribe_delivery_time subscribe_delivery_time, " +
				"o.create_time create_time, " +
				"o.`status` `status`, " +
				"a.realname realname, " +
				"a.gender gender, " +
				"a.phone phone, " +
				"a.location location, " +
				"c.comm_name comm_name, " +
				"c.fruit_shop_name fruit_shop_name, " +
				"f.`name` `name`, " +
				"f.price price, " +
				"f.measurement_type  measurement_type, " +
				"d.fruit_count fruit_count, " +
				"d.`level` `level` " +
				"from tbl_order o, tbl_order_detail d, tbl_community c, tbl_fruit f, tbl_user_shipping_address a " +
				"where o.order_id = d.order_id and o.commid = c.id and d.fruit_id = f.id and o.shipid = a.id " +
				"and o.`status` = 3";
		List<OrderInformation> infos = executeQuery(sql, OrderInformation.class, null);
		return infos;
	}
	
	// 获取预约并且没有审核订单详情 
	public List<OrderInformation> getSubscribeOrderInfos(){
		String sql = "select o.order_id order_id, " +
				"o.order_type order_type, " +
				"o.subscribe_delivery_time subscribe_delivery_time, " +
				"o.create_time create_time, " +
				"o.`status` `status`, " +
				"a.realname realname, " +
				"a.gender gender, " +
				"a.phone phone, " +
				"a.location location, " +
				"c.comm_name comm_name, " +
				"c.fruit_shop_name fruit_shop_name, " +
				"f.`name` `name`, " +
				"f.price price, " +
				"f.measurement_type  measurement_type, " +
				"d.fruit_count fruit_count, " +
				"d.`level` `level` " +
				"from tbl_order o, tbl_order_detail d, tbl_community c, tbl_fruit f, tbl_user_shipping_address a " +
				"where o.order_id = d.order_id and o.commid = c.id and d.fruit_id = f.id and o.shipid = a.id " +
				"and o.`status` = 1 and o.order_type = 2";
		List<OrderInformation> infos = executeQuery(sql, OrderInformation.class, null);
		return infos;
	}
	
	// TODO 应该再加入一个查询 预约的订单 审核通过但是还没有配送的 接口
	
	// 获取指定order_id的订单详情
	public List<OrderInformation> getOrderInfoByOrderid(String order_id){
		String sql = "select o.order_id order_id, " +
				"o.order_type order_type, " +
				"o.subscribe_delivery_time subscribe_delivery_time, " +
				"o.create_time create_time, " +
				"o.`status` `status`, " +
				"a.realname realname, " +
				"a.gender gender, " +
				"a.phone phone, " +
				"a.location location, " +
				"c.comm_name comm_name, " +
				"c.fruit_shop_name fruit_shop_name, " +
				"f.`name` `name`, " +
				"f.price price, " +
				"f.measurement_type  measurement_type, " +
				"d.fruit_count fruit_count, " +
				"d.`level` `level` " +
				"from tbl_order o, tbl_order_detail d, tbl_community c, tbl_fruit f, tbl_user_shipping_address a " +
				"where o.order_id = d.order_id and o.commid = c.id and d.fruit_id = f.id and o.shipid = a.id " +
				"and o.order_id=?";
		List<OrderInformation> infos = executeQuery(sql, OrderInformation.class, new Object[]{order_id});
		return infos;
	}
	
	public static void main(String[] args) {
		OrderInformationDao dao = new OrderInformationDao();
		List<OrderInformation> infos = dao.getSubscribeOrderInfos();
		for(OrderInformation o : infos){
			System.out.println(o);
		}
	}
}
