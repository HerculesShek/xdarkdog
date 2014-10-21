package com.xdarkdog.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.xdarkdog.dao.FruitDao;
import com.xdarkdog.pojo.Fruit;
import com.xdarkdog.web.util.FormUtil;

// TODO 这里一定要验证 前台保证数据的准确性和有效性！后台可以不必判断！
public class AddFruitServlet extends HttpServlet {
	private static final long serialVersionUID = 4847877907309140750L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FormUtil fu = new FormUtil();
		List<FileItem> files = fu.getFileList(request, "img/fruits");
		Fruit f = (Fruit) fu.getInstanceByAdvanceForm(request, files, Fruit.class, "img/fruits");
		System.out.println("要添加的水果信息:" + f);
		new FruitDao().addFruit(f);
		// 转到这个水果所属于的水果店的水果显示页面
		request.getRequestDispatcher("/servlet/comm.do?method=managerfruits&id=" + f.getCommunityid()).forward(request, response);
	}
}
