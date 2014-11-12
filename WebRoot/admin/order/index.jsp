<%@ page language="java" import="java.util.*,com.xdarkdog.pojo.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<base href="<%=basePath%>">
    	<title>订单列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript" src="/pro/js/jquery-1.8.3.min.js"></script>    
  	</head>
	<body>
		<table border="1" align="center">
			<thead>
				<tr>
					<th>订单编号</th>
				</tr>
			  	<tr>
		  			<th>订单类型</th>
		  			<th>配送时间</th>
		  			<th>用户名</th>
		  			<th>水果店</th>
		  			<th>配送地址</th>
			  		<th>总花费</th>
		  			<th>状态</th>
	  			</tr>
	  		</thead>
	  		<tbody>
	  			<c:forEach items="${orders}" var="order">
		  			<tr>
		  				<td>${order.order_id}</td>
		  				<td>
							<c:choose>
								<c:when test="${order.order_type == 1}">普通订单</c:when>
								<c:when test="${order.order_type == 2}">预约订单</c:when>
							</c:choose>
						</td>
						<td>
							<fmt:formatDate value="${order.subscribe_delivery_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${order.username}
						</td>
						<td>
							<span class="fruitShop"></span>
						</td>
						<td>
							<span class="shippingAddr"></span>
						</td>
						<td>
							${order.totalcost}
						</td>
						<td>
							<c:choose>
								<c:when test="${order.status eq 1}">待审核</c:when>
								<c:when test="${order.status eq 2}">审核不通过</c:when>
								<c:when test="${order.status eq 3}">审核通过</c:when>
								<c:when test="${order.status eq 4}">订单完成</c:when>
								<c:when test="${order.status eq 5}">已取消</c:when>
							</c:choose>
						</td>
		  			</tr>
	  			</c:forEach>
	  		</tbody>
    	</table>
    	<hr/>
    	<a href="/admin/manager.jsp">管理主页面</a>
  	</body>
  	
  	<script type="text/javascript">
  		$(document).ready(function() {
  			
  			// 加载水果店铺信息和配送地址信息
  			loadFruitShops();
  			loadShippingAddr();
  		});
  		
  		function loadFruitShops() {
  			$.ajax({
  				url : "/"
  			});
  		}
  	</script>
</html>
