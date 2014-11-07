package com.xdarkdog.web.util.data;

/**
 * 实际上是把OrderInformation封装的
 * 
 * @author Hercules
 * 
 */
public class OrderDetailData {
	// 水果的信息
	private String name = ""; // 水果名字
	private double price; // 在售价格
	private String measurement_type;
	private double fruit_count; // 水果的数量
	private int level = 2; // 水果的大小等级 1小 2中 3大

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getMeasurement_type() {
		return measurement_type;
	}

	public void setMeasurement_type(String measurement_type) {
		this.measurement_type = measurement_type;
	}

	public double getFruit_count() {
		return fruit_count;
	}

	public void setFruit_count(double fruit_count) {
		this.fruit_count = fruit_count;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "OrderDetailData [name=" + name + ", price=" + price
				+ ", measurement_type=" + measurement_type + ", fruit_count="
				+ fruit_count + ", level=" + level + "]";
	}

}
