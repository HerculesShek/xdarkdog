package com.xdarkdog.web.controller.front;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.spring.Inject;
import com.jfinal.plugin.spring.IocInterceptor;
import com.xdarkdog.dao.CommunityDao;

@Before(IocInterceptor.class)
public class CommunityController extends Controller {

	@Inject.BY_TYPE
	private CommunityDao communityDao;

	public void index() {
		renderJsp("/pro/community.jsp");
	}

	public void list() {
		renderJson(communityDao.getAllCommunitiesByKey(null));
	}
}
