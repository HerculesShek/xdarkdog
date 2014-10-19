package com.xdarkdog.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xdarkdog.dao.CommunityDao;
import com.xdarkdog.dao.FruitDao;
import com.xdarkdog.pojo.Community;
import com.xdarkdog.pojo.Fruit;

// 水果管理的servlet，只负责水果信息的CRUD操作
public class FruitServlet extends HttpServlet {
	private static final long serialVersionUID = -1306142786329410670L;
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("getFruitsByCommid".equalsIgnoreCase(method)) {
			getFruitsByCommid(request, response);
		} else if ("modifyFruit".equalsIgnoreCase(method)) {
			modifyFruit(request, response);
		} else if ("removeFruit".equalsIgnoreCase(method)) {
			removeFruit(request, response);
		} else if ("friutinfo".equalsIgnoreCase(method)) {
			friutinfo(request, response);
		} else {

		}
	}

	// 根据社区的id获取所有的水果的信息
	private void getFruitsByCommid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String comm_id = request.getParameter("id");
		int cid = Integer.parseInt(comm_id);
		Community comm = new CommunityDao().getCommById(cid);
		List<Fruit> fruits = new FruitDao().getFruitsByCommId(cid);
		request.setAttribute("comm", comm);
		request.setAttribute("fruits", fruits);
		request.getRequestDispatcher("/manager/fruits.jsp").forward(request, response);
	}
	
	// 修改水果的属性
	private void modifyFruit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
	
}
