<%@ page language="java" import="java.util.*,com.xdarkdog.pojo.Community" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改社区信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  <body>
  <%
  	Community comm = (Community)request.getAttribute("comm");
  %>
    <form action="/xdarkdog/servlet/comm.do" method="post">
    	<input type="hidden" name="method" value="modifyComm"><br>
    	<input type="hidden" name="id" value="<%=comm.getId()%>"><br>
    	社区水果店名字: <input type="text" name="name" value="<%=comm.getName()%>"><br>
    	社区地址：<input type="text" name="location" value="<%=comm.getLocation()%>"><br>
    	纬度 ：<input type="text" name="lat" value="<%=comm.getLat()%>"><br>
    	经度 ：<input type="text" name="lon" value="<%=comm.getLon()%>"><br>
    	社区小照片：<input type="text" name="small_pic_url" value="<%=comm.getSmall_pic_url()%>"><br>
    	社区大照片：<input type="text" name="big_pic_url" value="<%=comm.getBig_pic_url()%>"><br>
    	<input type="submit" value="修改"> 
    </form>
  </body>
</html>
