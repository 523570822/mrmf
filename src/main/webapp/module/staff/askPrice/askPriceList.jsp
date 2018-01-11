<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>询价列表</title>
<link href="${ctxPath}/module/staff/css/style_mainPage.css" rel="stylesheet">
</head>
<body class="priceInfo">
	<input type="hidden" value="${openInquiry }" id="openInquiry">
	<form action="" id="staff_mainPage" method="post">
	<input type="hidden" value="${staff._id }" id="staff_id" name="staffId">
	<input type="hidden" value="" id="pages"/>
	<input type="hidden" value="" id="page"/>
	<input type="hidden" value="" id="data"/>
	<input type="hidden" value="" id="has_date"/>
	<input type="hidden" value="${longitude}" name="longitude" id="longitude"/>
	<input type="hidden" value="${latitude}" name="latitude" id="latitude"/>
	</form>
	<div class="top">报价</div>
	<c:if test="${openInquiry==true }">
		<div class="priceInfo-open" id="open" >
			<ul id="askPrice_list">
			</ul>
		</div>
		<div class="bottom" style="bottom: 4.2rem;background: #fff;height: 3rem;">
		  <div class="button font-34" style="height: 1.5rem;" onclick="closeInquiry('${staffId}')">关闭服务</div>
	   </div>
	</c:if>
	<c:if test="${openInquiry==false }">
		<div class="priceInfo-close" id="notOpen" >
			<div class="price-pic">
				<img src="module/staff/images/img/icon_criticjiahi.png">
			</div>
			<div class="price-mess">
				<h4>服务未开启</h4>
		     	<h4>将不会收到询价推送</h4>
			</div>
		</div>
		<div class="bottom" style="bottom: 3.2rem;background: #fff;">
			<button class="button font-34" style="height: 2.5rem;" onclick="openInquiry('${staffId}')">开启服务</button>
		</div>
	</c:if>
	<div class="bottom">
		<div class="col-2"  id="main_page">
			<i class="homenor"></i>
			<h4 >信息管理</h4>
		</div>
		<div class="col-2" >
			<i class="askpre"></i>
			<h4 style="color: #f4370b;">询价报价</h4>
		</div>
		<div class="col-2" id="sign_in">
			<i class="signinnor"></i>
			<h4>签到</h4>
		</div>
		<div class="col-2" id="mine">
			<i class="mynor"></i>
			<h4>我的</h4>
		</div>
	</div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script src="${ctxPath}/module/staff/js/changejs.js"></script>
<script src="${ctxPath}/module/staff/askPrice/js/page.js"></script>
</body>
</html>