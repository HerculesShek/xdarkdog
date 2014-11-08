<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="keywords" content="嘿狗社区" />
		<meta name="description" content="嘿狗社区。" />
		<title>嘿狗社区服务平台</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimum-scale=1.0">
		<link type="text/css" rel="stylesheet" href="/pro/css/common.css" />
		<script type="text/javascript" src="/pro/js/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="/pro/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="/pro/js/fastclick.min.js"></script>
		<script type="text/javascript" src="/pro/js/base.js"></script>
		<script>
		    //fastclick初始化
		    window.addEventListener('load', function () {
		        FastClick.attach(document.body);
		    }, false);
		</script>
		<link type="text/css" rel="stylesheet" href="/pro/css/pay.css" />
	</head>
	<body>
		<div class="container">
			<div class="header">
		    	<h2>订单完成</h2>
			    <!-- <a href="javascript:void(0)" class="left linkAct"><i></i></a> --> 
		  	</div>
		  	<div class="orderpay-success-tips"> 
			  	<img align="middle" src="/pro/images/green-success-icon.png" height="40"/> 恭喜！下单成功 </div>
		  	<div class="orderpay-success-info">
			    <p class="red">您的订单已生成，稍后嘿狗社区客服会与您联系确认订单信息，请保持手机畅通，祝您生活愉快！</p>
		    	<div class="orderpay-success-btn">
		      	<div class="ui-grid-a">
			        <div class="ui-block-a"> <a href="/order" class="button btn-green">查看订单</a> </div>
		        	<div class="ui-block-b"> <a href="/" class="button btn-org">回首页</a> </div>
		      	</div>
		    	</div>
		  	</div>
		</div>
	</body>
</html>