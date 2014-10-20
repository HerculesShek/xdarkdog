<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>更新水果的信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  <body>
  
  <form action="/xdarkdog/servlet/fruit.do" method="post" enctype="multipart/form-data">
    	<input type="hidden" name="communityid" value="${requestScope.fruit.communityid }">
    	<table align="center">
    	<tr>
    		<td>水果名:</td>
    		<td><input type="text" name="name"></td>
    	</tr>
    	<tr>
    		<td>价格：</td>
    		<td><input type="text" name="price"></td>
    	</tr>
    	<tr>
    		<td>积分：</td>
    		<td><input type="text" name="points"></td>
    	</tr>
    	
    	<tr>
    		<td>进价：</td>
    		<td><input type="text" name="original_price"></td>
    	</tr>
    	<tr>
    		<td>显示价格：</td>
    		<td><input type="text" name="display_price"></td>
    	</tr>
    	<tr>
    		<td>是否热卖：</td>
    		<td><input type="text" name="hot_tag"></td>
    	</tr>
    	<tr>
    		<td>是否推荐：</td>
    		<td><input type="text" name="commend_tag"></td>
    	</tr>
    	<tr>
    		<td>简介：</td>
    		<td><input type="text" name="remark"></td>
    	</tr>
    	<tr>
    		<td>详细介绍：</td>
    		<td><textarea cols="23" rows="4" name="introduce"></textarea></td>
    	</tr>
    	<tr>
    		<td>水果小照片：</td>
    		<td id="small_pic" bgcolor="gray"><input type="file" name="small_pic_url"></td>
    		<td><input type="button" value="再来一张" onclick="addSmall()"></td>
    	</tr>
    	<tr>
    		<td>水果大照片：</td>
    		<td id="big_pic"><input type="file" name="big_pic_url"></td>
    		<td><input type="button" value="再来一张" onclick="addBig()"></td>
    	</tr>
    	<tr><td colspan="2" align="center"><input type="submit" value="添加水果"></td></tr>
    	</table>
    <hr/>
    <a href="/xdarkdog/manager/manager.jsp">管理主页面</a>
    </form>
  </body>
</html>
