<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/style_user_index.css"/>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/swiper.min.css" />
</head>
<body class="bg_gray">
	<input type="hidden" value="${homeType}" id="homeType">
    <div class="hair_nav">
        <i class="back" onclick="window.location.href='${ctxPath}/w/home/toHomePage'"></i>
        <h4 class="font-34" id="title">${homeType}</h4>
        <i class="search"></i>
    </div>
    <div class="beautiful_sort_type">
        <ul>
            <li class="hot_sort_type b_active">热度排序</li>
            <li class="price_sort_type">价格排序</li>
        </ul>
    </div>
     <input type="hidden" value="" id="pages"/>
     <input type="hidden" value="" id="page"/>
     <div class="hair_list" id="hair_list">
     </div>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/user/userhome/js/typeProgram.js"></script>
</body>
</html>