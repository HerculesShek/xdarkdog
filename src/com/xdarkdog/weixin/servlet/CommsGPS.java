package com.xdarkdog.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.xdarkdog.dao.CommunityDao;
import com.xdarkdog.pojo.Community;

public class CommsGPS extends HttpServlet {

	private static final long serialVersionUID = -783106732452005706L;

	// 返回json格式的所有的社区信息
	// 用在当用户没有获取到gps信息的时候，列出所有社区的信息，让用户选择
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Community> comms = new CommunityDao().getAllCommunitiesByKey(null);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println(JSON.toJSONString(comms));
		out.flush();
		out.close();
	}

}
