package com.xdarkdog.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = -8516907758118741896L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");

		if ("generate".equalsIgnoreCase(method)) {
			generateOrder(request, response);
		}
	}

	public void generateOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String order_type = request.getParameter("order_type");
		String commid = request.getParameter("commid"); // 社区id
		String username = request.getParameter("commid"); 
		String addr_id = request.getParameter("commid");
		String ids = request.getParameter("commid");
		String counts = request.getParameter("commid");
		String order_time = request.getParameter("commid");
		
		Date createTime = new Date(System.currentTimeMillis());
		String uuid = getUUID(username)
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	


}
