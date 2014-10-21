package com.xdarkdog.dao;

import java.util.List;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.pojo.User;

public class UserDao extends DaoSupport {
	public int addUser(User user) {
		String sql = "INSERT INTO `ddcommunity`.`tbl_user` (`username`, `phone`, `password`, `registration_time`) VALUES (?, ?, ?, ?);";
		Object[] params = { user.getUsername(), user.getPhone(), user.getPassword(), user.getRegistration_time()};
		int res = execOther(sql, params);
		System.out.println(res + "条用户信息添加到user中了！！！");
		return res;
	}

	public User getUserById(int id) {
		String sql = "select * from user where user_id =  ?";
		Object[] params = { id };
		List<User> users = executeQuery(sql, User.class, params);
		if (users != null && users.size() > 0)
			return users.get(0);
		else
			return null;
	}
	
	public User getUserByUserName(String username, String passwd) {
		List<User> users = null;
		if (passwd != null) {
			String sql = "SELECT * FROM ddcommunity.tbl_user where `username`= ? and `password`= ?;";
			Object[] params = { username, passwd };
			users = executeQuery(sql, User.class, params);
		} else {
			String sql = "SELECT * FROM ddcommunity.tbl_user where `username`= ?";
			Object[] params = { username };
			users = executeQuery(sql, User.class, params);
		}
		if (users != null && users.size() > 0)
			return users.get(0);
		else
			return null;
	}
	
	public User getUserByPhone(String phone, String passwd) {
		List<User> users = null;
		if (passwd != null) {
			String sql = "SELECT * FROM ddcommunity.tbl_user where `phone`= ? and `password`= ?;";
			Object[] params = { phone, passwd };
			users = executeQuery(sql, User.class, params);
		} else {
			String sql = "SELECT * FROM ddcommunity.tbl_user where `phone`= ?";
			Object[] params = { phone };
			users = executeQuery(sql, User.class, params);
		}
		if (users != null && users.size() > 0)
			return users.get(0);
		else
			return null;
	}
	
	

}
