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
        <meta name="keywords" content="嘿狗社区"/>
        <meta name="description" content="嘿狗社区。"/>
        <title>嘿狗社区服务平台</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimum-scale=1.0">
        <link type="text/css" rel="stylesheet" href="/pro/css/common.css"/>
        <script src="http://api.map.baidu.com/api?v=1.3" type="text/javascript"></script>
        <script type="text/javascript" src="/pro/js/jquery-1.8.3.min.js"></script>
        <script type="text/javascript" src="/pro/js/jquery.cookie.js"></script>
        <script type="text/javascript" src="/pro/js/fastclick.min.js"></script>
        <script type="text/javascript" src="/pro/js/base.js"></script>
        <script type="text/javascript">
            //fastclick初始化
            window.addEventListener('load', function () {
                FastClick.attach(document.body);
            }, false);
        </script>
        <link type="text/css" rel="stylesheet" href="/pro/css/index.css"/>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="logo">
                    <img width="35" src="/pro/images/logo.png"/>
                    <h1>嘿狗社区</h1>
                </div>
                <div class="header-login">
                	<c:choose>
                		<c:when test="${sessionUser eq null}">
                			<a class="linkAct" href="/user/login">登录</a>
                    		<a class="linkAct" href="/user/register">注册</a>
                		</c:when>
                		<c:otherwise>
                			<a class="linkAct" href="/user/profile">${sessionUser.username}</a>
                		</c:otherwise>
                	</c:choose>
                </div>
            </div>
            <div class="index-content">
                <a class="linkActImg" id="lunch" href="/fruit">
                    <img src="/pro/images/index1.jpg"/>
                </a>
                <a class="linkActImg" href="/user/profile">
                    <img src="/pro/images/index3.jpg"/>
                </a>
                <a class="linkActImg" id="subscribe" href="/community">
                    <img src="/pro/images/index1.jpg"/>
                </a>
                <a class="linkActImg" href="tel:13806101602">
                    <img src="/pro/images/index-tel.png"/>
                </a>
            </div>
            <div class="down-box" style="display:none;">
                <div class="ui-grid-a">
                    <div class="ui-block-a">
                        <img src="/pro/images/logo.png" height="50"/>
                        <p>下载APP<br/>点餐更优惠</p>
                    </div>
                    <div class="ui-block-b">
                        <a href="javascript:void(0)"><img src="/pro/images/chaha_03.png" class="download-close-btn" width="35"/></a>
                        <a href="javascript:void(0)" class="download-btn">立即下载</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        $(function () {
            var GPS_LOCATION_LNG = 'GPS_LOCATION_LNG';
            var GPS_LOCATION_LAT = 'GPS_LOCATION_LAT';

            if (!$.cookie(GPS_LOCATION_LNG)) {
                var date = new Date();
                date.setTime(date.getTime() + (30 * 60 * 1000));
                $.cookie(GPS_LOCATION_LNG, 120.2, { path: '/', expires: date });
                $.cookie(GPS_LOCATION_LAT, 30.33, { path: '/', expires: date });
                if (window.navigator.geolocation) {
                    var options = {
                        enableHighAccuracy: true
                    };
                    window.navigator.geolocation.getCurrentPosition(handleSuccess, handleError, options);
                } else {
                    alert("浏览器不支持html5来获取地理位置信息");
                }

                function handleSuccess(position) {
                	console.dir(position);
                    // 获取到当前位置经纬度  本例中是chrome浏览器取到的是google地图中的经纬度
                    var lng = position.coords.longitude;
                    var lat = position.coords.latitude;
                    // 调用百度地图api显示
                    var ggPoint = new BMap.Point(lng, lat);
                    // 将google地图中的经纬度转化为百度地图的经纬度
                    BMap.Convertor.translate(ggPoint, 2, function (point) {
                        var marker = new BMap.Marker(point);
                        var date = new Date();
                        date.setTime(date.getTime() + (30 * 60 * 1000));
                        $.cookie(COOKIE_GPS_NAME, point, { path: '/', expires: date });
                        //alert(point.lng + "," + point.lat);
                    });
                }

                function handleError(error) {
                	console.dir(error);
                    switch (error.code) {
                        case error.PERMISSION_DENIED:
                            alert("User denied the request for Geolocation.");
                            break;
                        case error.POSITION_UNAVAILABLE:
                            alert("Location information is unavailable.");
                            break;
                        case error.TIMEOUT:
                            alert("The request to get user location timed out.");
                            break;
                        case error.UNKNOWN_ERROR:
                            alert("An unknown error occurred.");
                            break;
                    }
                }
            }
        });

        var TYPE = 'ORDER_BY';
        $("#lunch").click(function () {
            $.cookie(TYPE, 1);
        });

        $("#subscribe").click(function () {
            $.cookie(TYPE, 2);
        });

        $(".download-close-btn").click(function () {
            $(".down-box").hide();
        });
        $(".download-btn").click(function () {
            alert("尽请期待~");
        });
    </script>
</html>