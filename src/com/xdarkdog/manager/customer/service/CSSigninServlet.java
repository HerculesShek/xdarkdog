package com.xdarkdog.manager.customer.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xdarkdog.dao.CustomerServiceDao;
import com.xdarkdog.pojo.CustomerService;

// 验证客服的登陆
public class CSSigninServlet extends HttpServlet {

	private static final long serialVersionUID = -9076360480663296265L;

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		CustomerService cs = new CustomerServiceDao().getCS(username, pwd);
		
		if (cs!=null) {
			HttpSession session = request.getSession();
			session.setAttribute("cs_name", cs.getUsername());
			request.getRequestDispatcher("/customer_service/manager.jsp").forward(request, response);
		} else {
			response.sendRedirect("/customer_services_login_in.jsp");
		}
	}

}
