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
		<link type="text/css" rel="stylesheet" href="/pro/css/member.css" />
		<link rel="Stylesheet" href="/pro/css/jquery.autocomplete.css" />
		<script type="text/javascript" src="/pro/js/jquery.autocomplete.min.js"></script>
	</head>

	<body>
		<div class="container">
			<div class="header"> 
				<a href="/shipping" class="left linkAct"><img class="m-t-4" src="/pro/images/arrow-left.png" width="30"/></a>
		    	<h2>编辑地址</h2>
		  	</div>
		  	<div class="content">
		    	<div class="addr-wrap">
			      	<div style="width:60%; float:left;">
				        <div class="input-box">
		          			<input type="hidden" name="addrId" value="${shippingAddress.id}">
		          			<input type="text" class="input name" value="${shippingAddress.realname}" name="contact"/>
		          			<p>真实姓名</p>
		        		</div>
		      		</div>
		      		<div class="gender-box">
		        		<div class="addr-gender-box">
		          		<select class="addr-gender-select select" name="contact_sex" id="addr-gender-select">
		            		<option <c:if test="${shippingAddress.gender eq 0}">selected</c:if> value="0">女士</option>
		            		<option <c:if test="${shippingAddress.gender eq 1}">selected</c:if> value="1">男士</option>
		          		</select>
		        	</div>
		      	</div>
		      	<div class="clr"></div>
		      	<div class="input-box">
			        <input type="tel" class="input phonenum" name="contactTel" value="${shippingAddress.phone}"/>
			        	<p>手机号码</p>
			      	</div>
		    	  	<div class="input-box">
		        		<input class="input address-c" id="location" name="location" value="${shippingAddress.location}"/>
		        		<p>配送地址</p>
		      		</div>
		      		<div class="input-box">
		        		<input type="button" class="button btn-org m-btn-success" value="提交"/>
		      		</div>
		    	</div>
		  	</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function(){
			$(".input").each(function() {
				if($(this).val() != ""){
					$(this).next("p").hide();
				}
			});
			$(".addr-gender-radio").change(function() {
				$(".addr-gender-radio").each(function() {
					if($(this).attr("checked") == "checked"){
						$(this).parents("label").find("b").attr("class","on");
					}else{
						$(this).parents("label").find("b").attr("class","");
					}
				});
			});
			$reg1 = /^1\d{10}$/;//手机验证
			$reg2 = /^[\u4E00-\u9FA5\uf900-\ufa2d\w\.\s]{2,12}$/;//真实姓名验证
			var name = $('input[name="contact"]');//真实姓名
			var tel = $('input[name="contactTel"]');//手机号码
			var community = $('.address-c');//配送地址
			var addr = $('input[name="addr"]');//补充地址
			//表单验证
			$(".m-btn-success").click(
				function() {
					//验证真实姓名
					if(name.val() == ""){
						alert("请输入真实姓名");
						return false;
					}else{
						if(!$reg2.test(name.val())){
							alert("真实姓名输入有误！格式为2到12位中英文字符");
							return false;
						}
					}
					//手机号码验证
					if(tel.val() == ""){
						alert("请输入手机号码");
						return false;
					}else{
						if(!$reg1.test(tel.val())){
							alert("请输入正确的手机号码");
							return false;
						}
					}
					//补充地址验证
					if(addr.val() == ""){
						alert("请补充地址信息");
						return false;
					}
					
					//表单提交
					var LOGIN_NAME = 'USER_NAME';
					$.ajax({
						url:"/shipping/doUpdate",
						type:"post",
						dataType:"json",
						data:{
							addId:$("input[name='addrId']").val(),
							location:$("input[name='location']").val(),
							realname:$("input[name='contact']").val(),
							phone:$("input[name='contactTel']").val(),
							gender:$("#addr-gender-select option:selected").val()
						},
						success:function(resp) {
							if(true == resp){
								window.location.href="/shipping";
							}else{
								alert("编辑地址失败");
							}
						}
					});
				}
			);
		
			$(".input").each(function() {
				if($(this).val() != ""){
					$(this).next("p").hide();
				}else{
					$(this).next("p").show();
				}
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
		});
	</script>
</html>