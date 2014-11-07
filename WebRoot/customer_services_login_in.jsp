<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>客服系统登陆</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
  <form action="/servlet/cssign.do" method="post">
	<table align="center">
		<tr><th colspan="2" align="center"><h3>嘿狗社区客服登陆</h3></th></tr>
		<tr>
		<td>用户名:</td>
		<td><input type="text" name="username"/></td>
		</tr>
		<tr>
		<td>密码:</td>
		<td><input type="password" name="pwd"/></td>
		</tr>
		<tr><td colspan="2" align="center"><input type="submit" value="sign in" /></td></tr>
	</table>
  </form>
  </body>
</html>
