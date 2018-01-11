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
<title>修改技师特长</title>
<link href="${ctxPath}/module/staff/css/style_technician.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8">
<div class="nav">
    <i class="back" onclick="window.history.go(-1)"></i>
    <h4 class="font-34">修改技师特长</h4>
    <i class="i0 confirm" id="submit">确定</i>
</div>
   <form action="" method="post" id="form">
        <input type="hidden" name="staffId" value="${staff._id }">
        <input type="hidden" name="status" value="jishiTechang">
        <input type="hidden" name="val" value="${staff.jishiTechang }" id="change_jishiTechang">
        <input type="hidden" name="sex" value="${staff.jishiTechang }" id="jishiTechang">
    <div class="change_box">
    <ul class="sex_select" id="change_jishiTechang">
        <li class="active">
            <div class="col-2">美发</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">美容</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">美甲</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">养生</div>
            <div class="col-8"><i></i></div>
        </li>
    </ul>
</div>
   </form>

<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
</body>
</html>