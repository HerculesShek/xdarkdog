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
			      	<li><a href="/order?status=1" class="linkAct">等待审核</a></li>
			    	<li><a href="/order?status=3" class="linkAct">正在进行</a></li>
			      	<li><a href="/order?status=4" class="linkAct">订单完成</a></li>
			      	<li><a href="/order?status=5" class="linkAct">订单失效</a></li>
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
			          		<p>订单：<span id="order-item-code"></span></p>
			          		<p>状态：<span><font id="order-item-status"></font></span></p>
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
		var status = getQueryString("status") || "";
		function loadOrders(){
			$.ajax({
				url:"/order/list",
				type:"get",
				data:{
					pageNo:$("#pageNo").val(),
					status:status
				},
				success:function(orders){
				
					var haveOrders = false; 
					for(var orderId in orders) {
						if(haveOrders == false) {
							haveOrders = true;
						} 
						var order = orders[orderId]; 
						var createTime = new Date(order.create_time).Format("yyyy-MM-dd");
						var deliveryTime = new Date(order.subscribe_delivery_time).Format("yyyy-MM-dd");
						var status = parseInt(order.status);
						switch(status) {
							case 1:var statusStr = '待审核';break;
							case 2:var statusStr = '审核不通过';break;
							case 3:var statusStr = '正在配送';break;
							case 4:var statusStr = '交易成功';break;
							case 5:var statusStr = '成功送到';break;
							case 6:var statusStr = '订单失效';break;
						}
						if(order.photos != ''){
							var photo = '<img width="85" height="85" src="' + order.details[0].photos.split(",")[0] + '"/>';
						}
						var totalcostStr = '';
						if(status == 4 || status == 5) {
							totalcostStr = '<span class="m5-l">总价：<font class="red">'+ order.totalcost+'元</font></span>'; 
						}
						var orderName = "";
						var orderNums = 0;
						var details = order.details;
						for( var i = 0, len = details.length; i < len; i ++ ) {
							var detail = details[i];
							if( i != 0 ) {
								orderName += "+";
							}
							orderName += detail.name;
							orderNums += detail.fruit_count;
						} 
						if(orderName.length > 30){
							orderName = orderName.substr(0,30)+"...";
						}
						$("#order_list_item").append(
							'<li>'+
								'<a href="javascript:void(0)" orderId="' + order.order_id + '" class="linkAct order-item-link">'+
									'<input type="hidden" name="orderId" value="' + order.order_id + '"/>' +
									'<input type="hidden" name="statusStr" value="'+ statusStr +'"/>' + 
									'<input type="hidden" name="status" value="' + status + '"/>' +
									'<input type="hidden" name="createTime" value="'+ createTime +'"/>' +
									'<input type="hidden" name="deliveryTime" value="'+ deliveryTime +'"/>'+
									'<div class="lunch-photo">'+photo+'</div>'+
									'<div class="lunch-info">'+
										'<p class="lunch-info-name">'+
										'<em class="left">' + orderName +'</em>'+
										'<span class="gray6 right">'+statusStr+'</span>'+
										'</p><br/>'+
										'<p>' + 
											'<span>总量：'+ orderNums +'</span>'+ 
											totalcostStr + 
										'</p>'+
									'</div>'+
								'</a>'+
							'</li>');
			      	} 
				    if(haveOrders) {
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
				//$(".order-dialog-box").find("a.close-order-dialog").attr("href",'#'+$(this).attr("orderId"));
				var orderId = $(this).attr("orderId");
				$t = $(this);
				$.ajax({
					url:"/order/detail",
					data:{
						orderId:orderId
					},
					success:function(detailInfos){
						//更改订单信息
						var orderId = $t.children("input[name='orderId']").val();
						orderId = "***" + orderId.substr(orderId.length - 10);
						$("#order-item-code").html(orderId);
						$("#order-item-status").html($t.children("input[name='statusStr']").val());
						$("#order-item-create-time").html($t.children("input[name='createTime']").val());
						var status = $t.children("input[name='status']").val();
						status = parseInt(status);
						if(4 == status ) {
							$("#order-item-order-time").html($t.children("input[name='deliveryTime']").val());
						} else {
							$("#order-item-order-time").parents("p").hide();
						}
						$("ul#order-list").empty('li');
						for(var i=0, len = detailInfos.length; i < len ; i++) {  
							var detailInfo = detailInfos[i];
							var fruit = detailInfo.fruit;
							var detail = detailInfo.detail;
						  	if(fruit.remark.length > 30){
								fruit.remark = fruit.desc.substr(0,30)+"...";
							}
							$("ul#order-list").append(
								'<li>'+
									'<a href="javascript:void(0)">'+
										'<div class="lunch-photo">'+ 
											'<img width="85" height="85" src="'+ fruit.photos +'">' + 
										'</div>'+
										'<div class="lunch-info" style="margin-top:-4px;">'+
											'<p>'+ fruit.name +'</p>' + 
											'<p class="gray6">'+ fruit.remark +'</p>'+
											'<p><span>数量：'+ detail.fruit_count+'</span><span class="m5-l">单价：<font class="red">'+ fruit.price + '(' + fruit.measurement_type +')</font></span></p>'+
										'</div>'+
									'</a>'+
								'</li>');
				      	}  
						
						if($("#order_list_item").find("li").length < 1){
							$(".more").hide();
							$("#order-b1box").append('<p class="notfound-order-tips">没有找到订单~</p>');
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
		});
	</script>
</html>