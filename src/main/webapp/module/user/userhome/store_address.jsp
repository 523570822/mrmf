<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
        body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
    </style>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=UbLgidkKinZvi3ksTu1Q0hDAhWHM8Zi6"></script>
    <title>地图展示</title>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/style_user_index.css"/>
</head>
<style>

</style>
<body>
<div class="common_nav">
    <i onclick="window.history.go(-1);"></i>
    <h4 class="font-34">店铺地址</h4>
</div>
<div id="allmap"></div>
 <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
 <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
 <script type="text/javascript">
    //百度地图API功能116.417854,39.921988
    var map = new BMap.Map("allmap");
    var point = new BMap.Point("${organ.gpsPoint.longitude}","${organ.gpsPoint.latitude}");
    var myIcon = new BMap.Icon("${ctxPath}/module/resources/images/icon_shop_map.png", new BMap.Size(50,50));
    var marker = new BMap.Marker(point,{icon:myIcon});  // 创建标注
    map.addOverlay(marker);              // 将标注添加到地图中
    var pt = new BMap.Point("${longitude}", "${latitude}");
    var myIcon2 = new BMap.Icon("${ctxPath}/module/resources/images/icon_location_map.png", new BMap.Size(36,36));
    var marker2 = new BMap.Marker(pt,{icon:myIcon2});  // 创建标注
    map.addOverlay(marker2);
    map.centerAndZoom(point, 15);
    //设置提示框中的内容
    var mapTitle="<img src='${ossImageHost}${organ.logo}"+"@!style400"+"' class='map_title_logo'/><span class='map_title'>${organ.name}</span>";
    var mapContent="<div class='maptxt'><span>地址:</span><p>${organ.address}</p></div>";
    var opts = {
        width : 250,     // 信息窗口宽度
        height: 60,     // 信息窗口高度
        title : mapTitle , // 信息窗口标题
        enableMessage:true,//设置允许信息窗发送短
    };
    var infoWindow = new BMap.InfoWindow(mapContent, opts);  // 创建信息窗口对象
    marker.addEventListener("click", function(){
        map.openInfoWindow(infoWindow,point); //开启信息窗口
    });
  </script>
</body>
</html>

