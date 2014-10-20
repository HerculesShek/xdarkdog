package com.xdarkdog.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class DaoSupport {

	// 三大核心接口
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// 四个方法
	// method1: 创建数据库的连接
	// TODO 异常的时候应该返回boolean类型
	private void getConntion() {
		try {
			// 1: 加载连接驱动，Java反射原理
			Class.forName(Config.CLASS_NAME);
			// 2:创建Connection接口对象，用于获取MySQL数据库的连接对象。三个参数：url连接字符串 账号 密码
			String url = Config.DATABASE_URL
					+ "://"
					+ Config.SERVER_IP
					+ ":"
					+ Config.SERVER_PORT
					+ "/"
					+ Config.DATABASE_SID
					+ "?autoReconnect=true&useUnicode=true&characterEncoding=utf-8";
			conn = DriverManager.getConnection(url, Config.USERNAME, Config.PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		getConntion();
		return conn;
	}

	// method2：关闭数据库的方法
	public void closeConn() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// method3: 专门用于发送增删改语句的方法
	public int execOther(final String strSQL, final Object[] params) {
		// 1、获取数据库连接
		getConntion();
		// 2、预先打印出即将执行的SQL语句(便于项目测试，仿Hibernate框架)
		System.out.println("SQL:> " + strSQL);
		try {
			// 3、创建Statement接口对象
			pstmt = conn.prepareStatement(strSQL);
			// 4、动态为pstmt对象赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			// 5、使用Statement对象发送SQL语句
			int affectedRows = pstmt.executeUpdate();
			// 6、返回结果
			return affectedRows;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			closeConn();
		}
	}

	// method4: 专门用于发送查询语句
	public ResultSet execQuery(final String strSQL, final Object[] params) {
		// 1、获取数据库连接
		getConntion();
		// 2、预先打印出即将执行的SQL语句(便于项目测试，仿Hibernate框架)
		System.out.println("SQL:> " + strSQL);
		try {
			// 3、创建PreparedStatement接口对象
			pstmt = conn.prepareStatement(strSQL);
			// 4、动态为pstmt对象赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			// 5、使用Statement对象发送SQL语句
			rs = pstmt.executeQuery();
			// 6、返回结果
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
// TODO 这里必须使用框架来解决资源没法释放的问题!!!!!
//		finally {
//			closeConn();
//		}
	}

	// 自动封装返回类型
	public List executeQuery(String sql, Class<?> clazz, Object... objects) {
		// 打印当前SQL语句。
		System.out.println("SQL:> " + sql);
		getConntion(); // 获得连接
		try {
			List list = new ArrayList(); // 声明容器
			pstmt = conn.prepareStatement(sql); 
			if (objects != null) {
				for (int j = 0; j < objects.length; j++) {
					pstmt.setObject(j + 1, objects[j]);
				}
			}

			rs = pstmt.executeQuery();
			// 获取模型
			ResultSetMetaData metaData = rs.getMetaData();

			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 实例化对象 相当于 new .
				Object object = clazz.newInstance(); // Emp emp = new Emp();

				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					// 让列名充当 Key 让得到的结果 充当Value 。
					// 这样 Key列就和Java类中的属性列是同名的 ， 那么Value 就会通过后面的方法赋值给该属性。
					map.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
				// 填充实例
				BeanUtils.populate(object, map); // Emp .... set
				list.add(object);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn();
		}
		return null;
	}

	// [a,b,c] => "a,b,c"
	public static String arrayToString(String[] param, String link) {

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < param.length; i++) {
			builder.append(param[i]).append(link);
		}

		builder.deleteCharAt(builder.lastIndexOf(link));

		return builder.toString();

	}

	// 将字符串数组转化成 int 数组
	public static int[] StringArrayToIntArray(String[] strArray) {
		int[] intArray = null;
		if (strArray != null && strArray.length > 0) {
			intArray = new int[strArray.length]; // 初始化 int[] 的长度
			for (int i = 0; i < intArray.length; i++) {
				intArray[i] = Integer.parseInt(strArray[i]);
			}
		}
		return intArray;
	}

	// 将int数组 转化成 Object[]

	public static Object[] IntArrayToObjectArray(int[] intArray) {
		Object[] objectArray = null;
		if (intArray != null && intArray.length > 0) {
			objectArray = new Object[intArray.length]; // 初始化 int[] 的长度
			for (int i = 0; i < objectArray.length; i++) {
				objectArray[i] = intArray[i];
			}
		}
		return objectArray;
	}

	// 将String数组 转化成 Object[]

	public static Object[] strArrayToObjectArray(String[] strArray) {
		Object[] objectArray = null;
		if (strArray != null && strArray.length > 0) {
			objectArray = new Object[strArray.length]; // 初始化 int[] 的长度
			for (int i = 0; i < objectArray.length; i++) {
				objectArray[i] = strArray[i];
			}
		}
		return objectArray;
	}

	// 生成问号 ?,?,?,?
	public static String getQuestionMark(String[] history) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < history.length; i++) {
			builder.append("?").append(",");
		}
		builder.deleteCharAt(builder.lastIndexOf(","));

		return builder.toString();
	}

	protected static String buildOrderby(LinkedHashMap<String, String> orderby) {

		// pid desc , name
		StringBuilder orderbyql = new StringBuilder("");
		if (orderby != null && orderby.size() > 0) {
			orderbyql.append(" order by "); // - order by pid desc
			for (String key : orderby.keySet()) {
				orderbyql.append(key).append(" ").append(orderby.get(key))
						.append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length() - 1);
		}
		return orderbyql.toString();
	}

	protected static void setQueryParams(PreparedStatement p, List queryParams) {
		try {
			if (queryParams != null && queryParams.size() > 0) {
				for (int i = 0; i < queryParams.size(); i++) {
					p.setObject(i + 1, queryParams.get(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
