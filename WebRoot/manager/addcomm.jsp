<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>添加社区信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
		<script type="text/javascript">
	function addMore(){
		var td = $("imgs");
		var input  = document.createElement("input");
		input.type="file";
		input.name="photos";
		td.appendChild(document.createElement("br"));
		td.appendChild(input);
	}
	
	function $(id){
		return document.getElementById(id);
	}
	</script>  
  </head>
  <body>
    <form action="/xdarkdog/servlet/comm.do" method="post" enctype="multipart/form-data">
    	<input type="hidden" name="method" value="addComm"><br>
    	<table align="center">
    	<tr>
    		<td>社区水果店名字: </td>
    		<td><input type="text" name="fruit_shop_name"></td>
    	</tr>
    	<tr>
    		<td>社区名字:</td>
    		<td><input type="text" name="comm_name"></td>
    	</tr>
    	<tr>
    		<td>水果店店主名字:</td>
    		<td><input type="text" name="fruit_shop_owner"></td>
    	</tr>
    	<tr>
    		<td>店主电话:</td>
    		<td><input type="text" name="owner_phone"></td>
    	</tr>
    	
    	<tr>
    		<td>水果店详细地址：</td>
    		<td><input type="text" name="location"></td>
    	</tr>
    	<tr>
    		<td>纬度 latitude：</td>
    		<td><input type="text" name="lat"></td>
    	</tr>
    	<tr>
    		<td>经度 longitude：</td>
    		<td><input type="text" name="lon"></td>
    	</tr>
    	<tr>
    		<td>社区照片：</td>
    		<td id="imgs"><input type="file" name="photos"></td>
    		<td><input type="button" value="再来一张" onclick="addMore()"></td>
    	</tr>
    	<tr><td colspan="2" align="center"><input type="submit" value="添加社区"> </td></tr>
    	</table>
    </form>
  </body>
</html>
