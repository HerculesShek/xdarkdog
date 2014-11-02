package com.xdarkdog.web.controller.front;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.spring.Inject;
import com.jfinal.plugin.spring.IocInterceptor;
import com.xdarkdog.config.Constants;
import com.xdarkdog.dao.UserDao;
import com.xdarkdog.pojo.User;
import com.xdarkdog.web.controller.interceptor.Sessionful;

@Before(IocInterceptor.class)
public class UserController extends Controller {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Inject.BY_TYPE
	private UserDao userDao;

	public void login() {
		String redirectURL = getPara("redirectURL");
		if( StringUtils.isNotEmpty(redirectURL) ) {
			setAttr("redirectURL", redirectURL);
		}
		renderJsp("/pro/login.jsp");
	}

	public void register() {
		renderJsp("/pro/register.jsp");
	}

	@Sessionful
	public void profile() {
		renderJsp("/pro/person/index.jsp");
	}

	public void doLogin() {

		String username = getPara("username");
		String password = getPara("password");

		User user = userDao.getUserByUserName(username, password);
		if (null == user) {
			user = userDao.getUserByPhone(username, password);
		}
		if (user != null) {
			setSessionAttr(Constants.SESSION_USER, user);
			renderJson(true);
		} else {
			renderJson(false);
		}
	}

	public void doRegister() {

		String username = getPara("username");
		String password = getPara("password");
		String tel = getPara("tel");

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setPhone(tel);
		user.setRegistration_time(new Date(System.currentTimeMillis()));
		logger.info("新注册用户[{}]", username);
		int res = userDao.addUser(user);
		if (res == 1) {
			renderJson(true);
		} else {
			renderJson(false);
		}
	}

	@Sessionful
	public void doLogout() {
		try {
			getSession().invalidate();
			renderJson(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			renderJson(false);
		}
	}
	
	@Sessionful
	public void modifypwd() {
		renderJsp("/pro/person/modifypwd.jsp");
	}

	@Sessionful
	public void doModifypwd() {
		User user = getSessionAttr(Constants.SESSION_USER);
		String username = user.getUsername();
		String oldPass = getPara("oldPass");
		String newPass = getPara("newPass");
		
		User _user = userDao.getUserByUserName(username, oldPass);
		JSONObject json = new JSONObject();
		if (null == _user) { // 老密码不对
			json.put("status", "-1");
			json.put("info", "旧密码错误");
		} else { // 密码正确
			user.setPassword(newPass);
			int res = userDao.changePasswd(user);
			if (res == 1) {// 修改成功
				json.put("status", 1);
			} else {// 修改失败
				json.put("status", 0);
			}
		}
		
		renderJson(json);
	}
	
	public void checkName() {
		String username = getPara("username");
		User user = userDao.getUserByUserName(username, null);
		if (user != null) { // 用户名占用
			renderJson(false);
		} else {
			renderJson(true);
		}
	}

	public void checkPhone() {
		String phone = getPara("phone");
		User user = userDao.getUserByPhone(phone, null);
		if (user != null) { // 用户名占用
			renderJson(false);
		} else {
			renderJson(true);
		}
	}
}
