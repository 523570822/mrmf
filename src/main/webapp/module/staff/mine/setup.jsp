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
    <title>设置</title>
   	<link href="${ctxPath}/module/resources/css/style_pos.css" rel="stylesheet">
</head>
<body style="background: #f7f7f7">
<div class="register_nav">
    <i class="back" id="back"></i>
    <h4 class="font-34">我的设置</h4>
	<input type="hidden" value="${staffId }" id="staff_id"/>
	<input type="hidden" value="setup" id="status"/>
</div>
<div class="bg_20"></div>
<ul class="busytime_setting">
    <li onclick="window.location.href='${ctxPath}/w/userMy/toMyUs'">
        <div class="col-7 ">关于任性猫</div>

        <div class="col-2 right"><i></i></div>
    </li>
    <li id="tofeedback">
        <div class="col-7 ">意见反馈</div>
        <div class="col-2 right"><i></i></div>
    </li>
    <li id="setpaypwdphone">
        <div class="col-7 ">支付密码设置</div>
        <div class="col-2 right"><i></i></div>
    </li>
    <!-- <li id="setpaypwdphone">
        <div class="col-7"></div>
        <div class="col-2 right">支付密码设置</div>

        <div class="col-2"><i></i></div>
    </li> -->
</ul>
<script src="${ctxPath}/module/staff/js/mine.js"></script>
</body>
</html>