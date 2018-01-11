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
    <title>店铺客户</title>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/style_pos.css"/>
    <link href="${ctxPath}/module/organ/store/css/nodata.css" rel="stylesheet">
</head>
<body style="background: #f7f7f7">
<div class="custome_type">
	<div class="register_nav">
	    <i class="back" id="custom_back"></i>
	    <h4 class="font-34">我的客户</h4>
	</div>
		<input type="hidden" value="${organId }" id="organId"/>
		<input type="hidden" value="" id="page"/>
		<input type="hidden" value="" id="pages"/>
		<input type="hidden" value="0" id="type"/>
	<ul class="custom_tab">
		<li class="active">预约客户 (${orderHisIds })</li>
		<li>会员客户 (${vip })</li>
		<li >关注客户 (${favorOrgan })</li>
	</ul>
</div>	

<ul class="custom_list" id="custom_list">
</ul>

<script src="${ctxPath}/module/organ/store/js/custom.js"></script>
</body>
</html>