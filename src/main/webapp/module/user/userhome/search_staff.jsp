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
<input type="hidden" value="${longitude}" name="longitude" id="longitude"/>
<input type="hidden" value="${latitude }" name="latitude" id="latitude"/>
<input type="hidden" id="userId" value="${userId}"/>
    <div class="common_nav">
   		<i onclick="window.history.go(-1);"></i>
        <div class="search_input">
           <input type="text" placeholder="搜索技师" />
        </div>
        <div class="search_icon"><i></i></div> 
    </div>
		 <input type="hidden" value="" id="pages"/>
	     <input type="hidden" value="" id="page"/>
	     <ul class="list" id="staff_list"></ul>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/user/userhome/js/star.js"></script>
</body>
</html>