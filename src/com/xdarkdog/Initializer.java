package com.xdarkdog;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.xdarkdog.config.Constants;

/**
 * 工程初始化器：因为工程是web工程，因此继承至HttpServlet 1. 全局配置设置，在jsp页面中通过jstl获取
 * 
 * @author hzr
 * 
 */
public class Initializer extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {

		ServletContext context = config.getServletContext();

		// 设置全局配置
		context.setAttribute(Constants.ORDER_PHONE, "057128975698");
	}

}
