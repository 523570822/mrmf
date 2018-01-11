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
<title>消息通知</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/module/resources/css/technician/style_mainPage.css"/>
<link rel="stylesheet" type="text/css" href="${ctxPath}/module/organ/store/css/stroe.css"/>
<link href="${ctxPath}/module/organ/store/css/nodata.css" rel="stylesheet">
</head>
<body class="myMessage">
<div class="custome_type">
	<div class="nav">
	    <i class="back" id="messageback"></i>
	    <h4 class="font-34">我的消息</h4>
	</div>
	<input type="hidden" value="${organId }" id="organId"/>
	<input type="hidden" value="" id="page"/>
	<input type="hidden" value="" id="pages"/>
	<input type="hidden" value="1" id="type"/>
	<div class="messTitle">
		<div class="title-1 sel_message">
			<h4 class="title-2" id="pMess" >个人消息</h4>
		</div>
		<div class="title-1 nosel_message">
			<h4 class="title-2" id="cMess" >系统消息</h4>
		</div>
	</div>
</div>	
	<ul id="message_context" class="message_context"></ul>
<script src="${ctxPath}/module/organ/store/js/message.js"></script>
</body>
</html>