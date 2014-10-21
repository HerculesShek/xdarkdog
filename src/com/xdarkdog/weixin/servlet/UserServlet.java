package com.xdarkdog.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xdarkdog.dao.UserDao;
import com.xdarkdog.pojo.User;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 2889320364114338060L;
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("register".equalsIgnoreCase(method)) { // 用户注册
			register(request, response);
		} else if ("login".equalsIgnoreCase(method)) { // 用户登录
			login(request, response);
		} else if ("checkName".equalsIgnoreCase(method)) { // 检查用户名是否存在
			checkName(request, response);
		} else if ("checkPhone".equalsIgnoreCase(method)) { // 检查电话是否存在
			checkPhone(request, response);
		}
	}

	// ajax
	// 成功的话返回1，否则返回0
	public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String tel = request.getParameter("tel");
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setPhone(tel);
		user.setRegistration_time(new Date(System.currentTimeMillis()));
		System.out.println("增加注册用户：" + user);
		int res = new UserDao().addUser(user);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(res == 1){
			out.println("{\"success\":\"1\"}");
		} else {
			out.println("{\"success\":\"0\"}");
		}
		out.flush();
		out.close();
	}

	// ajax
	// 
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String u_str = request.getParameter("username");
		String passwd = request.getParameter("password");
		UserDao dao = new UserDao();
		User u = dao.getUserByUserName(u_str, passwd);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (u != null) {
			out.println("{\"success\":\"1\"}");
		} else {
			u = dao.getUserByPhone(u_str, passwd);
			if (u != null) {
				out.println("{\"success\":\"1\"}");
			} else {
				out.println("{\"success\":\"0\"}");
			}
		}
		out.flush();
		out.close();
	}

	public void checkName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("username");
		User u = new UserDao().getUserByUserName(name, null);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (u != null) { // 用户名占用
			out.println("{\"success\":\"0\"}");
		} else {
			out.println("{\"success\":\"1\"}");
		}
	}

	public void checkPhone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phone = request.getParameter("phone");
		User u = new UserDao().getUserByPhone(phone, null);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (u != null) { // 用户名占用
			out.println("{\"success\":\"0\"}");
		} else {
			out.println("{\"success\":\"1\"}");
		}
	}
}
