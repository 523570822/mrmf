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
<body class="myMessage">
	<div class="nav">
	    <i class="back" id="back"></i>
	    <h4 class="font-34">我的消息</h4>
	</div>
	<input type="hidden" value="${userId }" id="user_id" name="userId"/>
	<input type="hidden" value="" id="page"/>
	<input type="hidden" value="" id="pages"/>
	<input type="hidden" value="1" id="type"/>
	<input type="hidden" value="" id="sysMessage"/>
	<div class="messTitle">
		<div class="title-1 sel_message">
			<h4 class="title-2" id="pMess">个人消息</h4>
		</div>
		<div class="title-1">
			<h4 class="title-2" id="cMess">系统消息</h4>
		</div>
	</div>
	<ul id="message_list"></ul>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/js/message_page.js"></script>
</body>
</html>