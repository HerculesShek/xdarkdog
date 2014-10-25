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
import com.xdarkdog.dao.FruitDao;
import com.xdarkdog.pojo.Community;
import com.xdarkdog.pojo.Fruit;

public class FruitServlet extends HttpServlet {
	private static final long serialVersionUID = 7093800319575515702L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("getFruitsByGPS".equalsIgnoreCase(method)) {
			getFruitsByGPS(request, response);
		} else if ("getFruitsByCommId".equalsIgnoreCase(method)) {
			getFruitsByCommId(request, response);
		}

	}
	
	// for AJAX
	// 根据用户的经纬度返回最近的水果店的所有水果的JSON信息
	public void getFruitsByGPS(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		System.out.println("lat is " + lat + ", lon is " + lon);
		List<Community> comms = new CommunityDao().getAllCommunitiesByKey(null);
		Community near_comm = comms.get(0);
		double min_distance = getDistance(lat, lon, near_comm.getLat(), near_comm.getLon());;
		for (int idx = 1; idx < comms.size(); idx++) {
			Community c = comms.get(idx);
			double lat2 = c.getLat();
			double lon2 = c.getLon();
			double distance = getDistance(lat, lon, lat2, lon2);
			if (distance < min_distance) {
				min_distance = distance;
				near_comm = c;
			}
		}
		System.out.println(near_comm);
		List<Fruit> fs = new FruitDao().getFruitsByCommId(near_comm.getId());
		String str = JSON.toJSONString(fs);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println(str);
		out.flush();
		out.close();
	}
	
	// for ajax
	// 根据社区id获取所有的水果
	public void getFruitsByCommId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int commid = Integer.parseInt(request.getParameter("commid"));
		List<Fruit> fs = new FruitDao().getFruitsByCommId(commid);
		String str = JSON.toJSONString(fs);
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println(str);
		out.flush();
		out.close();
	}
	
	private double getDistance(double lat1, double lon1, double lat2, double lon2){
		double res = (lat1-lat2)*(lat1-lat2)+(lon1-lon2)*(lon1-lon2);
		return Math.sqrt(res);
	}


}
