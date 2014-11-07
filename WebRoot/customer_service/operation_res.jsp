<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>订单操作结果</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  </head>

  <body>
	<%
		String res = (String) request.getAttribute("operation_res");
		if ("ok".equalsIgnoreCase(res)) {
			out.print("订单操作成功!");
		} else {
			out.print("订单操作失败!");
		}
		out.print(res);
	%>
	<a href="/customer_service/manager.jsp">返回客服主页面</a>
</body>
</html>
