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
		<link type="text/css" rel="stylesheet" href="/pro/css/login.css" />
	</head>
	<body>
	<div class="container" id="register">
		<div class="header"> 
			<a href="/" class="left linkAct">
				<img class="m-t-4" src="/pro/images/arrow-left.png" width="30"/><font>首页</font>
			</a>
	    	<h2>注册</h2>
	    	<a href="/user/login" class="right linkAct"><font>登录</font></a> </div>
		  	<div class="login-box">
		    	<form action="/xdarkdog/servlet/user.do?method=register" method="post" class="registerForm">
			      	<div class="input-box">
			        	<input type="text" class="input" name="username" placeholder="用户名（以字母开头）"/>
			      	</div>
			      	<div class="input-box">
			        	<input type="password" class="input" name="password" placeholder="密码"/>
			      	</div>
			      	<div class="input-box">
			        	<input type="password" class="input" name="password2" placeholder="确认密码"/>
			      	</div>
			      	<div class="input-box">
			        	<input type="tel" name="tel" class="input" placeholder="手机号码"/>
			      	</div>
			      	<div class="register-div">
			        	<div class="register-btn-box">
			          		<input type="button" class="button btn-green" value="立即注册"/>
			        	</div>
			      	</div>
		    	</form>
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
			$reg1 = /^1\d{10}$/;//手机验证
			$reg2 = /^[\w]{6,20}$/;//密码验证
			$reg3 = /^[a-zA-Z][\w]{5,19}$/;//用户名验证
			var telIpt = $('input[name="tel"]');//手机号码
			var pwd1 = $('input[name="password"]');//密码
			var pwd2 = $('input[name="password2"]');//确认密码
			var username = $('input[name="username"]');//用户名
			
			//判断手机号码正确性
			function checkMobile(){
				if(!$reg1.test(telIpt.val())){
					return false;
				}else{
					return true;
				}
			}
			
			//表单验证
			$(".register-btn-box").find(".button").click(function (){
				if(username.val() == ""){
					alert("请输入用户名");
					return false;
				}else{
					if(!$reg3.test(username.val())){
						alert("用户名格式错误！格式为6到20位的英文下划线和数字，并以字母开头！");
						return false;
					}
				}
				
				//判断密码格式
				if( pwd1.val() == "") {
					alert("请输入密码");
					return false;
				}else{
					if(!$reg2.test(pwd1.val())) {
						alert("密码格式错误！格式为6到20位的英文下划线和数字");
						return false;
					}
				}
				
				//确认密码
				if(pwd2.val() != pwd1.val()) {
					alert("两次密码输入不一致");
					return false;
				}
				
				//判断号码
				if(!checkMobile()) {
					alert("请输入正确的手机号码");
					return false;
				}
				
				var userTemp = false;
				$.ajax({
					url:"/user/checkName",
					type:"post",
					async:false,
					data:{username:$.trim(username.val())},
					success:function(resp){
						if(true == resp){
							userTemp = true;
						}else{
							alert("用户名已存在");
						}
					}
				});
				if(!userTemp){
					return false;
				}
				var codeTemp = false;
				//判断电话号码是否正确
				$.ajax({
					url:"/user/checkPhone",
					type:"post",
					async:false,
					data:{phone:telIpt.val()},
					success:function(resp){
						if(true == resp){
							codeTemp = true;
						}else{
							alert("电话号码已经被使用失败");
						}
					}
				});
				if(!codeTemp){
					return false;
				}
				//ajax
				$.ajax({
					url:"/user/doRegister",
					type:"post",
					data:{username:username.val(),password:pwd1.val(),tel:telIpt.val()},
					success:function(resp){
						if(true == resp){
							window.location.href="/user/login";
						}else{
							alert("注册失败，请重试");
						}
					},
					error:function(data){
						alert("服务器穿越了~请稍后再试~");
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