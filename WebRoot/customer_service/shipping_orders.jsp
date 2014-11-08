<%@ page language="java" import="java.util.*, com.xdarkdog.web.util.data.*" pageEncoding="UTF-8"%>
<%-- 导入标记库 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>所有正在配送的订单</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script>
	// setTimeout("location.href='url'",2000);
	setTimeout("self.location.reload();",60000);
	</script>   
  </head>
  
<%
	List<OrderData> datas = (List<OrderData>)request.getAttribute("datas");
%>
<body>
<table border="1" align="center">
<tr><th colspan="8"><h3>正在配送的订单</h3></th></tr>
  	<tr>
  		<th>订单创建时间</th>
  		<th>订单类型</th>
  		<th>配送姓名</th><!-- 加入用户的性别 -->
  		<th>用户电话</th>
  		<th>配送详细地址</th>
  		<th>水果店名字</th>
  		<th>社区名字</th>
  		<th align="center">管理</th>
  	</tr>
  	
  	<c:forEach var="orderData" items="${datas}">
  	<tr>
  	    <fmt:setLocale value="zh_CN"/>
  		<td>
  		<fmt:formatDate value="${orderData.create_time}" type="both" dateStyle="default" timeStyle="default"/>
  		</td>
  		<td>
  			<c:if test="${orderData.order_type eq 1}">
  		    	普通订单
  			</c:if>
  			<c:if test="${orderData.order_type eq 2}">
  		    	预约订单
  			</c:if>
  		</td>
  		<td>${orderData.realname}-
  			<c:if test="${orderData.gender eq 1}">
  		    	先生
  			</c:if>
  			<c:if test="${orderData.gender eq 0}">
  		    	女士
  			</c:if>
  		</td>
  		<td>${orderData.phone}</td>
  		<td>${orderData.location}</td>
  		<td>${orderData.fruit_shop_name}</td>
  		<td>${orderData.comm_name}</td>
  		<td align="center">
  			<a href="/servlet/csorder.do?method=getOrderDataByOrderId&next=checkout&orderid=${orderData.order_id }" target="view_window">配送完成去结账！</a>
  		</td>
  	</tr>
  	</c:forEach>
</table>  
  
</body>
</html>
