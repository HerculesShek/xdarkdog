<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
  <h4>分页显示社区</h4>
  <div>共有${pb.totalRows}条记录
  &nbsp;&nbsp;当前是第${pb.currentPage}页
  </div>
  
  <div>
  	<c:forEach var="i" begin="1" end="${pb.totalPages }">
  		<c:if test="${i eq pb.currentPage }">
  		    ${i }&nbsp;&nbsp;
  		</c:if>
  		<c:if test="${i ne pb.currentPage }">
  		    <a href="/xdarkdog/servlet/comm.do?method=showcommsByPage&currpage=${i }">${i }</a>&nbsp;&nbsp;
  		</c:if>
  	</c:forEach>
  	<table border="1">
  	<tr>
  		<td>社区名字</td>
  		<td>社区地址</td>
  		<td>社区纬度</td>
  		<td>社区经度</td>
  		<td>社区图片</td>
  		<td>社区管理</td>
  	</tr>
    <c:forEach var="comm" items="${pb.data}">
    	<tr>
    		<td>${comm.comm_name}</td>
	  		<td>${comm.location}</td>
	  		<td>${comm.lat }</td>
	  		<td>${comm.lon }</td>
	  		<td><img src="${comm.photos}" width="40px" height="35px"></td>
	  		<td>
				<a href="/xdarkdog/servlet/comm.do?method=showcomm&id=${comm.id }">修改</a>
				<a href="/xdarkdog/servlet/comm.do?method=removecomm&id=${comm.id }">删除</a>
				<a href="/xdarkdog/servlet/comm.do?method=managerfruits&id=${comm.id }">水果管理</a>
			</td>
			</tr>
    </c:forEach>
    </table>
  	<hr/>
    <a href="/xdarkdog/manager/manager.jsp">管理主页面</a>
  </div>
  </body>
</html>
