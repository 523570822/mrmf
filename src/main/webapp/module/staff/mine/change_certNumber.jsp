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
<title>修改资格证号</title>
<link href="${ctxPath}/module/staff/css/style_technician.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8">
<div class="nav">
    <i class="back" onclick="window.history.go(-1)"></i>
    <h4 class="font-34">修改资格证号</h4>
    <i class="i0 confirm" id="submit">确定</i>
</div>
<div class="change_box">
    <ul>
        <li>
        <form action="" method="post" id="form">
            <div class="col-2">资格证号</div>
            <div class="col-8">
            	<input type="hidden" name="staffId" value="${staff._id }">
            	<input type="hidden" name="status" value="certNumber" id="status">
                <input type="text" name="val" value="${staff.certNumber }" placeholder="请输入新资格证号" id="value">
                <!-- <span id="submit">完成</span> -->
            </div>
        </form>
        </li>
    </ul>
</div>

<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
</body>
</html>