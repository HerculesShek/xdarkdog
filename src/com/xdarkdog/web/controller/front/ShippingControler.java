package com.xdarkdog.web.controller.front;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.spring.Inject;
import com.jfinal.plugin.spring.IocInterceptor;
import com.xdarkdog.config.Constants;
import com.xdarkdog.dao.ShippingAddressDao;
import com.xdarkdog.pojo.User;
import com.xdarkdog.pojo.UserShippingAddress;
import com.xdarkdog.web.controller.interceptor.Sessionful;

@Sessionful
@Before(IocInterceptor.class)
public class ShippingControler extends Controller {
	
	@Inject.BY_TYPE
	private ShippingAddressDao shippingAddressDao;

	public void index() {
		renderJsp("/pro/person/addr/index.jsp");
	}

	public void list() {
		User user = getSessionAttr(Constants.SESSION_USER);
		List<UserShippingAddress> addrs = shippingAddressDao.getAddrsByUsername(user.getUsername());
		renderJson(addrs);
	}

	public void create() {
		renderJsp("/pro/person/addr/create.jsp");
	}
	
	public void update() {
		Integer addrId = Integer.parseInt(getPara("addrId"));
		UserShippingAddress shippingAddress = shippingAddressDao.getAddrById(addrId);
		setAttr("shippingAddress", shippingAddress);
		renderJsp("/pro/person/addr/edit.jsp");
	}
	
	public void setDefault() {
		int addrId = getParaToInt("addrId");
		int res = shippingAddressDao.setDefault(addrId);
		if(1 == res) {
			renderJson(true);
		} else {
			renderJson(false);
		}
	}
	
	public void doCreate() {
		
		User user = getSessionAttr(Constants.SESSION_USER);
		
		String location  = getPara("location");
		String realname = getPara("realname");
		String phone = getPara("phone");
		Integer gender = Integer.parseInt(getPara("gender"));
		
		UserShippingAddress shippingAddress = new UserShippingAddress();
		shippingAddress.setLocation(location);
		shippingAddress.setRealname(realname);
		shippingAddress.setPhone(phone);
		shippingAddress.setGender(gender);
		shippingAddress.setUsername(user.getUsername());
		shippingAddress.setUserid(user.getId());
		
		int res = shippingAddressDao.addUserShippingAddress(shippingAddress);
		if( res == 1 ) {
			renderJson(true);
		} else {
			renderJson(false);
		}
	}
	
	public void doUpdate() {

		Integer addId = Integer.parseInt(getPara("addId"));
		String location  = getPara("location");
		String realname = getPara("realname");
		String phone = getPara("phone");
		Integer gender = Integer.parseInt(getPara("gender"));
		
		UserShippingAddress shippingAddress = new UserShippingAddress();
		shippingAddress.setId(addId);
		shippingAddress.setLocation(location);
		shippingAddress.setRealname(realname);
		shippingAddress.setPhone(phone);
		shippingAddress.setGender(gender);
		
		int res = shippingAddressDao.modifyShippingAddr(shippingAddress);
		if(res == 1 ) {
			renderJson(true);
		} else {
			renderJson(false);
		}
	}
	
	public void doDelete() {
		User user = getSessionAttr(Constants.SESSION_USER);
		Integer addrId = Integer.parseInt(getPara("addrId"));
		int res = shippingAddressDao.removeShippingAddr(user.getUsername(),addrId);
		
		// 获取当前用户的配送地址，当且仅当只有一个配送地址的时候将其设置为默认
		List<UserShippingAddress> addersses = shippingAddressDao.getAddrsByUsername(user.getUsername());
		
		if( addersses.size() == 1 ) {
			UserShippingAddress address = addersses.get(0);
			shippingAddressDao.setDefault(address.getId());
		}
		
		if (res == 1) {
			renderJson(true);
		} else {
			renderJson(false);
		}
	}
}
