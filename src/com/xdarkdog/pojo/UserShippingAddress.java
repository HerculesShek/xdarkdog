package com.xdarkdog.pojo;

import java.util.Date;

/**
 * 用户配送地址信息
 * 
 * @author Hercules
 */
public class UserShippingAddress {
	private int id;
	private int userid; // 用户id
	private String username; // 用户昵称
	private String realname; // 用户真实姓名
	private int gender; // 用户性别 1表示先生  0表示女士
	private String phone; // 用户电话
	private String location; // 用户详细地址
	private double lat; // 配送地址的维度 可为空
	private double lon; // 配送地址的经度 可为空
	private Date last_time; // 用户最近一次使用这个配送地址的时间
	private int default_flag; // 是不是默认的配送地址 1表示是 0表示否 默认是0

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public Date getLast_time() {
		return last_time;
	}

	public void setLast_time(Date last_time) {
		this.last_time = last_time;
	}

	public int getDefault_flag() {
		return default_flag;
	}

	public void setDefault_flag(int default_flag) {
		this.default_flag = default_flag;
	}

	public UserShippingAddress(int id, int userid, String username,
			String realname, int gender, String phone, String location,
			double lat, double lon, Date last_time, int default_flag) {
		super();
		this.id = id;
		this.userid = userid;
		this.username = username;
		this.realname = realname;
		this.gender = gender;
		this.phone = phone;
		this.location = location;
		this.lat = lat;
		this.lon = lon;
		this.last_time = last_time;
		this.default_flag = default_flag;
	}

	public UserShippingAddress() {
		super();
	}
	
	@Override
	public String toString() {
		return "UserShippingAddress [id=" + id + ", userid=" + userid
				+ ", username=" + username + ", realname=" + realname
				+ ", gender=" + gender + ", phone=" + phone + ", location="
				+ location + ", lat=" + lat + ", lon=" + lon + ", last_time="
				+ last_time + ", default_flag=" + default_flag + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + default_flag;
		result = prime * result + gender;
		result = prime * result + id;
		result = prime * result
				+ ((last_time == null) ? 0 : last_time.hashCode());
		long temp;
		temp = Double.doubleToLongBits(lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		temp = Double.doubleToLongBits(lon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result
				+ ((realname == null) ? 0 : realname.hashCode());
		result = prime * result + userid;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserShippingAddress other = (UserShippingAddress) obj;
		if (default_flag != other.default_flag)
			return false;
		if (gender != other.gender)
			return false;
		if (id != other.id)
			return false;
		if (last_time == null) {
			if (other.last_time != null)
				return false;
		} else if (!last_time.equals(other.last_time))
			return false;
		if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (Double.doubleToLongBits(lon) != Double.doubleToLongBits(other.lon))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (realname == null) {
			if (other.realname != null)
				return false;
		} else if (!realname.equals(other.realname))
			return false;
		if (userid != other.userid)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
