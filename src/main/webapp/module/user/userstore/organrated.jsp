<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <meta name="description" content="This is  a .."/>
<title>顾客评价</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/module/resources/css/technician/style_mainPage.css"/>
<link href="${ctxPath}/module/organ/store/css/nodata.css" rel="stylesheet">
</head>
<body class="myEstimation">
	<div class="nav common_pos">
	    <i class="back" onclick="window.history.go(-1)"></i>
	    <h4 class="font-34">我的评价</h4>
	</div>
	<input type="hidden" value="${organId }" id="organId"/>
	<input type="hidden" value="" id="page"/>
	<input type="hidden" value="" id="pages"/>
	<div class="pos_top">
	<ul id="comment_content">
	</ul>
	<div class="evaluateBottom">
		<div class="evaluateBottom-left">
			<div class="out">
				<div class="picture">
					<img src="${ctxPath}/module/resources/images/img/icon_flower_90_pre.png">
				</div>
				<h4 class="zan">赞</h4>
				<h4 class="zanCount">(${organ.zanCount })</h4>
			</div>
		</div>
		<div class="evaluateBottom-right">
			<div class="out">
				<div class="picture">
					<img src="${ctxPath}/module/resources/images/img/icon_egg_90_pre.png">
				</div>
				<h4 class="zan">糗</h4>
				<h4 class="zanCount">(${organ.qiuCount})</h4>
			</div>
		</div>
	</div>
	</div>
<script src="${ctxPath}/module/user/userstore/js/organrated.js"></script>  
</body>
</html>