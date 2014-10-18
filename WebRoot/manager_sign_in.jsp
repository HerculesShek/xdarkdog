<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>嘿狗社区管理登陆界面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  <body>
    <form action="/xdarkdog/servlet/signin.do" method="post">
    	用户名：<input type="text" name="username"/><br>
    	密码：<input type="password" name="pwd"/><br>
    	<input type="submit" value="sign in" />
    </form>
  </body>
</html>
