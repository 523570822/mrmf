<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
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
<title>会员卡门店</title>
<link rel="stylesheet" href="${ctxPath}/module/user/css/my_style.css"/>
</head>
<body class="bg_gray">
<input type="hidden" value="${cardId }" id="card_id">
<input type="hidden" value="${userId }" id="user_id">
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
	<!-- common_nav start -->
	<div class="common_nav">
        <i id="back"></i>
        <h4 class="font-34">会员卡门店</h4>
    </div>
    <!-- common_nav end -->
    
    <!-- stores_list start -->
    <ul class="stores_list" id="stores_list">
		
    </ul>
    <!-- stores_list end -->

<script src="${ctxPath}/module/user/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/myvip/js/card_stores_page.js"></script>
</body>
</html>