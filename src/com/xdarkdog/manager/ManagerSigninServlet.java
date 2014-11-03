package com.xdarkdog.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManagerSigninServlet extends HttpServlet {

	private static final long serialVersionUID = 200L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		if ("admin".equals(username) && "dark_X512.com".equals(pwd)) {
			HttpSession session = request.getSession();
			System.out.println("管理员登陆 sign session id is " + session.getId());
			session.setAttribute("user", "ok");
			request.getRequestDispatcher("/admin/manager.jsp").forward(request, response);
		} else {
			response.sendRedirect("/manager_sign_in.jsp");
		}
	}

}
