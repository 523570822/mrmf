<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
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
<title>地点微调</title>
<link href="${ctxPath}/module/staff/css/style_mainPage.css" rel="stylesheet">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=cyuWAntIDuQM6TXAUrPRuGvosDUFZoh5"></script>
</head>
<body class="searchOrgan">
<input type="hidden" name="organId" id="organ_id">
<input type="hidden" value="" id="page"/>
<input type="hidden" value="99" id="pages"/>
<div class="nav">
	    <div class="title">
	    <i class="font-32 i-left" id="back">取消</i>
	    	地点调整
	    <i class="font-32 i-right" id="searchOrgan">确认</i>
	    </div>
	 </div>
	 <input type="hidden" value="adjustLocation" id="status">
	 <div class="search">
	 <form action="" method="post" id="search_form">
	 	<input type="hidden" value="${staffId }" id="staff_id" name="staffId">
	 	<input type="hidden" value="${longitude }" name="longitude" id="longitude">
		<input type="hidden" value="${latitude }" name="latitude" id="latitude">
	 	<input type="text" placeholder="搜索" name="organName" id="organName">
	 	<i id="search"></i>
	 </form>
	 </div>
	 <div class="ck-map" id="store_container">
	 </div>
	 <div class="organList">
	 	<ul class="choice" id="organ_list">
	 	</ul>
	 </div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script src="${ctxPath}/module/staff/signIn/js/organ_page.js"></script>
<script src="${ctxPath}/module/staff/signIn/js/map.js"></script>
</body>
</html>