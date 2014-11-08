package com.xdarkdog.manager.customer.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdarkdog.dao.OrderDao;
import com.xdarkdog.dao.OrderDetailDao;
import com.xdarkdog.dao.OrderInfoDao;
import com.xdarkdog.dao.OrderInformationDao;
import com.xdarkdog.pojo.Order;
import com.xdarkdog.pojo.OrderDetail;
import com.xdarkdog.web.util.OrderInfo;
import com.xdarkdog.web.util.OrderInformation;
import com.xdarkdog.web.util.UUIDSeria;
import com.xdarkdog.web.util.data.OrderData;
import com.xdarkdog.web.util.data.OrderDataUtil;


// "/servlet/csorder.do" 这个servlet只能客服系统访问！
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = -1178352384784036808L;
	private final Logger logger = LoggerFactory.getLogger(OrderServlet.class);

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("getOrdersByUsername".equalsIgnoreCase(method)) {
			getOrdersByUsername(request, response);
		} else if ("getUnauditedOrder".equalsIgnoreCase(method)) { // 查看所有的未审核订单
			getUnauditedOrder(request, response);
		} else if ("getShippingOrders".equalsIgnoreCase(method)) {
			getShippingOrders(request, response);
		} else if ("auditOrder".equalsIgnoreCase(method)) {
			auditOrder(request, response);
		} else if ("finishOrder".equalsIgnoreCase(method)) {
			finishOrder(request, response);
		} else if ("getSubscribeOrders".equalsIgnoreCase(method)) { // 预约订单
			getSubscribeOrders(request, response);
		} else if ("getOrderDataByOrderId".equalsIgnoreCase(method)) { // 预约订单
			getOrderDataByOrderId(request, response);
		}
	}

	// TODO 根据用户名获取用户所有的订单
	public void getOrdersByUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		
	}
	
	// TODO 获取所有的未审核的订单
	public void getUnauditedOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<OrderInformation> infos = new OrderInformationDao().getUnauditedOrderInfos();
		List<OrderData> datas = OrderDataUtil.parseOrderInformationToOrderData(infos);
		request.setAttribute("datas", datas);
		request.getRequestDispatcher("/customer_service/unaudited_orders.jsp").forward(request, response);
	}
		
	// TODO 根据订单号取得订单的详细信息
	public void getOrderDataByOrderId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderid = request.getParameter("orderid");
		List<OrderInformation> infos = new OrderInformationDao().getOrderInfoByOrderid(orderid);
		List<OrderData> datas = OrderDataUtil.parseOrderInformationToOrderData(infos);
		if(datas != null && datas.size()>0){
			request.setAttribute("info", datas.get(0));
		}
		String next = request.getParameter("next");
		if("audit".equalsIgnoreCase(next)){
		request.getRequestDispatcher("/customer_service/order_info_audit.jsp").forward(request, response);
		} else { // checkout
			request.getRequestDispatcher("/customer_service/order_info_checkout.jsp").forward(request, response);
		}
	}
	
	// TODO 获取所有的在配送的订单
	public void getShippingOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<OrderInformation> infos = new OrderInformationDao().getShippingOrderInfos();
		List<OrderData> datas = OrderDataUtil.parseOrderInformationToOrderData(infos);
		request.setAttribute("datas", datas);
		request.getRequestDispatcher("/customer_service/shipping_orders.jsp").forward(request, response);
	}

	//  审核订单 
	public void auditOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String res = request.getParameter("res");
		String orderid = request.getParameter("orderid");
		OrderDao odao = new OrderDao();
		int affectRows = 0;
		if ("1".equalsIgnoreCase(res)) { // 通过审核
			affectRows = odao.auditOrder(orderid, 3);
		} else if ("0".equalsIgnoreCase(res)) { // 未通过审核
			affectRows = odao.auditOrder(orderid, 2);
		}
		
		if(affectRows == 0){
			request.setAttribute("operation_res", "fail");
		} else if(affectRows == 1){
			request.setAttribute("operation_res", "ok");
		}
		request.getRequestDispatcher("/customer_service/operation_res.jsp").forward(request, response);
	}
	
	// TODO 查看预约订单
	public void getSubscribeOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	// 订单成功完成
	public void finishOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String order_id = request.getParameter("order_id"); // 订单id
		String cost = request.getParameter("totalcost"); // 此次账单的花费
		Order o = new Order();
		o.setOrder_id(order_id);
		o.setTotalcost(Double.parseDouble(cost));
		o.setStatus(4);
		o.setFinish_time(new Date(System.currentTimeMillis()));
		int affectRows = new OrderDao().finishOrder(o);
		if(affectRows == 0){
			request.setAttribute("operation_res", "fail");
		} else if(affectRows == 1){
			request.setAttribute("operation_res", "ok");
		}
		request.getRequestDispatcher("/customer_service/operation_res.jsp").forward(request, response);
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		List<OrderInfo> order_infos = new OrderInfoDao().getInfosByUsername("heihei");
		System.out.println(System.currentTimeMillis()-start);
		HashMap<String, JSONObject> infos = new LinkedHashMap<String, JSONObject>();
		for (OrderInfo i : order_infos) {
			JSONObject json_obj_detail = new JSONObject();
			json_obj_detail.put("name", i.getName());
			json_obj_detail.put("photos", i.getPhotos());
			json_obj_detail.put("fruit_count", i.getFruit_count());
			if (infos.containsKey(i.getOrder_id())) { // 已有 增加订单详情
				infos.get(i.getOrder_id()).getJSONArray("details").add(json_obj_detail);
			} else { // 还没有这个订单
				JSONObject json_obj = JSON.parseObject(JSON.toJSONString(i));
				json_obj.remove("name");
				json_obj.remove("photos");
				json_obj.remove("fruit_count");
				JSONArray arr = new JSONArray();
				arr.add(json_obj_detail);
				json_obj.put("details", arr);
				infos.put(i.getOrder_id(), json_obj);
			}
		}
		System.out.println(JSON.toJSONString(infos.values()));
		System.out.println(System.currentTimeMillis()-start);
	}
	
}
