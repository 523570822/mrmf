<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
<title>选额赔付者</title>
<link href="${ctxPath}/module/staff/css/style_technician.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8">
<div class="nav">
    <i class="back" id="submit"></i>
    <h4 class="font-34">选择赔付对象</h4>
</div>
   <form action="" method="post" id="form">
        <input type="hidden" name="orderId" value="${orderId }">
        <input type="hidden" name="target" value="" id="selected">
    <div class="change_box">
    <ul class="sex_select" >
        <li class="">
            <div class="col-2">店铺</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">技师</div>
            <div class="col-8"><i></i></div>
        </li>
    </ul>
</div>
   </form>

<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/js/compensate.js"></script>
</body>
</html>