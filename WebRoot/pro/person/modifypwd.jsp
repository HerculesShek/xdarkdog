<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
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
		<link type="text/css" rel="stylesheet" href="/pro/css/login.css" />
	</head>
	<body>
		<div class="container" id="register">
			<div class="header"> 
		  		<a href="/user/profile" class="left linkAct">
		  			<img class="m-t-4" src="/pro/images/arrow-left.png" width="30"/>
		  		</a>
		    	<h2>修改密码</h2>
		  	</div>
		  	<div class="login-box">
		    	<div class="input-box">
		      		<input type="password" class="input" name="password"/>
		      		<p>旧密码</p>
		    	</div>
		    	<div class="input-box">
		      		<input type="password" class="input" name="pwd"/>
		      		<p>新密码</p>
		    	</div>
		    	<div class="input-box">
		      		<input type="password" class="input" name="pwd2"/>
		      		<p>确认密码</p>
		    	</div>
		    	<div class="input-box">
		      		<div>
		        		<input type="button" class="button btn-green" id="modify-btn" value="确认修改"/>
		      		</div>
		    	</div>
		  	</div>
		</div>
	</body>
	<script type="text/javascript"><!--
		$(function(){
			$(".input").each(function() {
				if($(this).val() != ""){
					$(this).next("p").hide();
				}
			});
			$reg2 = /^[\w]{6,20}$/;//密码验证
			var pwd = $('input[name="password"]');//旧密码
			var pwd1 = $('input[name="pwd"]');//新密码
			var pwd2 = $('input[name="pwd2"]');//确认密码
			
			//表单验证
			$("#modify-btn").click(function (){
				//判断旧密码格式
				if( pwd.val() == "") {
					alert("请输入旧密码");
					return false;
				}else{
					if(!$reg2.test(pwd.val())) {
						alert("旧密码格式错误！格式为6到20位的英文下划线和数字");
						return false;
					}
				}
				//判断新密码格式
				if( pwd1.val() == "") {
					alert("请输入新密码");
					return false;
				}else{
					if(!$reg2.test(pwd1.val())) {
						alert("新密码格式错误！格式为6到20位的英文下划线和数字");
						return false;
					}
				}
				//确认密码
				if(pwd2.val() != pwd1.val()) {
					alert("两次密码输入不一致");
					return false;
				}
				
				$.ajax({
					url:"/user/doModifypwd",
					type:"post",
					data:{oldPass:pwd.val(),newPass:pwd1.val()},
					success:function(data){
						if(data.status == 1){
							window.location.href="/user/profile";
						}else{
							alert(data.info);
						}
					},
					error:function(data){
						alert("服务器穿越了~");
					}
				});
			});
		});
	
		$(".input").bind('input propertychange', function() {
			if($(this).val() != ""){
				$(this).next("p").hide();
			}else{
				$(this).next("p").show();
			}
		});
		
		//表单获得焦点提示信息
		$(".input").focus(
			function() {
				$(this).parents(".input-box").attr("class","input-box-on");
			}
		);
		//表单失去焦点提示信息
		$(".input").blur(
			function() {
				$(this).parents(".input-box-on").attr("class","input-box");
			}
		);
	</script>
</html>