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
    
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>订单管理</title>
    <link href="${ctxPath}/module/organ/store/css/style_pos.css" rel="stylesheet">
    <link href="${ctxPath}/module/organ/store/css/nodata.css" rel="stylesheet">
</head>
<body style="background: #f7f7f7">
<div class="register_nav">
    <i class="back" id="back"></i>
    <h4 class="font-34">店铺订单</h4>
    <i class="time_setting"></i>
</div>
<input type="hidden" value="${organId }" id="organId"/>
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
<ul class="order_list">
    
</ul>
 <script src="${ctxPath}/module/organ/store/js/order.js"></script>
 
</body>
</html>