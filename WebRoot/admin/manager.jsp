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
  <table align="center">
  	<tr><th align="center"><h3>嘿狗社区管理后台</h3></th></tr>
  	<tr><td><a href="/admin/addcomm.jsp">添加社区</a></td></tr>
  	<tr><td><a href="/servlet/comm.do?method=show">社区列表</a></td></tr>
  	<tr><td><a href="/servlet/comm.do?method=showcommsByPage">社区分页列表</a></td></tr>
  </table>
  </body>
</html>
