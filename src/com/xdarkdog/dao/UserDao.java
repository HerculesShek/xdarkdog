package com.xdarkdog.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.pojo.User;

@Service
public class UserDao extends DaoSupport {

	// 用户注册
	public int addUser(User user) {
		String sql = "INSERT INTO `ddcommunity`.`tbl_user` (`username`, `phone`, `password`, `registration_time`) VALUES (?, ?, ?, ?);";
		Object[] params = { user.getUsername(), user.getPhone(),
				user.getPassword(), user.getRegistration_time() };
		int res = execOther(sql, params);
		System.out.println(res + "条用户信息添加到user中了！！！");
		return res;
	}

	// 根据id获取用户
	public User getUserById(int id) {
		String sql = "select * from user where user_id =  ?";
		Object[] params = { id };
		List<User> users = executeQuery(sql, User.class, params);
		if (users != null && users.size() > 0)
			return users.get(0);
		else
			return null;
	}

	// 根据“用户用户名或者是 用户名和密码”获取用户
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

	// 根据“用户电话或者是 电话和密码”获取用户
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

	// 更改密码
	public int changePasswd(User user) {
		String sql = "UPDATE `ddcommunity`.`tbl_user` SET `password`=? WHERE `username`=?;";
		Object[] params = { user.getPassword(), user.getUsername() };
		return execOther(sql, params);
	}

}
