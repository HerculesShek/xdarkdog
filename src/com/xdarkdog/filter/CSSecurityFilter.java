package com.xdarkdog.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CSSecurityFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("客服安全过滤器启动");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession();
		
		if(session.getAttribute("cs_name") != null ){
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse)response).sendRedirect("/customer_services_login_in.jsp");
		}
	}

	@Override
	public void destroy() {
		System.out.println("客服安全过滤器启动");
	}

}
