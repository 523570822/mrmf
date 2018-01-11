<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <meta name="description" content="This is  a .."/>
<title>任性猫</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/module/resources/css/technician/style_mainPage.css"/>
</head>
<body class="myEstimation">
	<div class="nav">
	    <i class="back" onclick="window.history.go(-1)"></i>
	    <h4 class="font-34">客户评价</h4>
	</div>
	<input type="hidden" value="${staffId}" id="staffId"/>
	<input type="hidden" value="" id="page"/>
	<input type="hidden" value="" id="pages"/>
	<ul id="comment_content">
	</ul>
    <script src="${ctxPath}/module/user/userhome/js/comment.js"></script>  
</body>
</html>