package com.xdarkdog.manager;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.xdarkdog.dao.CommunityDao;
import com.xdarkdog.dao.FruitDao;
import com.xdarkdog.pojo.Community;
import com.xdarkdog.pojo.Fruit;
import com.xdarkdog.web.util.FormUtil;

// 水果管理的servlet，只负责水果信息的CRUD操作
public class FruitServlet extends HttpServlet {
	private static final long serialVersionUID = -1306142786329410670L;
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		String contentType = request.getContentType();
		if ("getFruitsByCommid".equalsIgnoreCase(method)) {
			getFruitsByCommid(request, response);
		} else if ("removeFruit".equalsIgnoreCase(method)) {
			removeFruit(request, response);
		} else if ("friutinfo".equalsIgnoreCase(method)) {
			friutinfo(request, response);
		} else if (contentType != null && contentType.contains("multipart/form-data")) { // 不是添加就是修改
			addOrModifyFruit(request, response);
		}
	}

	// 增加或者是修改水果的信息 
	private void addOrModifyFruit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FormUtil fu = new FormUtil();
		List<FileItem> items = fu.getFileList(request, "data_tmp");
		if (items == null || items.size() == 0) // 如果表单的数据是空的，直接跳转到显示页面
			request.getRequestDispatcher("/admin/fruits.jsp").forward(request, response);
		
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
		System.out.println("水果信息管理[add/modify]: " + method); // add modifyFruit
		
		// 根据方法名字做处理
		Fruit fruit = null;
		if (method.equalsIgnoreCase("add")) {
			fruit = (Fruit) fu.getInstanceByAdvanceForm(request, items, Fruit.class, "img/fruits");
			fruit.setValid_tag(1);
			new FruitDao().addFruit(fruit);
		} else if (method.equalsIgnoreCase("modifyFruit")) { // 修改一个水果的信息，有可能会有要删除的照片的信息
			if (photostodelete.length() > 0) { // 要删除一些照片
				// "http://localhost:8080/xdarkdog/img/1413805214235Koala.jpg,"
				System.out.println("要删除的照片是：" + photostodelete);
				// "/xdarkdog/img/1413805538709Koala.jpg,/xdarkdog/img/1413805538712Lighthouse.jpg"
				System.out.println("原来的照片是：" + originalphotos);
				// "C:\apache-tomcat-7.0.55\webapps\xdarkdog\"
				System.out.println("RealPath->" + request.getSession().getServletContext().getRealPath("/"));
			}
			// 更新水果信息
			// 1 删除一部分图片 并且从原来的照片的url里把这些照片的url删掉，只保留剩余的
			String orginalPhotos[] = originalphotos.split(",");
			if (photostodelete.length() > 0) {
				String p2delete[] = photostodelete.split(",");
				for (String p : p2delete) {
					// String p_postfix = p.substring(p.indexOf("xdarkdog") + "xdarkdog".length() + 1, p.length());
					String p_postfix = p.substring(p.indexOf("img") + "img".length() + 1, p.length());
					String fileRpath = request.getSession().getServletContext().getRealPath("/") +"img/"+ p_postfix.replace('/', File.separatorChar);
					File f = new File(fileRpath);
					if (f.exists()) {
						System.out.println("要删除的图片的物理路径是"+fileRpath);
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
			fruit = (Fruit) fu.getInstanceByAdvanceForm(request, items, Fruit.class, "img/fruits");
			if (orginalPhotos != null && orginalPhotos.length > 0) {
				for (String s : orginalPhotos) {
					if (s != null && !"".equalsIgnoreCase(s) && !"null".equalsIgnoreCase(s)) {
						String ps = fruit.getPhotos();
						if (fruit.getPhotos() == null || fruit.getPhotos().equalsIgnoreCase("")) {
							fruit.setPhotos(s);
						} else {
							fruit.setPhotos(ps + "," + s);
						}
					}
				}
			}
			System.out.println("修改后的水果信息:" + fruit);
			new FruitDao().modifyFruit(fruit);
		}
		// 转到这个水果所属于的水果店的水果显示页面
		System.out.println("fruit.getCommunityid()->"+fruit.getCommunityid());
		request.getRequestDispatcher("/servlet/fruit.do?method=getFruitsByCommid&id="+fruit.getCommunityid()).forward(request,response);
		// request.getRequestDispatcher("/servlet/comm.do?method=managerfruits&id=" + fruit.getCommunityid()).forward(request, response);
	}
	
	// 根据社区的id获取所有的水果的信息
	private void getFruitsByCommid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String comm_id = request.getParameter("id");
		System.out.println("getFruitsByCommid paramater id is "+comm_id);
		int cid = Integer.parseInt(comm_id);
		Community comm = new CommunityDao().getCommById(cid);
		List<Fruit> fruits = new FruitDao().getFruitsByCommId(cid);
		// 需要把社区的信息放进去，表明当前的水果管理是哪个社区的
		request.setAttribute("comm", comm);
		request.setAttribute("fruits", fruits);
		request.getRequestDispatcher("/admin/fruits.jsp").forward(request, response);
	}
	
	// 删除一个水果
	private void removeFruit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		FruitDao fruitDao = new FruitDao();
		if (idStr != null && idStr.length() > 0) {
			int id = Integer.parseInt(idStr);
			// 还要删除掉水果的照片
			// 首先删除图片 
			Fruit fruit = fruitDao.getFruitById(id);
			String f_photos_str = fruit.getPhotos();
			if (f_photos_str != null && f_photos_str.length() > 0) { // 删除照片
				String[] photo_s = f_photos_str.split(",");
				for (String s : photo_s) { // 删除社区的照片
					String p_postfix = s.substring(s.indexOf("xdarkdog") + "xdarkdog".length() + 1, s.length());
					String fileRpath = request.getSession().getServletContext().getRealPath("/") + p_postfix.replace('/', File.separatorChar);
					File f = new File(fileRpath);
					if (f.exists()) {
						System.out.println("删除照片：" + f.getAbsolutePath());
						f.delete();
					}
				}
			}
			fruitDao.removeFruitById(id);
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
			if (f != null) {
				request.setAttribute("fruit", f);
				request.getRequestDispatcher("/admin/fruitinfo.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/admin/manager.jsp").forward(request, response);
			}
		}
	}
	
}
