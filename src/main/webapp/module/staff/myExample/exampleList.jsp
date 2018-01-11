<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
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
<title>典型案例</title>
<link href="${ctxPath}/module/staff/css/style_myExample.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8;">
	<div class="nav">
	    <i class="back" id="back"></i>
	    <h4 class="font-34">典型案例</h4>
	</div>
<input type="hidden" value="example" id="status">
<input type="hidden" value="${staffId }" id="staff_id" name="staffId">
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="" id="page"/>
	<div class="example_content">
		<ul id="example_list">
		</ul>
	</div>
	<div class="bottom">
		<button class="button font-34" id="add_example">添加</button>
	</div>

<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script src="${ctxPath}/module/staff/myExample/js/page.js"></script>
<script src="${ctxPath}/module/resources/js/iscroll.js"></script>
</body>
</html>