<%@ page language="java" import="java.util.*,com.xdarkdog.pojo.Fruit" pageEncoding="UTF-8"%>
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
    <title>更新水果信息</title>
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
	
	<%
		Fruit fruit = (Fruit) request.getAttribute("fruit");
		// 此段代码是为了解决修改之前社区没有照片信息，而会上传一个'null'的字符串
		String oriphotos;
		if (fruit.getPhotos() == null || "".equalsIgnoreCase(fruit.getPhotos())) {
			oriphotos = "";
		} else {
			oriphotos = fruit.getPhotos();
		}
 	%>    
  </head>
  
  <body>
  
  <form action="/xdarkdog/servlet/fruit.do" method="post" enctype="multipart/form-data">
    	<!-- 修改水果的信息，需要隐藏5个额外的信息：修改方法，水果id，要删除的水果的照片，原来的照片的值,社区的id -->
    	<input type="hidden" name="method" value="modifyFruit"><br>
    	<input type="hidden" name="id" value="<%=fruit.getId()%>"><br>
    	<input type="hidden" name="photostodelete" id="delete" value="">
    	<input type="hidden" name="originalphotos" value="<%=oriphotos%>">
    	<input type="hidden" name="communityid" value="<%=fruit.getCommunityid()%>">
    	<table align="center">
    	<tr><th colspan="3" align="center"><h3>修改水果信息</h3></th></tr>
    	<tr>
    		<td>水果名:</td>
    		<td><input type="text" name="name" value="${fruit.name}" ></td>
    	</tr>
    	<tr>
    		<td>价格：</td>
    		<td><input type="text" name="price" value="${fruit.price}">元</td>
    	</tr>
    	<tr>
    		<td>计量单位：</td>
    		<td><input type="text" name="measurement_type" value="${fruit.measurement_type}"></td>
    	</tr>
    	<tr>
    		<td>单位积分：</td>
    		<td><input type="text" name="points" value="${fruit.points}"></td>
    	</tr>
    	
    	<tr>
    		<td>进价：</td>
    		<td><input type="text" name="original_price" value="${fruit.original_price}">元</td>
    	</tr>
    	<tr>
    		<td>显示价格：</td>
    		<td><input type="text" name="display_price" value="${fruit.display_price}">元</td>
    	</tr>
    	<tr>
    		<td>是否热卖：</td>
    		<td><input type="text" name="hot_tag" value="${fruit.hot_tag}"></td>
    		<td>1表示热卖&nbsp;0表示不热卖</td>
    	</tr>
    	<tr>
    		<td>是否推荐：</td>
    		<td><input type="text" name="commend_tag" value="${fruit.commend_tag}"></td>
    		<td>1表示推荐&nbsp;0表示不推荐</td>
    	</tr>
    	<tr>
    		<td>简介：</td>
    		<td><input type="text" name="remark" value="${fruit.remark}"></td>
    	</tr>
    	<tr>
    		<td>详细介绍：</td>
    		<td><textarea cols="23" rows="4" name="introduce">${fruit.introduce}</textarea></td>
    	</tr>
    	<tr>
    		<td>是否在售：</td>
    		<td><input type="text" name="valid_tag" value="${fruit.valid_tag}"></td>
    		<td>1表示推荐&nbsp;0表示不推荐&nbsp;重要!!</td>
    	</tr>
		<c:forTokens var="url"  items="${fruit.photos }"  delims=",">
    	<tr>
	    	<td></td>
	    	<td><img src="${url }" height="200px" width="200px" border="0" /></td>
	    	<td><input type="button" value="删除这个照片" onclick="deletePhoto(this)"></td>
	    </tr>
	    </c:forTokens>    	
		<tr>
    		<td>添加水果照片：</td>
    		<td id="imgs"><input type="file" name="photos"></td>
    		<td><input type="button" value="再来一张" onclick="addMore()"></td>
    	</tr>
    	<tr><td colspan="3" align="center"><input type="submit" value="修改提交"></td></tr>
    	</table>
    </form>
     <hr/>
     <a href="/xdarkdog/manager/manager.jsp">管理主页面</a>
     <a href="/xdarkdog/servlet/comm.do?method=show">社区信息</a>
  </body>
</html>
