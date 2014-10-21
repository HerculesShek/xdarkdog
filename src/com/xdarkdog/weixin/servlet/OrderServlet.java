package com.xdarkdog.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xdarkdog.dao.OrderDao;
import com.xdarkdog.dao.OrderDetailDao;
import com.xdarkdog.pojo.Order;
import com.xdarkdog.pojo.OrderDetail;
import com.xdarkdog.web.util.UUIDSeria;


// "/servlet/order.do"
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = -8516907758118741896L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("generate".equalsIgnoreCase(method)) {
			generateOrder(request, response);
		}
	}

	public void generateOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String order_type_str = request.getParameter("order_type");
		int order_type = Integer.parseInt(order_type_str);
		String commid_str = request.getParameter("commid"); // 社区id
		int commid = Integer.parseInt(commid_str);
		String username = request.getParameter("username");
		String addr_id = request.getParameter("addr_id");
		int shipid = Integer.parseInt(addr_id);
		String ids = request.getParameter("ids");
		String counts = request.getParameter("counts");
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
			int count = Integer.parseInt(countarr[idx]);
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
	
}
