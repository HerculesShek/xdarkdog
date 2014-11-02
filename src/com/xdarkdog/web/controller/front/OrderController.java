package com.xdarkdog.web.controller.front;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.spring.Inject;
import com.jfinal.plugin.spring.IocInterceptor;
import com.xdarkdog.config.Constants;
import com.xdarkdog.dao.OrderDao;
import com.xdarkdog.dao.OrderDetailDao;
import com.xdarkdog.dao.Order_InfoDao;
import com.xdarkdog.pojo.Order;
import com.xdarkdog.pojo.OrderDetail;
import com.xdarkdog.pojo.User;
import com.xdarkdog.web.controller.interceptor.Sessionful;
import com.xdarkdog.web.util.UUIDSeria;

@Sessionful
@Before(IocInterceptor.class)
public class OrderController extends Controller {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Inject.BY_TYPE
	private OrderDao orderDao;
	@Inject.BY_TYPE
	private Order_InfoDao orderInfoDao;

	public void index() {
		renderJsp("/pro/person/order/index.jsp");
	}

	public void confirm() {
		renderJsp("/pro/person/orderpay/confirm.jsp");
	}

	public void success() {
		renderJsp("/pro/person/orderpay/success.jsp");
	}

	public void list() {
		
		User user = getSessionAttr(Constants.SESSION_USER);
		
		Integer status = getParaToInt("status", 1);
		Integer pageNo = getParaToInt("pageNo", 1);
		Integer pageSize = getParaToInt("pageSize", 5);
		
		renderJson(orderInfoDao.getByPaging(user.getUsername(), pageNo, pageSize, status));
	}

	public void create() {
		User user = getSessionAttr(Constants.SESSION_USER);
		String order_type_str = getPara("order_type"); // 订单类型
		int order_type = Integer.parseInt(order_type_str);
		String commid_str = getPara("commid"); // 社区id
		int commid = Integer.parseInt(commid_str);
		String username = user.getUsername(); // 用户名 唯一的
		String addr_id = getPara("addr_id"); // 配送地址id
		int shipid = Integer.parseInt(addr_id);
		String ids = getPara("ids"); // 所有的水果id， 逗号隔开
		String counts = getPara("counts"); // 水果对应的数量，逗号隔开
		// TODO 后期加入水果的等级
		// String levels = getPara("levels"); // 水果对应的大小等级，逗号隔开
		String order_time = getPara("order_time"); // 预约配送时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date create_time = new Date(System.currentTimeMillis());
		Date subscribe_delivery_time = create_time;
		if (order_type == 2) {
			try {
				subscribe_delivery_time = sdf.parse(order_time);
			} catch (ParseException e) {
				logger.error("时间格式转化错误！ yyyy-MM-dd HH:mm " + order_time);
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
		affectaRows = orderDao.addOrder(order);
		if (affectaRows != 1) {
			renderJson(false);
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
				renderJson(false);
				return;
			}
		}
		renderJson(true);
	}
}
