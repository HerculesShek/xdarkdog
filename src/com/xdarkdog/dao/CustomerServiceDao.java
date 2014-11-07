package com.xdarkdog.dao;

import java.util.List;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.pojo.CustomerService;

public class CustomerServiceDao extends DaoSupport {
	// 根据用户名和密码查看是否存在 用于登录验证
	public CustomerService getCS(String username, String pwd) {
		String sql = "select * from ddcommunity.tbl_customer_service where `username`=? and `password`=?";
		List<CustomerService> css = executeQuery(sql, CustomerService.class, new Object[] {username, pwd});
		if (css != null && css.size() > 0)
			return css.get(0);
		else
			return null;
	}
}
