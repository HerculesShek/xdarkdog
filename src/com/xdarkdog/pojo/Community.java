package com.xdarkdog.pojo;

/**
 * 表示一个社区，实际是一个水果店
 * 
 * @author Hercules
 */
public class Community {
	private int id;
	private String fruit_shop_name;  // 水果店名字
	private String comm_name;        // 社区名字
	private String fruit_shop_owner; // 水果店店主名字
	private String owner_phone;      // 店主电话
	private String location;         // 街道地址
	private double lat;              // 纬度
	private double lon;              // 经度
	private String photos;           // 照片的地址

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getFruit_shop_name() {
		return fruit_shop_name;
	}

	public void setFruit_shop_name(String fruit_shop_name) {
		this.fruit_shop_name = fruit_shop_name;
	}

	public String getComm_name() {
		return comm_name;
	}

	public void setComm_name(String comm_name) {
		this.comm_name = comm_name;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getFruit_shop_owner() {
		return fruit_shop_owner;
	}

	public void setFruit_shop_owner(String fruit_shop_owner) {
		this.fruit_shop_owner = fruit_shop_owner;
	}

	public String getOwner_phone() {
		return owner_phone;
	}

	public void setOwner_phone(String owner_phone) {
		this.owner_phone = owner_phone;
	}

	public Community() {
		super();
	}

	public Community(int id, String fruit_shop_name, String comm_name,
			String location, String fruit_shop_owner, String owner_phone,
			double lat, double lon, String photos) {
		super();
		this.id = id;
		this.fruit_shop_name = fruit_shop_name;
		this.comm_name = comm_name;
		this.location = location;
		this.fruit_shop_owner = fruit_shop_owner;
		this.owner_phone = owner_phone;
		this.lat = lat;
		this.lon = lon;
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "Community [id=" + id + ", fruit_shop_name=" + fruit_shop_name
				+ ", comm_name=" + comm_name + ", location=" + location
				+ ", fruit_shop_owner=" + fruit_shop_owner + ", owner_phone="
				+ owner_phone + ", lat=" + lat + ", lon=" + lon + ", photos="
				+ photos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((comm_name == null) ? 0 : comm_name.hashCode());
		result = prime * result
				+ ((fruit_shop_name == null) ? 0 : fruit_shop_name.hashCode());
		result = prime
				* result
				+ ((fruit_shop_owner == null) ? 0 : fruit_shop_owner.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		temp = Double.doubleToLongBits(lon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((owner_phone == null) ? 0 : owner_phone.hashCode());
		result = prime * result + ((photos == null) ? 0 : photos.hashCode());
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
		Community other = (Community) obj;
		if (comm_name == null) {
			if (other.comm_name != null)
				return false;
		} else if (!comm_name.equals(other.comm_name))
			return false;
		if (fruit_shop_name == null) {
			if (other.fruit_shop_name != null)
				return false;
		} else if (!fruit_shop_name.equals(other.fruit_shop_name))
			return false;
		if (fruit_shop_owner == null) {
			if (other.fruit_shop_owner != null)
				return false;
		} else if (!fruit_shop_owner.equals(other.fruit_shop_owner))
			return false;
		if (id != other.id)
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
		if (owner_phone == null) {
			if (other.owner_phone != null)
				return false;
		} else if (!owner_phone.equals(other.owner_phone))
			return false;
		if (photos == null) {
			if (other.photos != null)
				return false;
		} else if (!photos.equals(other.photos))
			return false;
		return true;
	}

}
