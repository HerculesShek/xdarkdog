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
    
    <title>订单的详细信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  <%
  OrderData info = (OrderData)request.getAttribute("info");
  %>
  <body>
	<table border="1" align="center">
		<tr><th colspan="2" align="center"><h3>订单的详细信息</h3></th></tr>
		<tr>
    		<td>订单号</td>
    		<td>${ info.order_id}</td>
    	</tr>
    	<fmt:setLocale value="zh_CN"/>
    	<tr>
    		<td>订单创建时间</td>
    		<td><fmt:formatDate value="${info.create_time}" type="both" dateStyle="default" timeStyle="default"/></td>
    	</tr>
    	<tr>
    		<td>订单类型</td>
    		<td>
    			<c:if test="${info.order_type eq 1}">
  		    	普通订单
  				</c:if>
  				<c:if test="${info.order_type eq 2}">
  		    	预约订单
  				</c:if>
  			</td>
    	</tr>
    	<tr>
    		<td>预约订单的配送时间</td>
    		<td><fmt:formatDate value="${info.subscribe_delivery_time}" type="both" dateStyle="default" timeStyle="default"/></td>
    	</tr>
    	
    	<tr>
    		<td>订单的状态</td>
    		<td>
    			<c:if test="${info.status eq 1}">
  		    	未审核
  				</c:if>
  				<c:if test="${info.status eq 2}">
  		    	审核不通过
  				</c:if>
  				<c:if test="${info.status eq 3}">
  		    	正在配送
  				</c:if>
  				<c:if test="${info.status eq 4}">
  		    	已完成
  				</c:if>
  				<c:if test="${info.status eq 5}">
  		    	已取消
  				</c:if>
    		</td>
    	</tr>
    	<tr>
    		<td>用户真实姓名</td>
    		<td>${info.realname}-
  				<c:if test="${info.gender eq 1}">
  		    	先生
  				</c:if>
  				<c:if test="${info.gender eq 0}">
  		    	女士
  				</c:if>
  			</td>
    	</tr>
    	<tr>
    		<td>用户联系电话</td>
    		<td>${ info.phone}</td>
    	</tr>
    	<tr>
    		<td>配送地址</td>
    		<td>${ info.location}</td>
    	</tr>
    	<tr>
    		<td>水果店名字</td>
    		<td>${ info.fruit_shop_name}</td>
    	</tr>
    	<tr>
    		<td>社区名字</td>
    		<td>${ info.comm_name}</td>
    	</tr>
    	<tr>
    		<td>订单的详细信息</td>
    		<td>
				<c:forEach var="detail" items="${info.details}">
				${ detail.name}&nbsp;${ detail.fruit_count}(份)&nbsp;x&nbsp;${ detail.price}/${ detail.measurement_type}&nbsp;
				<c:if test="${detail.level eq 1}">
  		    	小个
  				</c:if>
  				<c:if test="${detail.level eq 2}">
  		    	中个
  				</c:if>
  				<c:if test="${detail.level eq 3}">
  		    	大个
  				</c:if>
  				<br>
				</c:forEach>
			</td>
    	</tr>
   
   	<form action="/servlet/csorder.do" method="post">  
   		<input type="hidden" name="method" value="finishOrder">	
   		<input type="hidden" name="order_id" value="${ info.order_id}">	
		<tr style="background-color: gray">
		<td>用户实付款</td>
		<td><input type="text" name="totalcost"/></td>
		</tr>
		<tr><td colspan="2" align="center"><input type="submit" value="结算" /></td></tr>
	</form>
   
	</table>    
  </body>
</html>
