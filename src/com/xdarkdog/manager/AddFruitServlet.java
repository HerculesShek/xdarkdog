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
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.xdarkdog.dao.FruitDao;
import com.xdarkdog.pojo.Fruit;


// TODO 这里一定要验证 前台保证数据的准确性和有效性！后台可以不必判断！
public class AddFruitServlet extends HttpServlet {
	private static final long serialVersionUID = 100L;
	// 添加水果
	private static final long MAX_SIZE = 30* 1024 * 1024; //文件上传的最大尺寸 
	private static final String filePath = "img";
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 磁盘工厂设置
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(20 * 1024 * 1024); // 设置缓冲区
		factory.setRepository(new File(request.getSession().getServletContext().getRealPath(filePath))); // 设置文件保存位置
		// 文件上传的工具类
		ServletFileUpload handler = new ServletFileUpload(factory);
		handler.setSizeMax(MAX_SIZE);// 设置文件上传的最大尺寸
		handler.setHeaderEncoding("utf-8"); // 设置头文件的编码方式。
		try {
			List<FileItem> files = handler.parseRequest(request);
			Iterator<FileItem> it = files.iterator();
			// 构建一个 Fruit 实例
			Fruit f = new Fruit();
			String small_pic_url = "", big_pic_url = "";
			while (it.hasNext()) {
				FileItem item = it.next();
				if (item.isFormField()) {
					// 获取普通控件名称
					String field_name = item.getFieldName();
					String field_value = item.getString("utf-8");
					System.out.println("field_name is " + field_name + ", and field_value is " + field_value);
					if ("name".equals(field_name)) {
						f.setName(field_value);
					} else if ("price".equals(field_name)) {
						if (field_value != null && !"".equals(field_value))
							f.setPrice(Double.parseDouble(field_value));
					} else if ("points".equals(field_name)) {
						if (field_value != null && !"".equals(field_value))
							f.setPoints(Integer.parseInt(field_value));
					} else if ("original_price".equals(field_name)) {
						if (field_value != null && !"".equals(field_value))
							f.setOriginal_price(Double.parseDouble(field_value));
					} else if ("display_price".equals(field_name)) {
						if (field_value != null && !"".equals(field_value))
							f.setDisplay_price(Double.parseDouble(field_value));
					} else if ("hot_tag".equals(field_name)) {
						if (field_value != null && !"".equals(field_value))
							f.setHot_tag(Integer.parseInt(field_value));
					} else if ("commend_tag".equals(field_name)) {
						if (field_value != null && !"".equals(field_value))
							f.setCommend_tag(Integer.parseInt(field_value));
					} else if ("remark".equals(field_name)) {
						f.setRemark(field_value);
					} else if ("introduce".equals(field_name)) {
						f.setIntroduce(field_value);
					} else if ("communityid".equals(field_name)){
						if (field_value != null && !"".equals(field_value))
							f.setCommunityid(Integer.parseInt(field_value));
					}
				} else {
					String field_name = item.getFieldName();
					System.out.println(field_name);
					String filename = item.getName();
					if (filename != null && !"".equals(filename)) {
						File filetoserver = new File(this.getServletContext().getRealPath(filePath), filename);
						item.write(filetoserver);
						// 生成一个存入数据库的路径 TODO 图片的路径要做一些处理，否则两个用户的图片名字一样就有问题
						String fileToDataBasePath = request.getContextPath()
								+ "/"
								+ filePath
								+ "/"
								+ filename.substring(filename.lastIndexOf(File.separator) + 1);
						if ("small_pic_url".equals(field_name)) {
							small_pic_url += fileToDataBasePath + ";";
						} else if ("big_pic_url".equals(field_name)) {
							big_pic_url += fileToDataBasePath + ";";
						}
					}
				}
			}
			if (small_pic_url.length() > 0)
				f.setPhotots(small_pic_url.substring(0, small_pic_url.lastIndexOf(';')));
			new FruitDao().addFruit(f);
			// 转到这个水果所属于的水果店的水果显示页面
			request.getRequestDispatcher("/servlet/comm.do?method=managerfruits&id="+f.getCommunityid()).forward(request,response);
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
