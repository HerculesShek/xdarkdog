package com.xdarkdog.manager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.alibaba.fastjson.JSON;
import com.xdarkdog.dao.CommunityDao;
import com.xdarkdog.dao.FruitDao;
import com.xdarkdog.pojo.Community;
import com.xdarkdog.pojo.Fruit;
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
		// 首先判断是什么方法 要删除的照片 原来的照片
		String method = "", photostodelete = "", originalphotos = "";
		while (it.hasNext()) {
			FileItem item = it.next();
			if (item.isFormField()) {
				String fieldName = item.getFieldName();
				if (fieldName.equalsIgnoreCase("method")) {
					method = item.getString("utf-8");
				}
				if (fieldName.equalsIgnoreCase("photostodelete")) {
					photostodelete = item.getString("utf-8");
				}
				if (fieldName.equalsIgnoreCase("originalphotos")) {
					originalphotos = item.getString("utf-8");
				}
			}
		}
		System.out.println("社区信息管理[add/modify]: " + method);
		// 根据方法名字做处理
		if (method.equalsIgnoreCase("addComm")) {
			Community comm = (Community)fu.getInstanceByAdvanceForm(req, items, Community.class, "img/community");
			new CommunityDao().addCommunity(comm);
			showComms(req, resp);           
		} else if (method.equalsIgnoreCase("modifyComm")) { // 修改一个社区的信息，有可能会有要删除的照片的信息
			if (photostodelete.length() > 0) { // 要删除一些照片
				// "http://localhost:8080/xdarkdog/img/1413805214235Koala.jpg,"
				System.out.println("要删除的照片是：" + photostodelete);
				// "/xdarkdog/img/1413805538709Koala.jpg,/xdarkdog/img/1413805538712Lighthouse.jpg"
				System.out.println("原来的照片是：" + originalphotos);
				// "C:\apache-tomcat-7.0.55\webapps\xdarkdog\"
				System.out.println("RealPath->"+req.getServletContext().getRealPath("/"));
			}
			// 更新社区信息 
			// 1 删除一部分图片 并且从原来的照片的url里把这些照片的url删掉，只保留剩余的
			String orginalPhotos[] = originalphotos.split(",");
			if (photostodelete.length() > 0) {
				String p2delete[] = photostodelete.split(",");
				for (String p : p2delete) {
					String p_postfix = p.substring(p.indexOf("xdarkdog") + "xdarkdog".length() + 1, p.length());
					String fileRpath = req.getServletContext().getRealPath("/")+p_postfix.replace('/', File.separatorChar);
					File f = new File(fileRpath);
					if (f.exists()) {
						f.delete();
					}
					// 把这个图片的url从原来的url里删除
					for (int i = 0; i < orginalPhotos.length; i++) {
						if (orginalPhotos[i].contains(p_postfix)) {
							orginalPhotos[i] = "";
						}
					}
				}
			}
			// 2 重组comm的photos属性，更新数据库
			Community comm = (Community) fu.getInstanceByAdvanceForm(req, items, Community.class, "img/community");
			if (orginalPhotos != null && orginalPhotos.length > 0) {
				for (String s : orginalPhotos) {
					if (s != null && !"".equalsIgnoreCase(s) && !"null".equalsIgnoreCase(s)) {
						String ps = comm.getPhotos();
						if (comm.getPhotos() == null) {
							comm.setPhotos(s);
						} else {
							comm.setPhotos(ps + "," + s);
						}
					}
				}
			}
			System.out.println("社区信息:"+comm);
			new CommunityDao().modifyComm(comm);
			showComms(req, resp);
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

	// 删除一个社区的信息
	private void removecomm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idStr = req.getParameter("id");
		System.out.println("要删除的社区的id is " + idStr);
		// 删除图片 删除社区下面所有的水果！
		CommunityDao commDao = new CommunityDao();
		int id = Integer.parseInt(idStr);
		Community comm = commDao.getCommById(id);
		String photos_str = comm.getPhotos();
		System.out.println("photos_str->"+photos_str);
		if (photos_str != null && photos_str.length() > 0) { // 删除照片
			String[] photo_s = photos_str.split(",");
			for (String s : photo_s) { // 删除社区的照片
				String p_postfix = s.substring(s.indexOf("xdarkdog") + "xdarkdog".length() + 1, s.length());
				String fileRpath = req.getServletContext().getRealPath("/") + p_postfix.replace('/', File.separatorChar);
				File f = new File(fileRpath);
				if (f.exists()) {
					System.out.println("删除照片：" + f.getAbsolutePath());
					f.delete();
				}
			}
		}
		// 删除这个社区
		commDao.removeCommById(id);
		// 删除掉社区下面的所有的水果 不能简单的这么把水果信息给删掉 还有水果的照片必须也得删掉
		FruitDao fruitDao = new FruitDao();
		List<Fruit> fruits = fruitDao.getFruitsByCommId(id);
		for (Fruit f : fruits) {
			String f_photos_str = f.getPhotos();
			System.out.println("f_photos_str->"+f_photos_str);
			if (f_photos_str != null && f_photos_str.length() > 0) { // 删除照片
				String[] f_photo_s = f_photos_str.split(",");
				for (String s : f_photo_s) { // 删除社区的照片
					String p_postfix = s.substring(s.indexOf("xdarkdog") + "xdarkdog".length() + 1, s.length());
					String fileRpath = req.getServletContext().getRealPath("/") + p_postfix.replace('/', File.separatorChar);
					File img_file = new File(fileRpath);
					if (img_file.exists()) {
						System.out.println("删除照片：" + img_file.getAbsolutePath());
						img_file.delete();
					}
				}
			}
		}
		fruitDao.removeFruitsByCommId(id);
		req.getRequestDispatcher("/servlet/comm.do?method=show").forward(req, resp);
	}
		
	// 获取一个社区中所有的水果
	private void managerfruits(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
