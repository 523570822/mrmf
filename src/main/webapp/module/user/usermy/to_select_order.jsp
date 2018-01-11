<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <meta name="description" content="This is  a .."/>
<title>我的订单</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/module/staff/css/style_mainPage.css"/>
</head>
<body class="orderList">
	 <input type="hidden" value="" id="page"/>
	 <input type="hidden" value="" id="pages"/>
	 <input type="hidden" value="myOrder" id="status">
	<div class="nav">
	    <i class="back" onclick="window.history.go(-1)"></i>
	    <h4 class="font-34">需理赔订单</h4>
	 </div>
	 <div class="list">
	 	<ul id="order_list"></ul>
	 </div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/js/order_page.js"></script>
<script src="${ctxPath}/module/user/usermy/myOrder/js/my_order.js"></script>

</body>
</html>