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
	 <input type="hidden" value="${user._id}" id="user_id" name="userId">
	 <input type="hidden" value="" id="page"/>
	 <input type="hidden" value="" id="pages"/>
	 <input type="hidden" value="1" id="type"/>
	 <input type="hidden" value="myOrder" id="status">
	<div class="nav">
	    <i class="back" id="back"></i>
	    <h4 class="font-34">我的订单</h4>
	 </div>
	 <div class="messTitle">
	 	<div class="order-title sel_message">
			<h4 class="customer-title2" id="pMess" style="margin-left: 2rem;" >全部</h4>
		</div>
		<div class="order-title ">
			<h4 class="customer-title2" id="cMess" >预约中</h4>
		</div>
		<div class="order-title ">
			<h4 class="customer-title2" id="cMess" >待支付</h4>
		</div>
		<div class="order-title ">
			<h4 class="customer-title2" id="cMess" >待评价</h4>
		</div>
	 </div>
	 <div class="list">
	 	<ul id="order_list"></ul>
	 </div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/myOrder/js/order_page.js"></script>
<script src="${ctxPath}/module/user/usermy/myOrder/js/my_order.js"></script>

</body>
</html>