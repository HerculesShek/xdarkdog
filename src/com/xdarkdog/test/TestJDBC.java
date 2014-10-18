package com.xdarkdog.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDBC {
	private static final String URL = "jdbc:mysql://121.40.154.64:3306/mysql";

	public static void main(String[] args) {
		try {
			// (1) 先加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// (2)创建并获取连接资源
			Connection conn = DriverManager.getConnection(URL, "root", "darkdog");
			// (3)发送 SQL 语句命令
			Statement stmt = conn.createStatement();
			// (4)执行或获取返回结果
			// 执行 增删改 的数据库案例
			String sql = "select `host`,`user` from user;";
			ResultSet res = stmt.executeQuery(sql);
			while (res.next()) {
				String h = res.getString(1);
				String user = res.getString(2);
				System.out.println(h + "\t" + user);
			}
			res.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
