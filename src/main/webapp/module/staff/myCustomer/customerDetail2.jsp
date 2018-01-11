<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML >
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
	<title>任性猫</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/module/staff/css/style_mainPage.css"/>
</head>
<body class="customerDetail">
<input type="hidden" value="${userId }" id="user_id" name="userId">
<input type="hidden" value="${staffId }" id="staff_id" name="staffId">
	 <input type="hidden" value="" id="page"/>
	<input type="hidden" value="" id="pages"/>
	<div class="nav">
	    <i class="back" onclick="window.history.go(-1)"></i>
	    <h4 class="font-34">会员消费情况</h4>
	 </div>
	 <div class="title">
	 	<div class="cphoto">
	 		<c:if test="${!empty user.avatar }">
	 			<img src="${ossImageHost}${user.avatar}@!avatar">
	 		</c:if>
	 		<c:if test="${empty user.avatar }">
	 			<img src="${ctxPath }/module/resources/images/nopicture.jpg">
	 		</c:if>
	 	</div>
	 	<div class="mess">
	 		<h4 class="h5 font-34">${user.nick }</h4>
	 		<h4 class="h4" style="margin-bottom: 0.2rem;">电话<span>${user.phone }</span></h4>
	 		<h4 class="h4"></h4>
	 	</div>
	 </div>
	 <div class="clist">
	 	<ul id="customer_detail"></ul>
	 </div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/myCustomer/js/detail_page.js"></script>
</body>
</html>