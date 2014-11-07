package com.xdarkdog.web.controller.interceptor;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**
 * <pre>
 * 负责对请求的参数进行拦截，作如下的工作： 
 * 1. 将param中的参数重新设置到attribute中，可以做到参数穿透 
 * 2. 参数合法性做通用的验证
 * </pre>
 * 
 * @author hzr
 */
public class ParamInterceptor implements Interceptor {

	/* 需要穿透的参数名称集合 */
	private static final Set<String> THROUGH_PARAMS = new HashSet<String>();

	static {
		THROUGH_PARAMS.add("fromUrl");
	}

	@Override
	public void intercept(ActionInvocation ai) {

		// 1. 参数穿透
		Controller controller = ai.getController();
		for (String thoughParam : THROUGH_PARAMS) {
			String param = controller.getPara(thoughParam);
			if (StringUtils.isNotEmpty(param)) {
				controller.setAttr(thoughParam, param.trim());
			}
		}
		
		ai.invoke();
	}
}
