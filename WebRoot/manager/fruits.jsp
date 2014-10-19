<%@ page language="java" import="java.util.*,com.xdarkdog.pojo.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>水果管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
    <%
    	Community comm = (Community)request.getAttribute("comm");
    	List<Fruit> fruits = (List<Fruit>)request.getAttribute("fruits");
    %>
  <body>
	<h4><%=comm.getComm_name() %></h4>
	<table border="1" align="center">
  	<tr>
  		<td>水果名字</td>
  		<td>价格</td>
  		<td>可获得积分</td>
  		<td>是否热卖</td>
  		<td>是否推荐</td>
  		<td>图片</td>
  	</tr>
    <%
    	if(fruits != null && fruits.size()>0){
    		for(Fruit f : fruits){
    			out.print("<tr>");
    			out.println("<td>"+f.getName()+"</td>");
    			out.println("<td>"+f.getPrice()+"</td>");
    			out.println("<td>"+f.getPoints()+"</td>");
    			out.println("<td>"+(f.getHot_tag()==1?"是":"否")+"</td>");
    			out.println("<td>"+(f.getCommend_tag()==1?"是":"否")+"</td>");
    			out.println("<td>");
    			String photos = f.getPhotos();
    			if(photos != null){
    				String[] pics = photos.split(",");
    				if(pics.length>0){
    					for(String s : pics){
    						out.println(" <img  src=\""+s+"\" width=\"40px\" height=\"35px\">");
    					}
    				}
    			}
    			out.println("</td>");
    			// TODO 修改水果和删除水果的页面
    			out.println("<td><a href=\"/xdarkdog/servlet/fruit.do?method=friutinfo&id="+f.getId()+"\">修改</a>");
    			out.println("<a href=\"/xdarkdog/servlet/fruit.do?method=removeFruit&id="+f.getId()+"&commid="+f.getCommunityid()+"\">删除</a>");
    			out.println();
    			out.print("</tr>");
    		}
    	}
    %>
    <tr>
    <td align="center" colspan="6"><a href="/xdarkdog/manager/addfruit.jsp?commid=<%=comm.getId()%>">添加水果</a></td>
    </tr>

    </table>
  </body>
</html>
