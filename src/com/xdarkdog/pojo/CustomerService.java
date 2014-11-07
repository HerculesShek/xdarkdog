package com.xdarkdog.pojo;

/**
 * 客服人员的信息
 * @author Hercules
 *
 */
public class CustomerService {
	private int cs_id;       	// 客服的id
	private String username;	// 客服的用户名
	private String password;	// 客服的密码
	private String realname;	// 客服的真实名字

	public int getCs_id() {
		return cs_id;
	}

	public void setCs_id(int cs_id) {
		this.cs_id = cs_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public CustomerService(int cs_id, String username, String password,
			String realname) {
		super();
		this.cs_id = cs_id;
		this.username = username;
		this.password = password;
		this.realname = realname;
	}

	public CustomerService() {
		super();
	}

	@Override
	public String toString() {
		return "CustomerService [cs_id=" + cs_id + ", username=" + username
				+ ", password=" + password + ", realname=" + realname + "]";
	}

}
