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
<body class="myMessage" style="background: #fff;">
	<div class="nav">
	    <i class="back" onclick="window.history.go(-1)"></i>
	    <h4 class="font-34">理赔规则说明</h4>
	</div>
	<input type="hidden" value="${userId }" id="user_id" name="userId"/>
	<input type="hidden" value="" id="page"/>
	<input type="hidden" value="" id="pages"/>
	<input type="hidden" value="1" id="type"/>
	<!-- <div class="messTitle">
		<div class="title-1 sel_message">
			<h4 class="title-2" id="pMess">理赔规则</h4>
		</div>
		<div class="title-1">
			<h4 class="title-2" id="cMess">申诉处理人</h4>
		</div>
	</div> -->
	<ul id="compensate_list"></ul>
	<!-- 
	<ul id="message_list">
		<c:forEach var="msg" items="${fpi.data }">
			<li>
				<div class="mess">
					${msg.content }
				</div>
				<div class="time">
					${msg.createTimeFormat }
				</div>
			</li>		
		</c:forEach>
	</ul> -->
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/js/compensate_rule_page.js"></script>
</body>
</html>