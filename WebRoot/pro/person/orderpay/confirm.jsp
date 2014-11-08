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
		<div class="addAddrBox" style="display:none;">
			<iframe src="" width="100%" height="100%" frameborder="0"></iframe>
		</div>
		<div class="container">
		    <div class="header">
		    	<a href="/fruit" class="left linkAct"><img class="m-t-4" src="/pro/images/arrow-left.png" width="30"/></a>
		    	<h2>订单结算</h2>
		    </div>
	    	<div class="manage-address">
	    		<h2 class="left">配送地址</h2><a class="linkAct red addAddrBtn" href="/shipping">管理地址</a>
	    	</div>
	    	<div class="clr"></div>
	    	<div id="noadd" style="display:none;"><a href="javascript:void(0)" class="button btn-green">新增地址</a></div>
	        <div class="order-address-box" style="border-bottom:none;">
		    	<div class="order-address-default linkAct">
		    		<input type="hidden" name="addr_id" id="addr_id"/>
		   			<h2></h2>
		   			<p class="gray8"></p>
					<b class="order-address-arrow arrow-right"><img src="/pro/images/arrow.png" width="20"/></b>
		    	</div>
	   		</div>
		    <!-- 选择地址会话框 -->
		   	<div class="dialog-wrap" style="display:none;">
			    <div class="dialog-shade"></div>
				<div class="dialog-box">
					<h3>选择配送地址<a href="javascript:void(0)" class="dialog-close-btn linkAct"><img src="/pro/images/close.png" width="20"/></a></h3>
					<div class="dialog-content">
						<ul class="order-other-address"></ul>
					</div>
				</div>
			</div>
			<div class="order-pay-box-display">
			    <div class="manage-title"><h2>选择送水果时间</h2></div>
			    <div class="order-pay-time-wrap">
			    	<div class="order-pay-time" style="border-bottom: none;">
			    		<div class="order-time-box">
			    			<div class="order-pay-time-select-left" style="width:50%;">
								<select id="order-pay-day" class="order-pay-select select"  name="order_time">
							   	</select>
					   	  	</div>
					   	  	<div class="order-pay-time-select-left" style="width:20%;">
						   	  	<select class="order-pay-select select" id="order-pay-hour">
						   	  		<option value="08">08点</option>
							   		<option value="09">09点</option>
							   		<option value="10">10点</option>
							   		<option value="11">11点</option>
							   		<option value="12">12点</option>
							   		<option value="13">13点</option>
							   		<option value="14">14点</option>
							   		<option value="15">15点</option>
							   		<option value="16">16点</option>
							   		<option value="17">17点</option>
							   		<option value="18">18点</option>
							   		<option value="19">19点</option>
							   		<option value="20">20点</option>
							   		<option value="21">21点</option>
							   	</select>
						   	</div>
						   	<div class="order-pay-time-select-right" style="width:20%;">
						   	  	<select class="order-pay-select select" id="order-pay-min">
							   		<option value="00">00分</option>
							   		<option value="10">10分</option>
							   		<option value="20">20分</option>
							   		<option value="30">30分</option>
							   		<option value="40">40分</option>
							   		<option value="50">50分</option>
							   	</select>
						   	</div>
			    		</div>
			    		<div class="clr"></div>
			    	</div>
			    </div>
			    <div class="clr"></div>
				 <!-- 选择地址会话框 结束 -->
			    <input type="hidden" value="">
			    <div class="manage-title"><h2>确认鲜果信息</h2></div>
			    <div class="lunch-box order-pay-lunch-box">
				    <ul>
				    	<!-- 遍历订单 -->
				    	<li>
				        	<div class="lunch-photo">
				        		<img width="85" height="85" alt="" src="http://www.mrhaosi.com/haosi./Uploads/Mobile/day_140916/201409161548185830.jpg">		                
				        	</div>
				            <div class="lunch-title"><h2>米饭</h2></div>
			            	<div class="lunch-info">
			                    <div class="ui-grid-a">
			                        <div class="ui-block-a">
			                            <p class="lunch-font12"><font class="org">￥<i class="price">1.00</i></font>&nbsp;<span>￥<font class="font-through">1.00</font></span></p>
			                        </div>
			                        <div class="ui-block-b">
			                            <b class="item-btn item-plus"><img height="28" src="/pro/images/plus.png"/></b>
			                            <div class="item-btn-box" style="display:none;">
			                                <font class="item-text">0</font>
			                          		<input type="hidden" class="order-pay-type" />
			                                <b class="item-btn item-minus"><img height="28" src="/pro/images/minus.png"/></b>
			                            </div>
			                        </div>
			                    </div>
			                </div>
				        </li>		    
		     		</ul>
		     		<div style="display:inline-block;margin:10px;">
				    	<strong class="order-pay-tips red">注：请确认您的订单信息。</strong>
				    </div>
				</div>
			</div>
			<div class="clr"></div>
			<input type="hidden" class="order-pay-order-id" id="order_id" value="" />
			<input type="hidden" class="order-pay-order-count" id="order_count" value="" />
			<input type="hidden" class="order-pay-order-level" id="order_level" value="" />
			<input type="hidden" id="order-pay-yhInput" value="0" />
		    <div class="order-count-box">
				<div class="order-count-text left">实付款:<span class="org">￥<font class="toTalPrice">0.00</font></span></div>
				<div class="settlement-box"><input type="button" id="settlement" class="button btn-org order-payment-btn" value="确认下单"/></div>
		   	</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function(){
			var TYPE = 'ORDER_BY';
			var LOGIN_NAME = 'USER_NAME';
			var COOKIE_NAME = 'mobileCart';
			$.ajax({
				url:'/shipping/list',
				type:'post',
				dataType:'json',
				success:function(json){
					if (json.length < 1) {
						$("#noadd").show();
					} else {
						$("#noadd").hide();
					}
					$(".dialog-content ul.order-other-address").empty("li");
					for (var i=0; i<json.length; i++){
						var address = json[i];
						$(".dialog-content ul.order-other-address").append(
							'<li class="linkAct" onclick="changeAddr(this)">' +
	    						'<input type="hidden" class="address-id" ref="'+address.id+'"/>' + 
		    					'<h2>'+address.location+'</h2><p>'+address.phone+'</p>' +
		    				'</li>');
					}
					
					//初始化地址
					$(".order-address-default").find("h2").html($(".order-other-address .linkAct:first").find("h2").html());
					$(".order-address-default").find("p").html($(".order-other-address .linkAct:first").find("p").html());
					$(".order-address-default").find("#addr_id").val($(".order-other-address .linkAct:first").find(".address-id").attr("ref"));
					
					if($("#noadd").css("display") != "none") {
						$(".order-address-box").hide();
					}else{
						$(".order-address-box").show();
					}
				}
			});
	
			$(".lunch-box.order-pay-lunch-box ul").empty('li');
			//如果有餐车记录进行回显
			if( $.cookie(COOKIE_NAME) ){
				var $param = eval($.cookie(COOKIE_NAME));
					$($param).each(
						function() {
							var photo = this.photos.split(",")[0];
							$(".lunch-box.order-pay-lunch-box ul").append(
								'<li>' + 
									'<div class="lunch-photo">' + 
										'<img width="85" height="85" alt="" src="'+this.photos+'" />' + 
									'</div>' + 
									'<div class="lunch-title">' + 
										'<h2>'+this.name+'</h2>' + 
									'</div>' + 
									'<div class="lunch-info">' + 
										'<div class="ui-grid-a">' + 
											'<div class="ui-block-a">' + 
												'<p class="lunch-font12">' + 
													'<font class="org">￥<i class="price">'+this.price+'</i></font>&nbsp;' + 
												'</p>' + 
											'</div>' + 
											'<div class="ui-block-b">' + 
												'<b class="item-btn item-plus">' + 
													'<img height="28" src="/pro/images/plus.png"/>' + 
												'</b>' +
											'<div class="item-btn-box" style="display:none;">' + 
												'<font class="item-text">'+this.count+'</font>' + 
												'<input type="hidden" class="order-pay-type" value="'+ this.id +'"/>' +
												'<b class="item-btn item-minus"><img height="28" src="/pro/images/minus.png"/></b>' + 
											'</div>' + 
										'</div>' + 
									'</div>' +
									'<div class="lunch-info">' +
            	                   		'<div class="ui-grid-a">'+
            	                   			'<div class="ui-block-b" style="float:right;">'+
            	                   				'<select name="level" style="float:right;">'+
            	                   					'<option value="1" '+ (this.level == 1 ? 'selected' : '') +'>小个</option>' +
            	                   					'<option value="2" '+ (this.level == 2 ? 'selected' : '') +'>中个</option>' +
            	                   					'<option value="3" '+ (this.level == 3 ? 'selected' : '') +'>大个</option>' +
            	                   				'</select>' +
            	                   			'</div>' + 
            	                   		'</div>' +
            	                   	'</div>' + 
								'</div>' + 
							'</li>');
							var ids = ($("#order_id").val() && $("#order_id").val().split(",")) || [];
							ids.push(this.id);
							$("#order_id").val(ids.join(","));
							
							var counts = ($("#order_count").val() && $("#order_count").val().split(",")) || [];
							counts.push(this.count);
						   	$("#order_count").val(counts.join(","));
						   	
						   	var levels = ($("#order_level").val() && $("#order_level").val().split(",")) || [];
							levels.push(this.level);
						   	$("#order_level").val(levels.join(","));
						}
					);
			}
			countPrices();
	
			if($.cookie(TYPE) && $.cookie(TYPE) == 1) {
				$(".order-pay-time-wrap").hide();
				$(".manage-title").hide();
				// 一个小时后的时间
				$("#order-pay-hour").empty('option');
				for (var i = 8; i <= 23; i++) {
					var s = i;
					if (i < 10) {
						s = "0" + i;
					}
					$("#order-pay-hour").append('<option value="'+s+'">'+s+'</option>');
				}	
			} else {
				$(".order-pay-time-wrap").show();
				$(".manage-title").show();
			}
	
			$("#noadd").find("a").click(function() {
				window.location.href= "/shipping/create?fromUrl=/order/confirm";
			});
			
			//会话框动态垂直居中
			$(window).resize(function(){ 
			    $(".dialog-box").css({ 
			        top: ($(window).height() - $(".dialog-box").outerHeight())/2 
			    });        
			}); 
			//弹出选择地址会话框
			$(".order-address-default").click(function() {
				$(".dialog-wrap").show();
				$(window).resize();
			});
			//关闭选择地址会话框
			$(".dialog-close-btn").click(function() {
				$(".dialog-wrap").hide();
			});
			
			//改变使用优惠
			$(".medal-select").change(function() {
				countPrices();
			});
			
			//获得用户选择时间
			var $d = new Date();
			$d.setTime($d.getTime() - $d.getHours() * 60*60*1000 - $d.getMinutes() *60*1000 - $d.getSeconds() * 1000);
			var day = new Date();
			var hour = 0;
			var min = 0;
			var html;
			for(var i = 0; i < 7; i++) {
				var _day = new Date();
				_day.setTime($d.getTime() + i * 24 * 60 * 60 * 1000 - $d.getHours() * 60*60*1000 - $d.getMinutes() *60*1000 - $d.getSeconds() * 1000);
				var _date = _day.getDate();
				if(_date < 10) {
					_date = "0" + _date;
				}
				var date = _day.getFullYear() +'-'+ _day.getMonth() + '-' + _date;
				html += '<option value="' + date +'">'+ date +'&nbsp;' + getWeek(_day) +'</option>';
			}
			$("#order-pay-day").html(html);
			
			var $plus = $(".item-plus");
			var $minus = $(".item-minus");
			//增加套餐数量
			$plus.live("click",function(){
				var $priceNum = $(this).next(".item-btn-box").find(".item-text");
				var $toTalNum = parseFloat($priceNum.html()) + 1;
				$priceNum.html($toTalNum);
				countPrices();
			});
			//减少套餐数量
			$minus.live("click",function(){
				var $priceNum = $(this).parents(".item-btn-box").find(".item-text");
				if(parseFloat($priceNum.html()) > 0){
					var $toTalNum = parseFloat($priceNum.html()) - 1;
					if($toTalNum<=0) 
					return;
					$priceNum.html($toTalNum);
					countPrices();
				}
			});
			
			$(".lunch-box.order-pay-lunch-box ul select[name='level']").change(function() {
				var $idParams = $("#order_id").val().split(',');
				var $levelParams = $("#order_level").val().split(',');
				var $thisOrderId = $(this).parents(".order-pay-lunch-box").find(".order-pay-type").val();
				for(var i = 0; i<$idParams.length; i++){
					if($idParams[i] == $thisOrderId){
						$levelParams[i] = $(this).val();
						var newarry = $levelParams.join(',');
						$("#order_level").val(newarry);
					}
				}
			});
	
			//操作数量集合
			function countNumParams(data,num){
				var $idParams = $("#order_id").val().split(',');
				var $countParams = $("#order_count").val().split(',');
				var $thisOrderId = data.parents(".ui-block-b").find(".order-pay-type").val();
				for(var i = 0; i<$idParams.length; i++){
					if($idParams[i] == $thisOrderId){
						$countParams[i] = num;
						var newarry = $countParams.join(',');
						$("#order_count").val(newarry);
					}
				}
			}
			//计算最终的数量和价格和初始化
			function countPrices() {
				//如果数量套餐数量等于0隐藏元素
				$(".item-text").each(function() {
					if(parseFloat($(this).html()) > 0){
						$(this).parents(".item-btn-box").show();
					}else{
						$(this).parents(".item-btn-box").hide();
					}
					//调用改变ID和COUNT数组方法
					countNumParams($(this),parseFloat($(this).html()));
				});
				
				var $toTalPrice = 0;//总价格
				//计算总价格
				$(".price").each(function() {
					var $fPrice = parseFloat($(this).html()) * parseFloat($(this).parents(".lunch-info").find(".item-text").html());
					$toTalPrice = $toTalPrice + $fPrice;
				});
	
				$toTalPrice = $toTalPrice.toFixed(2);
				if($toTalPrice > 0){
					$(".toTalPrice").html($toTalPrice);
				}else{
					$(".toTalPrice").html("0.00");
				}
				$(".order-pay-day-default").html($("#order-pay-day").find("option:selected").text());
				$(".order-pay-time-default").html($("#order-pay-time").find("option:selected").text());
			}
			
			//提交信息
			var state = true;
			$(".order-payment-btn").click(function() {
				$addrMessage = "您还没有选择配送地址";	
				$loginMessage = "请重新登录";		
				if($(".order-address-default").find("input[name='addr_id']").val() == ""){
					alert($addrMessage);
					return false;
				}
			
				if(state){//防止ajax重复提交
					state = false;
				}else{
					return false;
				}
				
			    var $ordertype = "";
			    var TYPE = 'ORDER_BY';
			    if($.cookie(TYPE)){  
			        $ordertype = $.cookie(TYPE);
			    } else {
			    	$ordertype = 1;
			    }
			    
				var $comm_id = 0;
			    var COMM_ID = 'comm_id';
			    if($.cookie(COMM_ID)){  
			        $comm_id = $.cookie(COMM_ID);
			    }
			    
			    var deliveryTime;
			    deliveryTime = $("#order-pay-day").val() + " " + $("#order-pay-hour").val() + ":" + $("#order-pay-min").val() + ":00";
		    	$.ajax({	
					url:" /order/create",
					type:"post",
					data:{
						addr_id:$("#addr_id").val(),
						ids:$(".order-pay-order-id").val(),
						counts:$(".order-pay-order-count").val(),
						levels: $(".order-pay-order-level").val(),
						order_time:deliveryTime,
						order_type:$ordertype,
						commid:$comm_id
					},
					success:function(resp){
						state = true;
						$(".order-payment-btn").attr('disable','disable');
						if(true == resp){
							var COOKIE_NAME = 'mobileCart';
							$.cookie(COOKIE_NAME, null, { path: '/' });  //删除cookie
							window.location.href = "/order/success";
						} else {
							$(".order-payment-btn").removeAttr('disable');
						}
					},
					error:function(data){
						state = fale;
						$(".order-payment-btn").removeAttr('disable');
					}
				});
			});
			$(".addAddrBtn").click(function() {
				$(".order-pay-box-display").hide();
				$(".addAddrBox").show();
			});
		});
		
		//点击后调换地址
		function changeAddr(t) {
			var otherAddr = $(t).find("h2").html();
			var otherTel = $(t).find("p").html();
			var otherId = $(t).find(".address-id").attr("ref");
			
			var mainAddr = $(".order-address-default").find("h2").html();
			var mainTel = $(".order-address-default").find("p").html();
			var mainId = $(".order-address-default").find("#addr_id").val();
			
			$(t).find("h2").html(mainAddr);
			$(t).find("p").html(mainTel);
			$(t).find(".address-id").attr("ref",mainId);
			
			$(".order-address-default").find("h2").html(otherAddr);
			$(".order-address-default").find("p").html(otherTel);
			$(".order-address-default").find("#addr_id").val(otherId);
			$(".dialog-wrap").hide();
		}
		
		function closeIframe(){
			$(".order-pay-box-display").show();
			$(".addAddrBox").hide();
		}
		//用于iframe子页面测试
		function testAddr(){
			return 1;
		}
		function refreshAddrIframe(){
			$(".order-pay-box-display").show();
			$(".addAddrBox").hide();
			refreshAddr();
		}
		$(".refresh").click(function() {
			refreshAddr();
		});
		/*刷新地址*/
		function refreshAddr(){
			$.ajax({
				url:"/shipping/list",
				type:"post",
				success:function(jsonData){
					$(".order-other-address").find("li").remove();
					if(jsonData.length > 0){
						$(".order-address-box").show();
						$("#noadd").remove();
					}
					for(var i=0; i<jsonData.length; i++) {
						$(".order-other-address").append('<li class="linkAct" onclick="changeAddr(this)"><input type="hidden" class="address-id" ref="'+jsonData[i].id+'"/><h2>'+jsonData[i].location+'&nbsp;&nbsp;</h2><p>'+jsonData[i].phone+'</p></li>');
		      		}  
					$(".order-address-default").find("h2").html($("#default-other-addr").find("h2").html());
					$(".order-address-default").find("p").html($("#default-other-addr").find("p").html());
					$(".order-address-default").find("#addr_id").val($("#default-other-addr").find(".address-id").attr("ref"));
				},
				error:function(data){
				}
			});
		}
	</script>
</html>