package com.xdarkdog.web.controller.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.xdarkdog.config.Constants;
import com.xdarkdog.pojo.User;

public class SessionInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
	
	private static final Map<String, Boolean> INTERCEPT_MAPPING = new HashMap<String, Boolean>();

	@Override
	public void intercept(ActionInvocation ai) {

		Boolean shouldIntercept = false;
		String key = ai.getActionKey();
		
		if (!(INTERCEPT_MAPPING.containsKey(key))) {
			Controller controller = ai.getController();
			shouldIntercept = ai.getMethod().isAnnotationPresent(Sessionful.class) || controller.getClass().isAnnotationPresent(Sessionful.class);
			INTERCEPT_MAPPING.put(key, shouldIntercept);
		} else {
			shouldIntercept = INTERCEPT_MAPPING.get(key);
		}
		
		logger.info("ControllerKey[{}] shouldIntercept[{}]", key, shouldIntercept);
		if( shouldIntercept ) {
			Controller controller = ai.getController();
			User user = controller.getSessionAttr(Constants.SESSION_USER);
			if(null != user ) {
				ai.invoke();
			} else {
				String uri = controller.getRequest().getRequestURI();
				String queryStr = controller.getRequest().getQueryString();
				String request = uri;
				String redirectURL = ""; 
				if(StringUtils.isNotEmpty(queryStr)) {
					request += "?" + queryStr;
				}
				try {
					redirectURL = "?redirectURL=" + URLEncoder.encode(request, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage(), e);
				} catch(Exception e) {
					logger.error(e.getMessage(), e);
				}
				controller.redirect("/user/login" + redirectURL);
			}
		} else {
			ai.invoke();
		}
	}
	
}
