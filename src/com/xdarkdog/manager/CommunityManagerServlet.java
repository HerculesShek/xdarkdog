package com.xdarkdog.manager;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.xdarkdog.dao.CommunityDao;
import com.xdarkdog.pojo.Community;
import com.xdarkdog.web.util.FormUtil;
import com.xdarkdog.web.util.PageBean;

public class CommunityManagerServlet extends HttpServlet {
	private static final long serialVersionUID = -1751838613193050225L;
	public CommunityManagerServlet() {
		super();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		// System.out.println("method is " + method);
		// 如果是上传文件的话
		// contentType 的值是 "multipart/form-data; boundary=---------------------------144672491323635"
		// 否则 值就是 null
		String contentType = req.getContentType();
		System.out.println("getContentType is " + contentType);
		if (contentType != null && contentType.contains("multipart/form-data")) { // 不是添加就是修改
			addOrModifyComm(req, resp);
		} else if ("showcomm".equals(method)) {
			showcomm(req, resp);
		} else if ("modifyComm".equals(method)) {
			modifyComm(req, resp);
		} else if ("removecomm".equals(method)) {
			removecomm(req, resp);
		} else if ("managerfruits".equals(method)) {
			managerfruits(req, resp);
		} else if ("showcommsByPage".equals(method)) {
			showcommsByPage(req, resp);
		} else {
			showComms(req, resp);
		}
	}

	// 由于是社区的信息，会有照片信息的干预，所以单独拿出来处理
	private void addOrModifyComm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FormUtil fu = new FormUtil();
		List<FileItem> items = fu.getFileList(req, "data_tmp");
		if (items == null || items.size() == 0) // 如果表单的数据是空的，直接跳转到显示页面
			showComms(req, resp);
		
		Iterator<FileItem> it = items.iterator();
		// 首先判断是什么方法
		String method = "";
		while (it.hasNext()) {
			FileItem item = it.next();
			if (item.isFormField()) {
				String fieldName = item.getFieldName();
				if (fieldName.equalsIgnoreCase("method")) {
					method = item.getString("utf-8");
					break;
				}
			}
		}
		System.out.println("[add/modify]:"+method);
		// 根据方法名字做处理
		if (method.equalsIgnoreCase("addComm")) {
			Community comm = (Community)fu.getInstanceByAdvanceForm(req, items, Community.class, "img");
			new CommunityDao().addCommunity(comm);
			showComms(req, resp);
		} else if (method.equalsIgnoreCase("modifyComm")) {

		}

	}

	// 显示所有的社区
	private void showComms(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		CommunityDao dao = new CommunityDao();
		List<Community> comms = dao.getAllCommunitiesByKey(null);
		req.setAttribute("comms", comms);
		req.getRequestDispatcher("/manager/allComms.jsp").forward(req, resp);
	}

	// 显示一个社区的信息
	private void showcomm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		System.out.println("id is " + id);
		Community comm = new CommunityDao().getCommById(id);
		if (comm == null) {
			req.getRequestDispatcher("/servlet/comm.do?method=show").forward(req, resp);
		} else {
			req.setAttribute("comm", comm);
			req.getRequestDispatcher("/manager/comminfo.jsp").forward(req, resp);
		}
	}

	// 修改一个社区的信息 TODO
	private void modifyComm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");
		Community comm = new Community();
		int id = Integer.parseInt(req.getParameter("id"));
		comm.setId(id);
		//comm.setName(name);
		System.out.println("转义之后：" + name);
		comm.setLocation(req.getParameter("location"));
		String lonStr = req.getParameter("lon");
		// TODO 此处必须加入验证
		comm.setLon(Double.parseDouble(lonStr));
		String latStr = req.getParameter("lat");
		// TODO 此处必须加入验证
		comm.setLat(Double.parseDouble(latStr));
		//comm.setBig_pic_url(req.getParameter("big_pic_url"));

		CommunityDao commDao = new CommunityDao();
		commDao.modifyComm(comm);
		req.getRequestDispatcher("/servlet/comm.do?method=show").forward(req, resp);
	}
	
	// 删除一个社区的信息
	private void removecomm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String idStr = req.getParameter("id");
		System.out.println("要删除的社区的id is " + idStr);
		// TODO 
		int id = Integer.parseInt(idStr);
		new CommunityDao().removeCommById(id);
		req.getRequestDispatcher("/servlet/comm.do?method=show").forward(req, resp);
		// TODO 
	}
		
	// 获取一个社区中所有的水果
	private void managerfruits(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String comm_id = req.getParameter("id");
		req.getRequestDispatcher("/servlet/fruit.do?method=getFruitsByCommid&id="+comm_id).forward(req,resp);
	}

	// 获取一个社区中所有的水果
	private void showcommsByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int currentPage = 1;
		String currpage = req.getParameter("currpage");
		if(currpage != null){
			currentPage = Integer.parseInt(currpage);
		}
		int pagesize = 5;
		PageBean pb = new CommunityDao().getCommunitiesByPage(currentPage, pagesize);
		pb.setCurrentPage(currentPage);
		int totalPages = pb.getTotalRows()%pagesize==0?pb.getTotalRows()/pagesize:pb.getTotalRows()/pagesize+1;
		pb.setTotalPages(totalPages);
		req.setAttribute("pb", pb);
		req.getRequestDispatcher("/manager/showCommsByPage.jsp").forward(req, resp);
	}
}
