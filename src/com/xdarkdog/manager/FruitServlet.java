package com.xdarkdog.manager;

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
	private static final long serialVersionUID = -1306142786329410670L;

	public FruitServlet() {
		super();
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("getFruitsByCommid".equalsIgnoreCase(method)) {
			getFruitsByCommid(request, response);
		}  else if ("modifyFruit".equalsIgnoreCase(method)) {
			modifyFruit(request, response);
		} else if ("removeFruit".equalsIgnoreCase(method)) {
			removeFruit(request, response);
		} else if ("friutinfo".equalsIgnoreCase(method)) {
			friutinfo(request, response);
		} else if ("getFruitsByGPS".equalsIgnoreCase(method)) {
			getFruitsByGPS(request, response);
		} else {
			
		}
	}

	// 根据社区的id获取所有的水果的信息
	private void getFruitsByCommid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String comm_id = request.getParameter("id");
		int cid = Integer.parseInt(comm_id);
		Community comm = new CommunityDao().getCommById(cid);
		List<Fruit> fruits = new FruitDao().getFruitsByCommId(cid);
		request.setAttribute("comm", comm);
		request.setAttribute("fruits", fruits);
		request.getRequestDispatcher("/manager/fruits.jsp").forward(request, response);
	}
	
	// 修改水果的属性
	private void modifyFruit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	// 删除一个水果
	private void removeFruit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		if (idStr != null && idStr.length() > 0) {
			int id = Integer.parseInt(idStr);
			new FruitDao().removeFruitById(id);
			String commid = request.getParameter("commid");
			request.getRequestDispatcher("/servlet/fruit.do?method=getFruitsByCommid&id="+commid).forward(request, response);
		}
	}
	
	// 获取水谷偶的详细信息
	private void friutinfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		if (idStr != null && idStr.length() > 0) {
			int id = Integer.parseInt(idStr);
			Fruit f = new FruitDao().getFruitById(id);
			if(f != null){
				request.setAttribute("fruit", f);
				request.getRequestDispatcher("/manager/fruitinfo.jsp").forward(request, response);
			} else {
				// TODO 没有这个水果则转到管理页面
				request.getRequestDispatcher("/manager/manager.jsp").forward(request, response);
			}
		}
	}
	
	// for AJAX
	// 根据用户的经纬度返回最近的水果店的所有水果的JSON信息
	private void getFruitsByGPS(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));

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
		response.setContentType("text/html");
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
