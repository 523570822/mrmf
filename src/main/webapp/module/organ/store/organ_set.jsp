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
    <title>设置</title>
   	<link href="${ctxPath}/module/resources/css/style_pos.css" rel="stylesheet">
</head>
<body style="background: #f7f7f7">
<div class="register_nav">
    <i class="back" id="back"></i>
    <h4 class="font-34">店铺设置</h4>
	<input type="hidden" value="${organId }" id="organId"/>

</div>
<div class="bg_20"></div>
<ul class="busytime_setting">
    <li id="for_us">
        <!-- <div class="col-1"></div> -->
        <div class="col-9 ">关于我们</div>

        <div class="col-1 "><i></i></div>
    </li>
    <li id="tofeedback">
        <!-- <div class="col-1"></div> -->
        <div class="col-9 ">信息反馈</div>
        <div class="col-1"><i></i></div>
    </li>
    <!-- <li id="setpaypwdphone">
        <div class="col-1"></div>
        <div class="col-9 ">支付密码设置</div>

        <div class="col-1"><i></i></div>
    </li> -->
    <li id="twoCode" style="cursor: pointer">
        <div class="col-9">店铺二维码</div>
        <div class="col-1"><i></i></div>
    </li>
</ul>
<script src="${ctxPath}/module/organ/store/js/organset.js"></script>
</body>
</html>