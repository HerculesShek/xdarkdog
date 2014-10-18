<%@page import="com.xdarkdog.pojo.Community"%>
<%@ page language="java" import="java.util.*,com.xdarkdog.pojo.Community" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">

    <title>所有社区的管理页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
  <table border="1">
  	<tr>
  		<td>社区水果店名字</td>
  		<td>社区名字</td>
  		<td>水果店店主名字</td>
  		<td>店主电话</td>
  		<td>水果店详细地址</td>
  		<td>社区纬度</td>
  		<td>社区经度</td>
  		<td>社区图片</td>
  		<td>社区管理</td>
  	</tr>
    <%
    	List<Community>	comms = (List<Community>)request.getAttribute("comms");
    	if(comms != null && comms.size()>0){
    		for(Community c : comms){
    			out.print("<tr>");
    			out.println("<td>"+c.getFruit_shop_name()+"</td>");
    			out.println("<td>"+c.getComm_name()+"</td>");
    			out.println("<td>"+c.getFruit_shop_owner()+"</td>");
    			out.println("<td>"+c.getOwner_phone()+"</td>");
    			out.println("<td>"+c.getLocation()+"</td>");
    			out.println("<td>"+c.getLat()+"</td>");
    			out.println("<td>"+c.getLon()+"</td>");
    			out.println("<td>"+c.getPhotos()+"</td>");
    			out.println("<td><a href=\"/xdarkdog/servlet/comm.do?method=showcomm&id="+c.getId()+"\">修改</a>");
    			out.println("<a href=\"/xdarkdog/servlet/comm.do?method=removecomm&id="+c.getId()+"\">删除</a>");
    			out.println("<a href=\"/xdarkdog/servlet/comm.do?method=managerfruits&id="+c.getId()+"\">水果管理</a></td>");
    			out.println();
    			out.print("</tr>");
    		}
    	}
    %>
    </table>
  </body>
</html>
