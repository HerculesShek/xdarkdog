<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加水果</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript">
	function addSmall(){
		var td = $("small_pic");
		var input  = document.createElement("input");
		input.type="file";
		input.name="small_pic_url";
		td.appendChild(document.createElement("br"));
		td.appendChild(input);
	}
	
	function addBig(){
		var td = $("big_pic");
		var input  = document.createElement("input");
		input.type="file";
		input.name="big_pic_url";
		td.appendChild(document.createElement("br"));
		td.appendChild(input);
	}
	function $(id){
		return document.getElementById(id);
	}
	</script>
  </head>
  <%
  	String commid = request.getParameter("commid");
  %>
  <body>
    <form action="/xdarkdog/servlet/addfruit.do" method="post" enctype="multipart/form-data">
    	<input type="hidden" name="communityid" value="<%=commid%>">
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
    	
    </form>
  </body>
</html>
