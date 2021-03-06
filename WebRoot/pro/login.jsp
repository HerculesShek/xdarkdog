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
        <div class="container">
            <div class="header">
                <a href="/" class="left linkAct">
                    <img class="m-t-4" src="/pro/images/arrow-left.png" width="30"/>
                    <font>首页</font>
                </a>
                <h2>登录</h2>
                <a href="/user/register" class="right linkAct"><font>注册</font></a>
            </div>
            <div class="login-box">
                <form method="post" id="loginForm">
                    <div class="input-box">
                        <input type="hidden" id="come_form_key" value=""/>
                        <input type="text" class="input" id="userName" autocomplete="off" name="username" placeholder="用户名/手机号码"/>
                    </div>
                    <div class="input-box">
                        <input type="password" class="input" id="loginPassword" autocomplete="off" name="password" placeholder="密码"/>
                    </div>
                    <div class="login-btn">
                        <input type="button" class="button btn-org" id="btn" value="登 录" class="submit-btn"/>
                        <a href="tel:${orderPhone}" class="forget-pwd linkAct left">电话订购鲜果</a>
                        <a href="tel:${orderPhone}" class="forget-pwd linkAct right">忘记密码？</a>
                    </div>
                </form>
                <input name="redirectURL" value="${redirectURL}" type="hidden"/>
              </div>
        </div>
    </body>
    <script type="text/javascript">
        //登录验证
        $(function(){
            $(".input").each(function() {
                if($(this).val() != ""){
                    $(this).next("p").hide();
                }
            });
            var $login = $("#loginForm");
            var $userName = $("#userName");
            var $passWord = $("#loginPassword");
            var $input = $(".l-input");

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

            $("#btn").click(function(){
                if($userName.val() == ""){
                    alert("用户名不能为空");
                    return false;
                }
                if($passWord.val() == ""){
                    alert("密码为空");
                    return false;
                }

                $(this).attr("class","button btn-gray");
                $(this).val("登录中...");
                $(this).attr("disabled","true");
                $.ajax({
                    url:'/user/doLogin',
                    type:'post',
                    dataType:'json',
                    data:{
                    	username:$("#userName").val(),
                    	password:$("#loginPassword").val(),
                    	come_form_key:$("#come_form_key").val()
                    },
                    success:function(json){
                        if(true == json){
                           	var redirectURL = $("input[name='redirectURL']").val();
                           	if( "" != redirectURL ) {
                               	window.location.href= redirectURL;
                           	} else {
                           		window.location.href="/";
                           	}
                        }else{
                            $passWord.val("");
                            $("#btn").attr("class","button btn-org");
                            $("#btn").val("登 录");
                            $("#btn").removeAttr("disabled");
                        }
                    }
                });
            });
            //监听回车事件
            document.onkeydown = function(ev){
               ev = ev || window.event;
               if(ev.keyCode == 13){
                   document.getElementById('btn').click();
               }
            };
        });
    </script>
</html>