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
	<title>任性猫</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/module/staff/css/style_mainPage.css"/>
</head>
<body class="myEstimation">
<input type="hidden" value="${staff._id }" id="staff_id" name="staffId">
	<div class="nav">
	    <i class="back" onclick="window.history.go(-1)"></i>
	    <h4 class="font-34">我的评价</h4>
	</div>
	<ul id="estimation_list" style="background: #fff;"></ul>
	
	<div class="evaluateBottom">
		<div class="evaluateBottom-left">
			<div class="out">
				<div class="picture">
					<img src="${ctxPath}/module/staff/images/img/icon_flower_90_pre.png">
				</div>
				<h4 class="zan">赞</h4>
				<h4 class="zanCount">(${empty staff.zanCount?0:staff.zanCount})</h4>
			</div>
		</div>
		<div class="evaluateBottom-right">
			<div class="out">
				<div class="picture">
					<img src="${ctxPath}/module/staff/images/img/icon_egg_90_pre.png">
				</div>
				<h4 class="zan">糗</h4>
				<h4 class="zanCount">(${empty staff.qiuCount?0:staff.qiuCount})</h4>
			</div>
		</div>
	</div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/myEstimation/js/estimation_page.js"></script>
</body>
</html>