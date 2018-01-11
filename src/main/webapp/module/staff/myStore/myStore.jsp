<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
<title>我的店铺</title>
<link href="${ctxPath}/module/staff/css/style_myStore.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8;">
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="mystore" id="status" name="status">
	 <div class="nav">
	    <i class="back" id="back"></i>
	    <h4 class="font-34">我的店铺</h4>
	</div>
	<div class="mine_content">
		<ul id="myStore_list">
		</ul>
	</div>
	<div class="bottom">
	<form action="" method="post" id="mystore_form">
		<input type="hidden" value="${staffId }" id="staff_id" name="staffId">
		<input type="hidden" value="1667920738524089172" id="city_id" name="cityId">
		<button class="button font-34" onclick="addStore()">添加店铺</button>
	</form>
	</div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script src="${ctxPath}/module/staff/myStore/js/myStore_page.js"></script>