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
    
    <title>修改社区信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript">
	function deletePhoto(item){
		var hiddenInput = $("delete");
		td = item.parentNode;
		pretd = td.previousSibling.previousSibling;
		img = pretd.firstChild;
		hiddenInput.value=hiddenInput.value+img.src+",";
		tr = item.parentNode.parentNode;
		tr.parentNode.removeChild(tr);
	}
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
  <%
  	Community comm = (Community)request.getAttribute("comm");
    // 此段代码是为了解决修改之前社区没有照片信息，而会上传一个'null'的字符串
  	String oriphotos;
  	if(comm.getPhotos() == null || "".equalsIgnoreCase(comm.getPhotos())){
  		oriphotos = "";
  	}else{
  		oriphotos = comm.getPhotos();
  	}
  %>
    <form action="/xdarkdog/servlet/comm.do" method="post" enctype="multipart/form-data">
    <!-- 修改社区的信息，需要隐藏4个额外的信息：修改方法，社区id，要删除的社区的照片，原来的照片的值 -->
    	<input type="hidden" name="method" value="modifyComm"><br>
    	<input type="hidden" name="id" value="<%=comm.getId()%>"><br>
    	<input type="hidden" name="photostodelete" id="delete" value="">
    	<input type="hidden" name="originalphotos" value="<%=oriphotos%>">
    	<table align="center">
    	<tr><th colspan="2" align="center"><h3>修改社区信息</h3></th></tr>
    	<tr>
    		<td>社区水果店名字: </td>
    		<td><input type="text" name="fruit_shop_name" value="<%=comm.getFruit_shop_name()%>"></td>
    	</tr>
    	<tr>
    		<td>社区名字:</td>
    		<td><input type="text" name="comm_name" value="<%=comm.getComm_name()%>"></td>
    	</tr>
    	<tr>
    		<td>水果店店主名字:</td>
    		<td><input type="text" name="fruit_shop_owner" value="<%=comm.getFruit_shop_owner()%>"></td>
    	</tr>
    	<tr>
    		<td>店主电话:</td>
    		<td><input type="text" name="owner_phone" value="<%=comm.getOwner_phone()%>"></td>
    	</tr>
    	
    	<tr>
    		<td>水果店详细地址：</td>
    		<td><input type="text" name="location" value="<%=comm.getLocation()%>"></td>
    	</tr>
    	<tr>
    		<td>纬度 latitude：</td>
    		<td><input type="text" name="lat" value="<%=comm.getLat()%>"></td>
    	</tr>
    	<tr>
    		<td>经度 longitude：</td>
    		<td><input type="text" name="lon" value="<%=comm.getLon()%>"></td>
    	</tr>
    	<c:forTokens var="url"  items="${comm.photos }"  delims=",">
    	<tr>
	    	<td></td>
	    	<td><img src="${url }" height="200px" width="200px" border="0" /></td>
	    	<td><input type="button" value="删除这个照片" onclick="deletePhoto(this)"></td>
	    </tr>
	    </c:forTokens>
    	<tr>
    		<td>添加社区照片：</td>
    		<td id="imgs"><input type="file" name="photos"></td>
    		<td><input type="button" value="再来一张" onclick="addMore()"></td>
    	</tr>
    	<tr><td colspan="2" align="center"><input type="submit" value="修改信息"> </td></tr>
    	</table>
    </form>
    <hr/>
    <a href="/xdarkdog/manager/manager.jsp">管理主页面</a>
  </body>
</html>
