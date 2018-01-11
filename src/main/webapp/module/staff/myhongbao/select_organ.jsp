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
<title>修改工作年限</title>
<link href="${ctxPath}/module/staff/css/style_technician.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8">
<div class="nav">
    <i class="back" onclick="window.history.go(-1)"></i>
    <h4 class="font-34">选择所属店铺</h4>
    <i class="i0 confirm" id="organ_back">确定</i>
</div>
   	<input type="hidden" value="" id="organ_id">
    <div class="change_box">
    <ul class="sex_select" id="select_organ">
    <c:forEach items="${organList }" var="organ" varStatus="status">
        <li >
        	<input type="hidden" value="${organ._id }">
            <div class="col-2" style="width: 60%;">${organ.name }</div>
            <div class="col-8" style="width: 40%;"><i></i></div>
        </li>
    </c:forEach>
    </ul>
</div>

<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/myhongbao/js/hongbao.js"></script>
</body>
</html>