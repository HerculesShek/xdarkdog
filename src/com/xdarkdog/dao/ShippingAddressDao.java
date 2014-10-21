package com.xdarkdog.dao;

import java.util.List;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.pojo.UserShippingAddress;

public class ShippingAddressDao extends DaoSupport {
	// 增加一条配送信息
	public int addUserShippingAddress(UserShippingAddress addr) {
		String sql = "INSERT INTO `ddcommunity`.`tbl_user_shipping_address` (`uesrname`, `realname`, `gender`, `phone`, `location`) VALUES (?,?,?,?,?);";
		Object params[] = { addr.getUsername(), addr.getRealname(), addr.getGender(), addr.getPhone(), addr.getLocation() };
		int affectRows = execOther(sql, params);
		return affectRows;
	}

	// 根据用户的id获取此用户所有的配送地址
	public List<UserShippingAddress> getAddrsByUsername(String username) {
		String sql = "SELECT a.* FROM ddcommunity.tbl_user_shipping_address a, ddcommunity.tbl_user u where a.userid=u.id and u.username=? order by default_flag desc";
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
	
	// 把一个配送地址设为默认的
	public int setDefault(int addrId){
		String sql = "UPDATE `ddcommunity`.`tbl_user_shipping_address` SET `default_flag`='1' WHERE `id`=?";
		Object[] param = {addrId};
		return execOther(sql, param);
	}

}
