<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=cyuWAntIDuQM6TXAUrPRuGvosDUFZoh5"></script>
</head>
<body class="bg_gray">
    <div class="common_nav">
        <i id="appoint_store_map_back"></i>
        <h4 class="font-34">店铺位置</h4>
    </div>
    <div id="store_container" ></div>
    <script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="${ctxPath}/module/user/userstore/js/appoint_info.js"></script>
    <script type="text/javascript">
        // 百度地图API功能
        var map = new BMap.Map("store_container");
        var longitude = ${organ.gpsPoint.longitude};
        var latitude = ${organ.gpsPoint.latitude};
        var gps=GPS.bd_encrypt(latitude,longitude);
		//alert(gps.lon+"::"+gps.lat);
        var point = new BMap.Point(gps.lon, gps.lat);  //当前店铺所在的位置
		
        var storeIcon = new BMap.Icon("${ctxPath}/module/user/images/icon_shop_map.png", new BMap.Size(50,50));
        var opts = {
            width : 0,     // 信息窗口宽度
            height: 0,     // 信息窗口高度
        }
        var marker = new BMap.Marker(point,{icon:storeIcon});

        map.centerAndZoom(point, 15);

        map.addOverlay(marker);
    </script>
</body>
</html>