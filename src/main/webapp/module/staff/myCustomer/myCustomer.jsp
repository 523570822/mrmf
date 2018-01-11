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
<body style="background: #f8f8f8;">
	<div class="nav">
	    <i class="back" id="back"></i>
	    <h4 class="font-34">我的客户</h4>
	 </div>
	 <div class="messTitle">
	 	<div class="customer-title sel_message">
			<h4 class="customer-title2" id="pMess" >预约客户(${map['appoint'] })</h4>
		</div>
		<div class="customer-title ">
			<h4 class="customer-title2" id="cMess" >会员客户(${map['member'] })</h4>
		</div>
		<div class="customer-title ">
			<h4 class="customer-title2" id="fMess" >关注客户(${map['follow'] })</h4>
		</div>
	 </div>
	 <input type="hidden" value="${staffId }" id="staff_id" name="staffId">
	 <input type="hidden" value="" id="page"/>
	 <input type="hidden" value="" id="pages"/>
	 <input type="hidden" value="1" id="type"/>
	 
	 <div class="list">
	 	<ul id="my_customer"></ul>
	 </div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/myCustomer/js/page.js"></script>
</body>
</html>