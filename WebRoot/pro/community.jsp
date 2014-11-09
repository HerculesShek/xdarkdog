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
			<div class="header" id="header2">
				<a href="/" class="left linkAct"><img class="m-t-4" src="/pro/images/arrow-left.png" width="30"/></a>
		    	<h2>要预约配送到哪个社区？</h2>
		    </div>
		  	<div class="content"></div>
		</div>
	</body>
	<script type="text/javascript">
		$(function(){
			var COMM_ID = 'comm_id';
			$.ajax({
				url:"/community/list",
				type:"post",
				dataType:'json',
				success:function(json){
					$(".content").empty();
					for (var i = 0; i < json.length; i++) {
						var  comm = json[i];
			    		$(".content").append('<div class="addr-box"><div class="addr-info-box linkAct"><input class ="commid" type="hidden" value="'+comm.id+'"><div class="addr-name-tel-box"><p class="addr-name-tel" ><span>'+comm.comm_name+'</span></p><div class="clr"></div></div></div></div>');
					}
				},
				error:function(json){
					alert("服务器穿越了~ 再试试看");
				}
	  	  	});
		
			var $address = $(".addr-box");
			$address.live("click",function(){
			 	window.location.href="/fruit?commId=" + $(this).find(".commid").val();
			});
		});
	</script>
</html>