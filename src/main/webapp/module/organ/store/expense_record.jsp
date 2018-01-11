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
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/user/css/my_style.css"/>
</head>
<body class="bg_gray">
<%-- <input type="hidden" value="${userId }" id="userId"/> --%>
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
    <div class="common_nav">
        <i onclick="window.history.go(-1)"></i>
        <h4 class="font-34">我的消费记录</h4>
    </div>
    <div class="bg_gray" style="width:100%;height:0.7rem;">
    </div>
    <script src="${ctxPath}/module/user/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/organ/store/js/expense_record.js"></script>
</body>
</html>