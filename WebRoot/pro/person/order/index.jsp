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
			<div class="header" id="order-header"> 
		  		<a href="/user/profile" class="left linkAct">
		  			<img class="m-t-4" src="/pro/images/arrow-left.png" width="30"/>
		  		</a>
		    	<h2>我的订单</h2>
		    	<a href="javascript:void(0)" class="right linkAct screen-btn">
		    		<b class="screen-icon"><img src="/pro/images/screen-icon.png" height="22"/></b><font>筛选</font>
		   		</a> 
		  	</div>
		  	<div class="screen-shade" style="display:none;"></div>
		  	<div class="screen-box" style="display:none;">
				<ul>
			    	<li><a href="/order?status=1" class="linkAct">正在进行</a></li>
			      	<li><a href="/order?status=2" class="linkAct">订单完成</a></li>
			      	<li><a href="/order?status=3" class="linkAct">订单失效</a></li>
			      	<li><a href="/order?status=4" class="linkAct">等待审核</a></li>
			    </ul>
		  	</div>
			<div class="content" id="order-content"> 
		    	<!--
		    	<div class="order-title">
			    	<div class="ui-grid-a">
						<div class="ui-block-a">
							<a href="javascript:void(0)" id="order-b1" class="on">已支付订单</a>
						</div>
					  	<div class="ui-block-b">
					  		<a href="javascript:void(0)" id="order-b2">未支付订单</a>
						</div>
					</div>
				</div>
				 -->
				<div class="order-list order-list-box" id="order-b1box">
			    	<ul id="order_list_item">
			        	
			      	</ul>
			      	<input type="hidden" id="pageNo" value="1"/>
			      	<input type="hidden" id="status" value=""/>
			      	<a href="javascript:void(0)" class="more linkAct">加载更多</a> 
		   		</div>
				    <!------------------- 订单详情 -------------------------->
				<div class="order-dialog-wrap" style="display:none;"></div>
			    <div class="order-dialog-box" style="display:none;">
			   		<div class="header"> 
			   			<a href="#" class="left linkAct close-order-dialog"><img class="m-t-4" src="/pro/images/arrow-left.png" width="30"/></a>
			        	<h2>订单详情</h2>
			      	</div>
			      	<div class="content">
			        	<div class="order-item-box">
			          		<p>订单：<span id="order-item-code"></span><font id="order-item-status"></font></p>
			          		<p>下单时期：<span id="order-item-create-time"></span></p>
			          		<p>配送时期：<span id="order-item-order-time"></span></p>
			        	</div>
			        	<div class="order-list">
			          		<ul id="order-list"></ul>
			        	</div>
			      	</div>
			    </div>
			    <!------------------- 订单详情结束 -----------------------> 
			</div>
			<div class="loader-box" style="display:none;">
				<div class="loader-shade"></div>
			    <div class="loader-icon"></div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
			loadOrders();
		});
		var status = getQueryString("status") || 1;
		function loadOrders(){
			$.ajax({
				url:"/order/list",
				type:"get",
				data:{
					pageNo:$("#pageNo").val(),
					status:status
				},
				success:function(orders){
					console.dir(orders);
					if( orders.length > 0 ) {
						for(var i=0; i<jsonData.length; i++) {
							var createTime = new Date(jsonData[i].create_time*1000).Format("yyyy-MM-dd");
							var order_time = new Date(jsonData[i].order_time*1000).Format("yyyy-MM-dd");
							var is_lunch = jsonData[i].is_lunch?'午餐':'晚餐';
							switch(parseInt(jsonData[i].status)) {
								case 1:var status = '未付款';break;
								case 2:if(jsonData[i].pay_type == 3){var status = '已受理';}else{var status = '已受理';}break;
								case 3:var status = '等待配送';break;
								case 4:var status = '配送中';break;
								case 5:var status = '交易成功';break;
								case 6:var status = '待审核';break;
								case 7:var status = '订单失效';break;
							}
							if(jsonData[i].photo != ''){
								var photo = '<img width="85" height="85" src="http://www.mrhaosi.com/haosi'+jsonData[i].photo+'"/>';
							}
							if(jsonData[i].lunch_name.length > 10){
								var lunch_name = jsonData[i].lunch_name.substr(0,10)+'...';	
							}else{
								var lunch_name = jsonData[i].lunch_name;
							}
							$("#order_list_item").append(
								'<li>'+
									'<a href="#" class="linkAct order-item-link" id="orderLink'+(parseInt(data.info)+i)+'">'+
										'<input type="hidden" value="'+jsonData[i].code+'" id="code"/>'+
										'<input type="hidden" value="'+createTime+'" id="create_time"/>'+
										'<input type="hidden" value="'+order_time+'&nbsp;'+is_lunch+'" id="order_time"/>'+
										'<input type="hidden" value="'+status+'" id="status"/>'+
										'<div class="lunch-photo">'+photo+'</div>'+
										'<div class="lunch-title"><h2>'+order_time+is_lunch+'</h2></div>'+
										'<div class="lunch-info">'+
											'<p class="lunch-info-name">'+
											'<em class="left">'+lunch_name+'</em>'+
											'<span class="gray6 right">'+status+'</span>'+
											'</p><br/>'+
											'<p><span>总量：'+jsonData[i].num+'</span><span class="m5-l">总价：<font class="red">'+jsonData[i].real_price+'</font></span></p>'+
										'</div>'+
									'</a>'+
								'</li>');
				      	} 
				      	$("#pageNo").val(parseInt($("#pageNo").val()) + 1);
						$(".loader-box").hide();
						$(".more").html("加载更多");
					} else {
						$(".loader-box").hide();
						$(".more").unbind();
						$(".more").attr("class","more-off");
						$(".more-off").html("没有更多了");
					}
				}
			});
		}
		$(function() {
			//点击加载更多
			$(".more").click(function() {
				$(".loader-box").show();
				$(".more").html("加载中...");
				loadOrders();
			});
			//关闭订单详情
			$(".close-order-dialog").click(function() {
				$(".order-dialog-wrap").hide();
				$(".order-dialog-box").hide();
				$(".order-list-box").show();
			});
			//打开订单详情
			$(".order-item-link").live('click',function() {
				$(".order-dialog-box").find("a.close-order-dialog").attr("href",'#'+$(this).attr("id"));
				$t = $(this);
				$.ajax({
					url:"/haosi/index.php/weixin/order/getOrderInfo",
					type:'post',
					data:{code:$(this).children('#code').val()},
					success:function(data){
						var data = eval("("+data+")");
						if(data.status == 1){
							//更改订单信息
							$("#order-item-code").html($t.children("#code").val());
							$("#order-item-status").html($t.children("#status").val());
							$("#order-item-create-time").html($t.children("#create_time").val());
							$("#order-item-order-time").html($t.children("#order_time").val());
							var jsonData = data.data;//获取json数据
							$("ul#order-list").empty('li');
							for(var i=0; i<jsonData.length; i++)  
						  	{  
							  	var dish = jsonData[i].main_dish+","+jsonData[i].side_dish;
							  	if(dish.length > 30){
									dish = dish.substr(0,30)+"...";
								}
								$("ul#order-list").append('<li><a href="javascript:void(0)"><div class="lunch-photo"><img width="85" height="85" alt="" src="http://www.mrhaosi.com/haosi'+jsonData[i].mob_img+'"></div><div class="lunch-info" style="margin-top:-4px;"><p>'+jsonData[i].lunch_name+'</p><p class="gray6">'+dish+'</p><p><span>数量：'+jsonData[i].num+'</span><span class="m5-l">单价：<font class="red">'+jsonData[i].flexible_price+'</font></span></p></div></a></li>');
					      	}  
						}else{
							alert(data.info);
						}
					},
					error:function(){
						alert('服务器穿越了，我们正在呼叫它！');
					}
				});
				$(".order-dialog-wrap").show();
				$(".order-dialog-box").show();
				$(".order-list-box").hide();
			});
		
			$(".screen-btn").click(
				function() {
					if($(".screen-box").css("display") == "block") {
						screenClose();
					}else{
						screenOpen();
					}
		   		}
			);
			function screenOpen(){
		   		$(".screen-shade").show();
		    	$(".screen-box").show();
		   	}
		   	function screenClose(){
		   		$(".screen-shade").hide();
		    	$(".screen-box").hide();
		   	}
		   	$(".screen-shade").click(function() {
		   		screenClose();
		   	});
		   	$(".screen-box").find("a").click(function() {
		   		$(".loader-box").show();
		   		screenClose();
		   	});
			if ( $("#someID").length > 0 ) {
		
			}
			if($("#order_list_item").find("li").length < 1){
				$(".more").hide();
				$("#order-b1box").append('<p class="notfound-order-tips">没有找到订单~</p>');
			}
			//初始化分页参数
			var page_start = 5;
			if(parseInt(page_start)){
				$("#pageNo").val(page_start);
			}
		});
	</script>
</html>