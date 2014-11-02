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
			<div class="header" id="header1" style="display:none;"> 
				<a href="javascript:void(0)" class="historyBack left linkAct ok-icon"><font>完成</font></a>
		    	<h2>配送地址</h2>
		    	<a href="/shipping/create" class="right linkAct"><img src="/pro/images/plus-icon.png" width="27"/></a> 
		    </div>
		  	<div class="header" id="header2"> 
		  		<a href="javascript:void(0)" class="historyBack left linkAct"><img class="m-t-4" src="/pro/images/arrow-left.png" width="30"/></a>
		    	<h2>配送地址</h2>
		    	<a href="/shipping/create" class="right linkAct"><img src="/pro/images/plus-icon.png" width="27"/></a> 
		    </div>
		  	<div class="content"></div>
		</div>
	</body>
	<script type="text/javascript">
		$(function(){
			$.ajax({
				url:"/shipping/list",
				type:"post",
				dataType:'json',
				success:function(json){
					$(".content").empty();
					for (var i = 0; i <json.length;i++){
					    var address = json[i];
					    var url = '<img src="/pro/images/success-icon.png" height="20">';
					    if (i > 0) {
					    	url = '';
					    }
			    		$(".content").append(
			    			'<div class="addr-box">'+
			    				'<div class="addr-icon-btn">' + 
			    					'<a href="javascript:void(0)" ' + 
			    					    'onclick="return deleteShippingAddr('+ address.id+')" ' + 
			    					    'class="right linkAct member-del">' +
			    					    '<img src="/pro/images/waste-gray.png" height="20">'+
			    					 '</a>' + 
			    					 '<a href="/shipping/update?addrId='+address.id+'" class="right linkAct member-modify">'+
			    					 	'<img src="/pro/images/modify-icon.png" height="20">'+
			    					 '</a>'+
			    				'</div>'+
			    				'<div class="addr-info-box linkAct" '+
			    				      'onclick="setDefault('+address.id+ ')">' + 
			    				      '<div class="addr-default-icon" style="display:none;">'+url+'</div>' + 
			    				      '<div class="addr-name-tel-box">' + 
			    				      	'<p class="addr-name-tel"><span>'+address.location+'</span><em>'+address.phone+'</em></p>' + 
			    				      	'<div class="clr"></div>'+
			    				      '</div>'+
			    				'</div>'+
			    			'</div>');
					}
				},
				error:function(json){
					alert("服务器穿越了~ 再试试看");
				}
	  	  	});
		});
		function setDefault(addrId) {
			$.ajax({
				url:"/shipping/setDefault",
				type:"post",
				dataType:'json',
				data:{addrId:addrId},
				success:function(resp){
					if(true == resp){
						window.location.href="/shipping";
					}else{
						alert("服务器穿越了~ 再试试看");
					}
				},
				error:function(json){
					alert("服务器穿越了~ 再试试看");
				}
	  	  	});
		}
		function deleteShippingAddr(addrId) {
			if(confirm("是否删除?")){
		  		$.ajax({
					url:"/shipping/doDelete",
					type:"post",
					dataType:'json',
					data:{addrId:addrId},
					success:function(resp){
						if(true == resp){
							window.location.href="/shipping";
						}else{
							alert("服务器穿越了~ 再试试看");
						}
					},
					error:function(json){
						alert("服务器穿越了~ 再试试看");
					}
		  	  	});
			} else
		   		return false;
		}
		$(".back-icon").click(function() {
			window.parent.closeIframe();
		});
		$(".ok-icon").click(function() {
			window.parent.refreshAddrIframe();
		});
		$(document).ready(function(){
			var temp = 0;
			$.ajax({
				url:"/shipping/list",
				type:"post",
				dataType:'json',
				success:function(json){
					if (json.length > 1) {
						temp = 1;
					}
				},
				error:function(json){
					alert("服务器穿越了~ 再试试看");
				}
	  	  	});
			if(temp ==1){
				$("#header2").hide();
				$("#header1").show();
			}else{
				$("#header1").hide();
				$("#header2").show();
			}
			$('body,html').animate({
				scrollTop: 0
			},300);
		});
	</script>
</html>