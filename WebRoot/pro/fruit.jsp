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
		<link type="text/css" rel="stylesheet" href="/pro/css/lunch.css"/>
		<script>
		    //fastclick初始化
		    window.addEventListener('load', function () {
		        FastClick.attach(document.body);
		    }, false);
		</script>
	</head>
	<body>
		<div class="container">
		  	<div class="header" id="lunch-header"> 
			  	<a href="/" class="left linkAct">
			  		<img class="m-t-4" src="/pro/images/arrow-left.png" width="30"/>
			  	</a>
			    <h2>
			    	<c:choose>
			    		<c:when test="${community ne null}">
			    			${community.comm_name}
			    		</c:when>
			    		<c:otherwise>
							会员订餐			    		
			    		</c:otherwise>
			    	</c:choose>
			   	</h2>
			    <a href="tel:${orderPhone}" class="right linkAct"><img  src="/pro/images/tel-white.png" width="30"/></a> 
		    </div>
		  	<div class="menu-wrap">
		    	<div class="menu fixed-menu-on">
		      		<div class="ui-grid-a">
		        		<div class="ui-block-a"> 
		        			<a href="javascript:void(0)" id="order-b1" class="on">
		        				<font>鲜美水果</font><b id="order-tip1" style="display:none">0</b>
		        			</a> 
		        		</div>
			        	<div class="ui-block-b"> 
			        		<a href="javascript:void(0)" id="order-b2">
			        			<font>精品果盘</font><b id="order-tip2" style="display:none">0</b>
			        		</a> 
			        	</div>
	      			</div>
		    	</div>
		  	</div>
		  	<div class="lunch-banner-box"></div>
		  	<div class="lunch-content" id="lunch-content"> 
		    	<!-- 商务套餐 -->
		    	<div class="lunch-box" id="order-b1box">
		      		<ul></ul>
		    	</div>
		    	<!-- 精品果盘 -->
		    	<div class="lunch-box" id="order-b2box" style="display:none;">
		      		<ul></ul>
		    	</div>
		  	</div>
		  	<div class="clr"></div>
		  	<div class="order-count-box">
		    	<form action="/order/confirm" method="post" id="order_pay">
		      		<a class="waste-icon left" href="javascript:void(0)"><img height="25" src="/pro/images/waste.png"/></a>
		      		<div class="order-count-text left">总计:<span class="org">￥<font class="toTalPrice">0</font></span></div>
		      		<input type="hidden" name="data" value="" id="order_data">
		      		<div class="settlement-box">
		        		<input type="submit" id="settlement" class="button btn-gray" value="选好了"/>
		      		</div>
		    	</form>
		  	</div>
		</div>
	</body>
	<script type="text/javascript">
		$(function(){
		    var GPS_LOCATION_LNG = 'GPS_LOCATION_LNG';
		    var GPS_LOCATION_LAT = 'GPS_LOCATION_LAT';
		    var COMM_ID = 'comm_id';
		    var COOKIE_NAME = 'mobileCart';
		    var position = $.cookie(GPS_LOCATION_LNG);
		    var url = location.href;
		    var loc = url.indexOf("=");
		    var id = url.substring(url.indexOf("=") + 1);
		    if (loc && loc > -1) {
	        	//根据社区ID读取水果列表
		    	if (id == null || id == "") {
		    		window.location.href = "/community";
		    	}
		       	$.ajax({
					url:"/fruit/list",
				  	type:"get",
				  	data:{commId:id},
				  	success:function(typeData){
				    	$("#order-b1box ul").empty('li');
				        	for(var i=0; i<typeData.length; i++) {  
	                       		var photos = typeData[i].photos.split(",")[0];
	  					    	$("#order-b1box>ul").append(
	  					    		'<li>' + 
		                            	'<div class="lunch-photo"><img width="85" height="85" alt="" src="'+photos+'"></div>' + 
		                                '<div class="lunch-title"><h2>'+typeData[i].name+'</h2></div>'+
	    	                            '<div class="lunch-info"><div class="ui-grid-a"><div class="ui-block-a">'+
	        	                        '<p class="lunch-font12"><font class="org">￥<i class="price">'+typeData[i].price+'</i></font>&nbsp;<span>￥<font class="font-through">'+typeData[i].display_price+'</font></span></p></div>'+ 
	            	                    '<div class="ui-block-b"> <b class="item-btn item-plus"><img height="28" src="/pro/images/plus.png"/></b><div class="item-btn-box" style="display:none;"> <font class="item-text">0</font> <span style="display:none;" class="item-id">55</span> <b class="item-btn item-minus"><img height="28" src="/pro/images/minus.png"/></b></div></div></div></div>'+
	                	            '</li>');
		        			}
			    			var COOKIE_NAME = 'mobileCart';
				    		//如果有餐车记录进行回显
				    		if( $.cookie(COOKIE_NAME) ){
				    			var $param = eval($.cookie(COOKIE_NAME));
				    			$(".item-id").each(function() {
				    				var $itemId = $(this).html();
			    					var $itemText = $(this).prev(".item-text");
			    					$($param).each(
				    					function() {
				    						if($itemId == this.id){
				    							$itemText.html(this.count);
				    						}
			    						}
			    					);
			    					countPrices();
			    				});
				    		}
				        },
				        error:function(json){
				          alert("服务器穿越了~ 再试试看");
			    	    }
			      	});
		    	} else {
					$.ajax({
			      	url:"/fruit/list",
			      	type:"post",
			      	dataType:'json',
			      	data:{lon:$.cookie(GPS_LOCATION_LNG),lat:$.cookie(GPS_LOCATION_LAT)},
			      	success:function(typeData){
			        	// 获取信息
			          	$("#order-b1box ul").empty('li');
				            for(var i=0; i<typeData.length; i++) {  
				            	var photos = typeData[i].photos.split(",")[0];
				              	$("#order-b1box>ul").append(
				               		'<li>' + 
			                        	'<div class="lunch-photo"><img width="85" height="85" alt="" src="'+photos+'"></div>' + 
		                                '<div class="lunch-title"><h2>'+typeData[i].name+'</h2></div>'+
	                                    '<div class="lunch-info"><div class="ui-grid-a"><div class="ui-block-a">'+
	                                    '<p class="lunch-font12"><font class="org">￥<i class="price">'+typeData[i].price+'</i></font>&nbsp;<span>￥<font class="font-through">'+typeData[i].display_price+'</font></span></p></div>'+ 
	                                    '<div class="ui-block-b"> <b class="item-btn item-plus"><img height="28" src="/pro/images/plus.png"/></b><div class="item-btn-box" style="display:none;"> <font class="item-text">0</font> <span style="display:none;" class="item-id">'+typeData[i].id+'</span> <b class="item-btn item-minus"><img height="28" src="/pro/images/minus.png"/></b></div></div></div></div>'+
	                               '</li>');
								$.cookie(COMM_ID, typeData[i].communityid);
			    	        }
			    			//如果有餐车记录进行回显
			    			if( $.cookie(COOKIE_NAME) ){
			    				var $param = eval($.cookie(COOKIE_NAME));
			    				$(".item-id").each(function() {
				    				var $itemId = $(this).html();
			    					var $itemText = $(this).prev(".item-text");
			    					$($param).each(
				    					function() {
				    						if($itemId == this.id){
				    							$itemText.html(this.count);
				    						}
			    						}
			    					);
			    					countPrices();
			    				});	
				    		}
				        },
			        	error:function(json){
			          		alert("服务器穿越了~ 再试试看");
			        	}
					});
		    	}
	
			    function request(paras){  
			   		var url = location.href;   
			      	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");   
			      	var paraObj = {};
			      	for (i=0; j=paraString[i]; i++){   
			      		paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);   
			      	}   
			      	var returnValue = paraObj[paras.toLowerCase()];   
			      	if(typeof(returnValue)=="undefined"){   
			      		return "";   
			      	} else {   
			        	return returnValue;  
			      	}
			    }
			
				//Tab切换
				$title = $(".menu").find("a");
				$title.click(
					function() {
						$box = $(this).attr("id") + "box";
						$title.each(
							function() {
								$title.attr("class","");
							}
						);
						$(this).attr("class","on");
						$(".lunch-box").each(
							function() {
							$(this).hide();
							if($(this).attr("id") == $box){
								$(this).show();
								}
							}
						);
					}
				);
			
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
						$priceNum.html($toTalNum);
						countPrices();
					}
				});
	
				//清空套餐
				$(".waste-icon").click(function() {
					if (confirm('是否要清空餐车？')){
						$(".item-text").each(function() {
							$(this).html("0");
						});
						countPrices();
					}
				});
			
				//计算最终的数量和价格和cookie操作
				function countPrices(){
					var $tcToTalNum = 0;//鲜美水果总数
					var $tpToTalNum = 0;//汤品套餐总数
					var $jsToTalNum = 0;//酒水套餐总数
					//鲜美水果计数
					$("#order-b1box").find(".item-text").each(function() {
						var $tip = $("#order-tip1");
						$tcToTalNum = parseFloat($(this).html()) + $tcToTalNum;
						if($tcToTalNum == 0){
							$("#settlement").attr("class","button btn-gray");
							$tip.hide();
						}else{
							$("#settlement").attr("class","button btn-org");
							$tip.show();
						}
						if(parseFloat($(this).html()) == 0){
							$(this).parents(".item-btn-box").hide();
						}else{
							$(this).parents(".item-btn-box").show();
						}
						$tip.html($tcToTalNum);
					});
					//汤品套餐计数
					$("#order-b2box").find(".item-text").each(function() {
						var $tip = $("#order-tip2");
						$tpToTalNum = parseFloat($(this).html()) + $tpToTalNum;
						if($tpToTalNum == 0){
							$tip.hide();
						}else{
							$tip.show();
						}
						if(parseFloat($(this).html()) == 0){
							$(this).parents(".item-btn-box").hide();
						}else{
							$(this).parents(".item-btn-box").show();
						}
						$tip.html($tpToTalNum);
					});
					
					//酒水套餐计数
					$("#order-b3box").find(".item-text").each(function() {
						var $tip = $("#order-tip3");
						$jsToTalNum = parseFloat($(this).html()) + $jsToTalNum;
						if($jsToTalNum == 0){
							$tip.hide();
						}else{
							$tip.show();
						}
						if(parseFloat($(this).html()) == 0){
							$(this).parents(".item-btn-box").hide();
						}else{
							$(this).parents(".item-btn-box").show();
						}
						$tip.html($jsToTalNum);
					});
					
					var $toTalPrice = 0;//总价格
					//计算总价格
					$(".price").each(function() {
						//如果商品已售罄，不能增加减少商品，并且把该商品已有的数量清零
						if($(this).parents(".lunch-info").find(".lunch-sold").length > 0) {
							$(this).parents(".lunch-info").find(".item-text").html("0");
							$(this).parents(".lunch-info").find("b").hide();
							$(this).parents(".lunch-info").find(".item-btn-box").hide();
						}
						var $fPrice = parseFloat($(this).html()) * parseFloat($(this).parents(".lunch-info").find(".item-text").html());
						$toTalPrice = $toTalPrice + $fPrice;
					});
					$toTalPrice = $toTalPrice.toFixed(2);
					$(".toTalPrice").html($toTalPrice);
					//将购物车数据转换为json格式
					var cartTemp = new Object();
					var cartItem = "[";
					$(".lunch-box").find("li").each(
						function() {
							$cookieNum = $(this).find(".item-text").html();
							$cookieId = $(this).find(".item-id").html();
							$photo = $(this).find(".lunch-photo img").attr("src");
							$price = $(this).find(".price").html();
							cartTemp.count = $cookieNum;
							cartTemp.id = $cookieId;
							cartTemp.photos = $photo;
							cartTemp.price = $price;
							cartTemp.name = $(this).find(".lunch-title h2").html();
							if($cookieNum != 0) {
								if(cartItem == "["){
									cartItem = cartItem + JSON.stringify(cartTemp);
								}else{
									cartItem = cartItem +","+JSON.stringify(cartTemp);
								}
							}
						}
					);
					cartItem = cartItem+"]";
					//cookie操作
					$.cookie(COOKIE_NAME, null, { path: '/' });
					if(cartItem != "[]"){
						$.cookie(COOKIE_NAME, cartItem , { path: '/', expires: 3 });  
					}
				}
	
				//去结算
				$("#order_pay").submit(function() {
					if( $.cookie(COOKIE_NAME) ){
						var $param = eval($.cookie(COOKIE_NAME));
						$("#order_data").val(JSON.stringify($param));//json数据解析，表单提交
					}
					if($("#order-tip1").html() == "0") {
						alert("请至少选择一份套餐");
						return  false;
					}
					return true;
				});
	   	});
	</script>
</html>