package com.xdarkdog.dao;

import java.util.List;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.pojo.UserShippingAddress;

public class ShippingAddressDao extends DaoSupport {
	// 增加一条配送信息
	public int addUserShippingAddress(UserShippingAddress addr) {
		String sql = "INSERT INTO `ddcommunity`.`tbl_user_shipping_address` (`username`, `realname`, `gender`, `phone`, `location`) VALUES (?,?,?,?,?);";
		Object params[] = { addr.getUsername(), addr.getRealname(), addr.getGender(), addr.getPhone(), addr.getLocation() };
		int affectRows = execOther(sql, params);
		return affectRows;
	}

	// 根据用户名获取此用户所有的配送地址
	public List<UserShippingAddress> getAddrsByUsername(String username) {
		String sql = "SELECT a.* FROM ddcommunity.tbl_user_shipping_address a, ddcommunity.tbl_user u where a.username=u.username and u.username=? order by default_flag desc";
		Object[] params = { username };
		List<UserShippingAddress> addrs = executeQuery(sql, UserShippingAddress.class, params);
		return addrs;
	}
	
	// 删除一个配送地址 
	public int removeShippingAddr(int addrId){
		String sql = "delete FROM ddcommunity.tbl_user_shipping_address where id=?";
		Object[] param = {addrId};
		return execOther(sql, param);
	}
	
	// 修改一个配送地址
	public int modifyShippingAddr(UserShippingAddress addr){
		String sql = "UPDATE `ddcommunity`.`tbl_user_shipping_address` SET `realname`=?, `gender`=?, `phone`=?, `location`=? WHERE `id`=?";
		Object[] params = {addr.getRealname(), addr.getGender(), addr.getPhone(), addr.getLocation(), addr.getId()};
		return execOther(sql, params);
	}
	
	// 根据id获取配送地址信息 用于显示订单用
	public UserShippingAddress getAddrById(int addrId){
		String _sql = "SELECT * FROM ddcommunity.tbl_user_shipping_address where id=?";
		Object[] ps = { addrId };
		List<UserShippingAddress> addrs = executeQuery(_sql, UserShippingAddress.class, ps);
		if (addrs != null && addrs.size() > 0) {
			return addrs.get(0);
		} else {
			return null;
		}
	}
	
	
	// 把一个配送地址设为默认的 TODO 这里只传入一个addrid是不够方便的
	public int setDefault(int addrId){
		// 获取这个配送地址id的配送地址，主要是为了获取用户的username
		UserShippingAddress a = getAddrById(addrId);
		String username;
		if (a != null) {
			username = a.getUsername();
		} else {
			return 0;
		}
		// 把这个用户的所有的配送地址的id都置为0
		String _sql2 = "UPDATE ddcommunity.tbl_user_shipping_address SET `default_flag`='0' where `username`=?";
		Object[] ps2 = {username};
		execOther(_sql2, ps2);
		// 把这个配送地址置为默认
		String sql = "UPDATE `ddcommunity`.`tbl_user_shipping_address` SET `default_flag`='1' WHERE `id`=?";
		Object[] param = {addrId};
		return execOther(sql, param);
	}
	
	public static void main(String[] args) {
		ShippingAddressDao dao  = new ShippingAddressDao();
		List<UserShippingAddress> ll = dao.getAddrsByUsername("heihei");
		for(UserShippingAddress u : ll ){
			System.out.println(u);
		}
	}

}
