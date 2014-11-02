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
		<link type="text/css" rel="stylesheet" href="/pro/css/member.css" />
	</head>
	<body>
		<div class="container">
	  		<div class="header"> 
	  			<a href="javascript:void(0)" class="historyBack left linkAct">
	  				<img class="m-t-4" src="/pro/images/arrow-left.png" width="30"/>
	  			</a>
	    		<h2>个人中心</h2>
	  		</div>
	  		<div class="content">
	    		<div class="person-welcome">
	    			您好，${sessionUser.username}
	    		</div>
	    		<div class="clr"></div>
	    		<div class="person-center-wrap"> 
	    			<a href="/order" class="person-center-box linkAct">
	      				<h2>我的订单</h2>
	      				<b class="person-center-arrow arrow-right"></b> 
	      			</a> 
	      			<a href="/shipping" class="person-center-box linkAct">
	      				<h2>配送地址</h2>
	      				<b class="person-center-arrow arrow-right"></b> 
	      			</a> 
	      			<a href="/user/modifypwd" class="person-center-box linkAct">
      					<h2>修改密码</h2>
      					<b class="person-center-arrow arrow-right"></b> 
      				</a> 
      			</div>
   				<a href="javascript:void(0)" class="person-center-box linkAct person-logout">
   					<h2>退出账号</h2>
   				</a> 
	   		</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function(){
		    $(".person-logout").click(function() {
		    	$.ajax({
		    		url : "/user/doLogout",
		    		type:'post',
                    dataType:'json',
		    		success : function(resp) {
		    			if(true == resp) {
		    				window.location.href = "/";
		    			} else {
		    				alert("服务器穿越了，请重试...");
		    			}
		    		}
		    	});
		    });
		});
		function yesno() {
			if(confirm("是否删除?")){
		  		return true;
			}else {
		   		return false;
			}
		}
	</script>
</html>