package com.xdarkdog.weixin.servlet;

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


// "/servlet/order.do"
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = -8516907758118741896L;
	private final Logger logger = LoggerFactory.getLogger(OrderServlet.class);

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("generate".equalsIgnoreCase(method)) {
			generateOrder(request, response);
		} else if ("getOrdersByUsername".equalsIgnoreCase(method)) {
			getOrdersByUsername(request, response);
		} else if ("getUnauditedOrder".equalsIgnoreCase(method)) { // 查看所有的未审核订单
			getUnauditedOrder(request, response);
		} else if ("cancelOrder".equalsIgnoreCase(method)) {
			cancelOrder(request, response);
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
				logger.error("时间格式转化错误！ yyyy-MM-dd HH:mm " + order_time);
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
		String[] levelarr = levels.split(",");
		OrderDetailDao detailDao = new OrderDetailDao();
		for (int idx = 0; idx < idarr.length; idx++) {
			int id = Integer.parseInt(idarr[idx]);
			double count = Double.parseDouble(countarr[idx]);
			OrderDetail detail = new OrderDetail();
			detail.setOrder_id(order_id);
			detail.setFruit_id(id);
			detail.setFruit_count(count);
			//detail.setLevel(level);
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
		List<OrderInfo> order_infos = new OrderInfoDao().getInfosByUsername(username);
		
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
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println(JSON.toJSONString(infos.values()));
		out.flush();
		out.close();
	}
	
	// TODO 获取所有的未审核的订单
	public void getUnauditedOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<OrderInformation> infos = new OrderInformationDao().getUnauditedOrderInfos();
		List<OrderData> datas = OrderDataUtil.parseOrderInformationToOrderData(infos);
		request.setAttribute("datas", datas);
		request.getRequestDispatcher("/customer_service/unaudited_orders.jsp").forward(request, response);
	}
		
	// TODO 取消订单
	public void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	// TODO 根据订单号取得订单的详细信息
	public void getOrderDataByOrderId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderid = request.getParameter("orderid");
		OrderInformation info = new OrderInformationDao().getOrderInfoByOrderid(orderid);
		List<OrderInformation> infos = new ArrayList<OrderInformation>();
		infos.add(info);
		List<OrderData> datas = OrderDataUtil.parseOrderInformationToOrderData(infos);
		if(datas != null && datas.size()>0){
			request.setAttribute("info", datas.get(0));
		}
		request.getRequestDispatcher("/customer_service/order_info.jsp").forward(request, response);
	}
	
	// TODO 获取所有的在配送的订单
	public void getShippingOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		String cost = request.getParameter("cost"); // 此次账单的花费
		Order o = new Order();
		o.setOrder_id(order_id);
		o.setTotalcost(Double.parseDouble(cost));
		o.setStatus(4);
		int res = new OrderDao().finishOrder(o);
		// TODO 客服的界面 完成之后应该跳转还是使用ajax
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
