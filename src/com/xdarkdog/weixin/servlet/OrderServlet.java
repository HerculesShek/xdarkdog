package com.xdarkdog.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.xdarkdog.dao.CommunityDao;
import com.xdarkdog.dao.FruitDao;
import com.xdarkdog.dao.OrderDao;
import com.xdarkdog.dao.OrderDetailDao;
import com.xdarkdog.dao.ShippingAddressDao;
import com.xdarkdog.dao.UserDao;
import com.xdarkdog.pojo.Order;
import com.xdarkdog.pojo.OrderDetail;
import com.xdarkdog.web.util.OrderInfo;
import com.xdarkdog.web.util.DetailInfo;
import com.xdarkdog.web.util.UUIDSeria;


// "/servlet/order.do"
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = -8516907758118741896L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("generate".equalsIgnoreCase(method)) {
			generateOrder(request, response);
		} else if ("getOrdersByUsername".equalsIgnoreCase(method)) {
			getOrdersByUsername(request, response);
		} else if ("getUnauditedOrder".equalsIgnoreCase(method)) {
			getUnauditedOrder(request, response);
		} else if ("cancelOrder".equalsIgnoreCase(method)) {
			cancelOrder(request, response);
		} else if ("getShippingOrders".equalsIgnoreCase(method)) {
			getShippingOrders(request, response);
		} else if ("auditOrder".equalsIgnoreCase(method)) {
			auditOrder(request, response);
		} else if ("finishOrder".equalsIgnoreCase(method)) {
			finishOrder(request, response);
		}
	}

	// 生成一份订单
	public void generateOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String order_type_str = request.getParameter("order_type"); // 订单类型
		int order_type = Integer.parseInt(order_type_str);
		String commid_str = request.getParameter("commid"); // 社区id
		int commid = Integer.parseInt(commid_str); 
		String username = request.getParameter("username"); // 用户名 唯一的
		String addr_id = request.getParameter("addr_id"); // 配送地址id
		int shipid = Integer.parseInt(addr_id);
		String ids = request.getParameter("ids"); // 所有的水果id， 逗号隔开
		String counts = request.getParameter("counts"); // 水果对应的数量，逗号隔开
		// TODO 后期加入水果的等级
		String levels = request.getParameter("levels"); // 水果对应的大小等级，逗号隔开
		String order_time = request.getParameter("order_time"); // 预约配送时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date create_time = new Date(System.currentTimeMillis());
		Date subscribe_delivery_time = create_time;
		if (order_type == 2) {
			try {
				subscribe_delivery_time = sdf.parse(order_time);
			} catch (ParseException e) {
				System.out.println("时间格式转化错误！ yyyy-MM-dd HH:mm " + order_time);
				e.printStackTrace();
			}
		}
		int affectaRows = 0;
		String order_id = UUIDSeria.getUUID(username);
		// 创建订单和订单详情
		Order order = new Order();
		order.setOrder_id(order_id);
		order.setOrder_type(order_type);
		order.setUsername(username);
		order.setCommid(commid);
		order.setShipid(shipid);
		order.setSubscribe_delivery_time(subscribe_delivery_time);
		order.setCreate_time(create_time);
		// 订单入库
		affectaRows = new OrderDao().addOrder(order);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (affectaRows != 1) {
			out.println("{\"success\":\"0\"}");
			out.flush();
			out.close();
			return;
		}
		
		// 把订单的详细信息存入数据库 
		String[] idarr = ids.split(",");
		String[] countarr = counts.split(",");
		OrderDetailDao detailDao = new OrderDetailDao();
		for (int idx = 0; idx < idarr.length; idx++) {
			int id = Integer.parseInt(idarr[idx]);
			double count = Double.parseDouble(countarr[idx]);
			OrderDetail detail = new OrderDetail();
			detail.setOrder_id(order_id);
			detail.setFruit_id(id);
			detail.setFruit_count(count);
			affectaRows = detailDao.addOrderDatail(detail);
			if (affectaRows != 1) {
				out.println("{\"success\":\"0\"}");
				out.flush();
				out.close();
				return;
			}
		}
		out.println("{\"success\":\"1\"}");
		out.flush();
		out.close();
	}

	// 根据用户名获取用户所有的订单
	public void getOrdersByUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		OrderDao odao = new OrderDao();
		// 获取这个用户所有的订单
		List<Order> orders = odao.getOrderByUsername(username);
		// prepare dao
		List<OrderInfo> order_infos = new ArrayList<OrderInfo>();
		UserDao user_dao = new UserDao();
		CommunityDao comm_dao = new CommunityDao();
		ShippingAddressDao ship_addr_dao = new ShippingAddressDao();
		OrderDetailDao detail_dao = new OrderDetailDao();
		FruitDao fruit_dao = new FruitDao();
		for (Order order : orders) {
			OrderInfo order_info = new OrderInfo();
			order_info.setOrder(order);
			order_info.setUser(user_dao.getUserByUserName(order.getUsername(), null));
			order_info.setCommunity(comm_dao.getCommById(order.getCommid()));
			order_info.setShipAddr(ship_addr_dao.getAddrById(order.getShipid()));
			List<OrderDetail> details = detail_dao.getDetailsByOrder_id(order.getOrder_id());
			List<DetailInfo> detail_infos = new ArrayList<DetailInfo>();
			for (OrderDetail order_detail : details) {
				DetailInfo detail_info = new DetailInfo();
				detail_info.setDetail(order_detail);
				detail_info.setFruit(fruit_dao.getFruitById(order_detail.getFruit_id()));
				detail_infos.add(detail_info);
			}
			order_info.setDetail_infos(detail_infos);
			order_infos.add(order_info);
		}
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println(JSON.toJSONString(order_infos));
		out.flush();
		out.close();
	}
	
	// TODO 获取所有的未审核的订单
	public void getUnauditedOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	}
		
	// TODO 取消订单
	public void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	}
	// TODO 获取所有的在配送的订单
	public void getShippingOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	// 审核订单
	public void auditOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	// 订单成功完成
	public void finishOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	public static void main(String[] args) {
		OrderDao odao = new OrderDao();
		List<Order> orders = odao.getOrderByUsername("heihei");
		List<OrderInfo> order_infos = new ArrayList<OrderInfo>();
		UserDao user_dao = new UserDao();
		CommunityDao comm_dao = new CommunityDao();
		ShippingAddressDao ship_addr_dao = new ShippingAddressDao();
		OrderDetailDao detail_dao = new OrderDetailDao();
		FruitDao fruit_dao = new FruitDao();
		for (Order order : orders) {
			OrderInfo order_info = new OrderInfo();
			order_info.setOrder(order);
			order_info.setUser(user_dao.getUserByUserName(order.getUsername(), null));
			order_info.setCommunity(comm_dao.getCommById(order.getCommid()));
			order_info.setShipAddr(ship_addr_dao.getAddrById(order.getShipid()));
			List<OrderDetail> details = detail_dao.getDetailsByOrder_id(order.getOrder_id());
			List<DetailInfo> detail_infos = new ArrayList<DetailInfo>();
			for(OrderDetail order_detail : details){
				DetailInfo detail_info = new DetailInfo();
				detail_info.setDetail(order_detail);
				detail_info.setFruit(fruit_dao.getFruitById(order_detail.getFruit_id()));
				detail_infos.add(detail_info);
			}
			order_info.setDetail_infos(detail_infos);
			order_infos.add(order_info);
		}
		System.out.println(JSON.toJSONString(order_infos));
	}
	
}
