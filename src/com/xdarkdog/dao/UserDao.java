package com.xdarkdog.dao;

import java.util.List;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.pojo.User;

public class UserDao extends DaoSupport {
	public void addUser(User user) {
		String sql = "INSERT INTO `ddcommunity`.`tbl_user` (`userkey`,`first_time`) VALUES (?, ?);";
		Object[] params = { user.getUserkey(), user.getFirst_time() };
		int i = execOther(sql, params);
		System.out.println(i + "条用户星系添加到user中了！！！");
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
	public User getUserByKey(String key) {
		String sql = "select * from tbl_user where userkey =  ?";
		Object[] params = { key };
		List<User> users = executeQuery(sql, User.class, params);
		if (users != null && users.size() > 0)
			return users.get(0);
		else
			return null;
	}

}
