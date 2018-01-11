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
<title>选择类型</title>
<link href="${ctxPath}/module/staff/css/style_technician.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8">
<div class="nav">
    <i class="back" id="type_submit"></i>
    <h4 class="font-34">选择类型</h4>
</div>
   <form action="" method="post" id="type_form">
        <input type="hidden" id="user_id" value="${userId }" name="userId">
        <input type="hidden" id="order_id" value="${orderId }" name="orderId">
        <input type="hidden" id="code_id" value="" name="codeId">
        <input type="hidden" value="${target }" id="target" name="target"/>
    <div class="change_box">
    <ul class="sex_select" id="change_type">
    	<c:forEach items="${types.data }" var="code" varStatus="status">
	        <li class="" onclick="changeType('${code._id}')">
	            <div class="col-8" style="text-align: left;">${code.name }</div>
	            <div class="col-2"><i></i></div>
	        </li>
    	</c:forEach>
    </ul>
        <!-- <span id="submit" class="change_submit">完成</span> -->
</div>
   </form>

<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/js/compensate.js"></script>
</body>
</html>