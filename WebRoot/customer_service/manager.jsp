<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>客服管理主页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
    <table align="center">
  	<tr><th align="center"><h3>嘿狗社区客服系统</h3></th></tr>
  	<tr><td><a href="/servlet/order.do?method=getUnauditedOrder">查看所有的未审核订单</a></td></tr>
  	<tr><td><a href="/servlet/order.do?method=getShippingOrders">查看正在配送的订单</a></td></tr>
  	<tr><td><a href="/servlet/order.do?method=getShippingOrders">查看预约的订单</a></td></tr>
  </table>
  </body>
</html>
