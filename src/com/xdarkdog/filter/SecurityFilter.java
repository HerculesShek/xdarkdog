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

public class SecurityFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("安全过滤器启动");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession();
		System.out.println("安全过滤器中获取的sessionid是:"+session.getId());
		System.out.println("安全过滤器中获取的session中的user是:"+session.getAttribute("user"));
		if("ok".equals(session.getAttribute("user"))){
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse)response).sendRedirect("/manager_sign_in.jsp");
		}
	}

	@Override
	public void destroy() {
		System.out.println("安全过滤器销毁");
	}

}
