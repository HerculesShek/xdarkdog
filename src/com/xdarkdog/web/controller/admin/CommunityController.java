package com.xdarkdog.web.controller.admin;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.spring.Inject;
import com.jfinal.plugin.spring.IocInterceptor;
import com.xdarkdog.dao.CommunityDao;
import com.xdarkdog.pojo.Community;

@Before(IocInterceptor.class)
public class CommunityController extends Controller {

	@Inject.BY_TYPE
	private CommunityDao communityDao;
	
	public void get() {

		Integer id = getParaToInt("id");
		Community community = communityDao.getCommById(id);
		renderJson(community);
	}

	
}
