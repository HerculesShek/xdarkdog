package com.xdarkdog.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.xdarkdog.dao.ShippingAddressDao;
import com.xdarkdog.pojo.UserShippingAddress;

// "/servlet/shipaddr.do"
public class ShippingAddrServlet extends HttpServlet {
	private static final long serialVersionUID = 289635979230456288L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("addshipaddr".equalsIgnoreCase(method)) {
			addShipaddr(request, response);
		} else if ("getShipaddrsByUsername".equalsIgnoreCase(method)) {
			getShipaddrsByUsername(request, response);
		} else if ("removeshipaddr".equalsIgnoreCase(method)) {
			removeShipaddr(request, response);
		} else if ("modifyshipaddr".equalsIgnoreCase(method)) {
			modifyShipaddr(request, response);
		} else if ("setdefault".equalsIgnoreCase(method)) {
			setDefault(request, response);
		}
	}
	
	public void addShipaddr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String realname = request.getParameter("realname");
		int gender = Integer.parseInt(request.getParameter("gender"));
		String phone = request.getParameter("phone");
		String location = request.getParameter("location");
		
		UserShippingAddress addr = new UserShippingAddress();
		addr.setUsername(username);
		addr.setRealname(realname);
		addr.setGender(gender);
		addr.setPhone(phone);
		addr.setLocation(location);
		
		int res = new ShippingAddressDao().addUserShippingAddress(addr);
		
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (res == 1) {
			out.println("{\"success\":\"1\"}");
		} else {
			out.println("{\"success\":\"0\"}");
		}
		out.flush();
		out.close();
	}

	public void getShipaddrsByUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		List<UserShippingAddress> addrs = new ShippingAddressDao().getAddrsByUsername(username);
		// TODO 获取用户的所有配送地址之后返回什么？
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println(JSON.toJSONString(addrs));
		out.flush();
		out.close();
	}
	
	public void removeShipaddr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int addrid = Integer.parseInt(request.getParameter("addrid"));
		int res = new ShippingAddressDao().removeShippingAddr(addrid);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (res == 1) {
			out.println("{\"success\":\"1\"}");
		} else {
			out.println("{\"success\":\"0\"}");
		}
		out.flush();
		out.close();
	}
	
	// 根据id来修改一个配送地址的信息 包含 真实姓名 性别 电话 地址
	public void modifyShipaddr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int addrid = Integer.parseInt(request.getParameter("addrid"));
		String realname = request.getParameter("realname");
		int gender = Integer.parseInt(request.getParameter("gender"));
		String phone = request.getParameter("phone");
		String location = request.getParameter("location");
		
		UserShippingAddress addr = new UserShippingAddress();
		addr.setId(addrid);
		addr.setRealname(realname);
		addr.setGender(gender);
		addr.setPhone(phone);
		addr.setLocation(location);
		
		int res = new ShippingAddressDao().modifyShippingAddr(addr);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (res == 1) {
			out.println("{\"success\":\"1\"}");
		} else {
			out.println("{\"success\":\"0\"}");
		}
		out.flush();
		out.close();
	}
	
	public void setDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int addrid = Integer.parseInt(request.getParameter("addrid"));
		int res = new ShippingAddressDao().setDefault(addrid);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (res == 1) {
			out.println("{\"success\":\"1\"}");
		} else {
			out.println("{\"success\":\"0\"}");
		}
		out.flush();
		out.close();
	}
}
