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
<title>选择客户范围</title>
<link href="${ctxPath}/module/staff/css/style_technician.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8">
<div class="nav">
    <i class="back" onclick="window.history.go(-1)"></i>
    <h4 class="font-34">选择客户范围</h4>
    <i class="i0 confirm" id="scope_back">确定</i>
</div>
	<form action="" method="post" id="scopeForm">
		<input type="hidden" id="scope" name="scope" value="${ scope }">
		<input  type="hidden"  id="sendTime" name="sendTime" value="${ sendTime }">
		<input  type="hidden"  id="count" name="count" value="${ count }">
		<input  type="hidden"  id="money" name="money" value="${ money }">
		<input  type="hidden"  id="desc" name="desc" value="${ desc }">
	</form>
    <div class="change_box">
    <ul class="sex_select" id="select_scope">
        <li>
        	<input type="hidden" value="1">
            <div class="col-2" style="width: 60%;">服务过会员客户</div>
            <div class="col-8" style="width: 40%;"><i></i></div>
        </li>
        <li>
            <input type="hidden" value="2">
            <div class="col-2" style="width: 60%;">所有关注客户</div>
            <div class="col-8" style="width: 40%;"><i></i></div>
        </li>
    </ul>
</div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/myhongbao/js/hongbao.js"></script>
</body>
</html>