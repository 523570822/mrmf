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
    <%-- <style type="text/css">
         html,body{margin:0;padding:0;}
         .iw_poi_title {color:#CC5522;font-size:14px;font-weight:bold;overflow:hidden;padding-right:13px;white-space:nowrap}
         .iw_poi_content {font:12px arial,sans-serif;overflow:visible;padding-top:4px;white-space:-moz-pre-wrap;word-wrap:break-word}
     </style>--%>
    <script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.1&services=true"></script>
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=yes" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>

    <link rel="stylesheet" href="${ctxPath}/module/resources/css/swiper.min.css"/>
    <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
    <link href="${ctxPath}/module/organ/store/css/nodata.css" rel="stylesheet">
</head>
<body class="bg_gray">
<nav class="nav">
    <div class="col-3">
        <i class="back" onclick="window.history.go(-1)"></i>
    </div>
    <div class="col-4">
        <h4 class="font-34">工位租赁协议</h4>
    </div>
    <div class="col-3"></div>
</nav>


<section class="store-title">
    <h3>工位的租赁协议</h3>
    <p>工位的租赁协议</p>
    <div class="store-price">
    </div>
</section>
</body>
</html>
