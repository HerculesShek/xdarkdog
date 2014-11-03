<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    
    <title>分页展示社区信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
  <h4 align="center">分页显示社区</h4>
  <div align="center">&nbsp;&nbsp;共有${pb.totalRows}条记录
  &nbsp;&nbsp;当前是第${pb.currentPage}页
  </div>
  
  <div>
  &nbsp;&nbsp;
  <div align="center">
  	<c:forEach var="i" begin="1" end="${pb.totalPages }">
  		<c:if test="${i eq pb.currentPage }">
  		    ${i }&nbsp;&nbsp;
  		</c:if>
  		<c:if test="${i ne pb.currentPage }">
  		    <a href="/servlet/comm.do?method=showcommsByPage&currpage=${i }">${i }</a>&nbsp;&nbsp;
  		</c:if>
  	</c:forEach>
  </div>
  	<table border="1" align="center">
  	<tr><th colspan="9"><h3>社区信息列表</h3></th></tr>
  	<tr>
  		<td>水果店名字</td>
  		<td>社区名字</td>
  		<td>店主名字</td>
  		<td>店主电话</td>
  		<td>地址</td>
  		<td>纬度</td>
  		<td>经度</td>
  		<td>图片</td>
  		<td align="center">管理</td>
  	</tr>
    <c:forEach var="comm" items="${pb.data}">
    	<tr>
    	    <td>${comm.fruit_shop_name}</td>
    		<td>${comm.comm_name}</td>
    		<td>${comm.fruit_shop_owner}</td>
  		    <td>${comm.owner_phone}</td>
	  		<td>${comm.location}</td>
	  		<td>${comm.lat }</td>
	  		<td>${comm.lon }</td>
	  		<td>
		  		<c:forTokens var="url"  items="${comm.photos }"  delims=",">
	    			<img src="${url }" height="80px" width="80px" border="0" /> 
	    		</c:forTokens>
	    	</td>
	  		<td>
				<a href="/servlet/comm.do?method=showcomm&id=${comm.id }">修改</a>
				<a href="/servlet/comm.do?method=removecomm&id=${comm.id }">删除</a>
				<a href="/servlet/comm.do?method=managerfruits&id=${comm.id }">水果管理</a>
			</td>
			</tr>
    </c:forEach>
    </table>
  	<hr/>
    <a href="/admin/manager.jsp">管理主页面</a>
  </div>
  </body>
</html>
