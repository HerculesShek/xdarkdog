<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>管理界面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
  	<a href="/xdarkdog/manager/addcomm.jsp">添加社区</a><br>
    <a href="/xdarkdog/servlet/comm.do?method=show">社区列表</a><br>
    <a href="/xdarkdog/servlet/comm.do?method=showcommsByPage">社区分页列表</a><br>
  </body>
</html>
