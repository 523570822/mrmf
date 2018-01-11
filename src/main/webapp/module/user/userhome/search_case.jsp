<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/style_user_index.css"/>
</head>
<body class="bg_gray">
<input type="hidden" value="${homeType}" id="homeType">
    <div class="common_nav">
   		<i onclick="window.history.go(-1);"></i>
        <div class="search_input">
           <input type="text" placeholder="搜索案例" id="case" />
        </div>
        <div class="search_icon"><i></i></div>
    </div>
    <input type="hidden" value="" id="pages1"/>
    <input type="hidden" value="" id="page1"/>
    <div class="hair_list" id="hair_list2"></div>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/user/userhome/js/case.js"></script>
</body>
</html>