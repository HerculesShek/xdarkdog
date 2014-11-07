package com.xdarkdog;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.spring.SpringPlugin;
import com.jfinal.render.ViewType;
import com.xdarkdog.web.controller.front.CommunityController;
import com.xdarkdog.web.controller.front.FruitController;
import com.xdarkdog.web.controller.front.GPSController;
import com.xdarkdog.web.controller.front.IndexController;
import com.xdarkdog.web.controller.front.OrderController;
import com.xdarkdog.web.controller.front.ShippingControler;
import com.xdarkdog.web.controller.front.UserController;
import com.xdarkdog.web.controller.interceptor.ParamInterceptor;
import com.xdarkdog.web.controller.interceptor.SessionInterceptor;

public class JfinalConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants constants) {
		constants.setDevMode(true);
		constants.setViewType(ViewType.JSP);
	}

	@Override
	public void configHandler(Handlers handler) {
	}

	@Override
	public void configInterceptor(Interceptors interceptors) {
		interceptors.add(new SessionInterceptor());
		interceptors.add(new ParamInterceptor());
	}

	@Override
	public void configPlugin(Plugins plugins) {
		// 为了修复jfinal的文件路径在linux文件中系统中的bug
		if( "/".equals(System.getProperty("file.separator")) ) {
			plugins.add(new SpringPlugin("/" + PathKit.getWebRootPath() + "/WEB-INF/applicationContext.xml"));
		} else {
			plugins.add(new SpringPlugin(PathKit.getWebRootPath() + "/WEB-INF/applicationContext.xml"));
		}
	}

	@Override
	public void configRoute(Routes routes) {
		routes.add("/", IndexController.class);
		routes.add("/user", UserController.class);
		routes.add("/fruit", FruitController.class);
		routes.add("/order", OrderController.class);
		routes.add("/shipping", ShippingControler.class);
		routes.add("/gps", GPSController.class);
		routes.add("/community", CommunityController.class);
	}

}
