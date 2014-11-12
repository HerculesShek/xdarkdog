package com.xdarkdog.web.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.spring.Inject;
import com.jfinal.plugin.spring.IocInterceptor;
import com.xdarkdog.dao.OrderDao;
import com.xdarkdog.pojo.Order;

@Before(IocInterceptor.class)
public class OrderContrller extends Controller {

	private static final Logger logger = LoggerFactory.getLogger(OrderContrller.class);

	@Inject.BY_TYPE
	private OrderDao orderDao;
	
	public void index() {

		Integer pageSize = getParaToInt("pageSize");
		Integer pageNo = getParaToInt("pageNo");
		Integer status = getParaToInt("status");

		List<Order> orders = orderDao.getByPaging(pageNo, pageSize, status);
		setAttr("orders", orders);
		
		renderJsp("/");
	}

}
