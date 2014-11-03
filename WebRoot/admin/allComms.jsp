<%@page import="com.xdarkdog.pojo.Community"%>
<%@ page language="java" import="java.util.*,com.xdarkdog.pojo.Community" pageEncoding="UTF-8"%>
<%-- 导入标记库 --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
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
  <table border="1" align="center">
  <tr><th colspan="9"><h3>社区信息列表</h3></th></tr>
  	<tr align="center">
  		<th>社区水果店名字</th>
  		<th>社区名字</th>
  		<th>水果店店主名字</th>
  		<th>店主电话</th>
  		<th>水果店详细地址</th>
  		<th>社区纬度</th>
  		<th>社区经度</th>
  		<th>社区图片</th>
  		<th>社区管理</th>
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
    		out.println("<td>");
    		String photosStr = c.getPhotos();
    		if(photosStr!=null && !"".equals(photosStr)){
    			for(String s:photosStr.split(",")){
    				out.println("<img  src=\""+s+"\" width=\"40px\" height=\"35px\">");
    			}
    		}
    		out.print("</td>");	
    		out.println("");
    		out.println("<td><a href=\"/servlet/comm.do?method=showcomm&id="+c.getId()+"\">修改</a>");
    		out.println("<a href=\"/servlet/comm.do?method=removecomm&id="+c.getId()+"\">删除</a>");
    		out.println("<a href=\"/servlet/comm.do?method=managerfruits&id="+c.getId()+"\">水果管理</a></td>");
    		out.println();
    		out.print("</tr>");
    	}
    }
    %>
    </table>
    <hr/>
    <a href="/admin/manager.jsp">管理主页面</a>
  </body>
</html>
