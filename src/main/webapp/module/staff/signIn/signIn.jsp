<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <meta name="description" content="This is  a .."/>
<title>签到</title>
<link href="${ctxPath}/module/staff/css/style_mainPage.css" rel="stylesheet">
 <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=UbLgidkKinZvi3ksTu1Q0hDAhWHM8Zi6"></script>
 </head>
</head>
<body class="checkin">
<form action="" method="post" id="sign_form">
	<input type="hidden" value="${staffId }" id="staff_id" name="staffId">
	<input type="hidden" value="${organId }" name="organId" id="organ_id">
	<input type="hidden" value="${longitude}" name="longitude" id="longitude">
	<input type="hidden" value="${latitude}" name="latitude" id="latitude"> 
	<input type="hidden" value="" id="pages"/>
    <input type="hidden" value="" id="page"/>
</form>
<form action="" method="post" id="staff_mainPage">
<input type="hidden" value="${staffId }" name="staffId">
</form>
	<div class="nav">
	    <div class="title">
	    	签到
	    <i class="font-34" id="statistics">统计</i>
	    </div>
	 </div>
	 <div class="ck-time">
	 	<div class="col-4 font-32">
	 		<h4><i class="sign-calender"></i> </h4>
	 		<h4 class="colorGray-light font-32">
	 			<c:if test="${week==1 }">星期日</c:if>
	 			<c:if test="${week==2 }">星期一</c:if>
	 			<c:if test="${week==3 }">星期二</c:if>
	 			<c:if test="${week==4 }">星期三</c:if>
	 			<c:if test="${week==5 }">星期四</c:if>
	 			<c:if test="${week==6 }">星期五</c:if>
	 			<c:if test="${week==7 }">星期六</c:if>
	 		</h4>
	 		<h4 class="font-32">${day }</h4>
	 	</div>
	 	<div class="col-4" style="float: right;text-align: right;">
	 		<h4><i class="sign-clock"></i> </h4>
	 		<h4 class="colorGray-light font-32">当前时间</h4>
	 		<h4 class="font-32" style="margin-right: 0">${time }</h4>
	 	</div>
	 </div>
<div class="ck-map" id="store_container" style="z-index: 99;"></div>
	 	<!-- <i class="adjustLocation font-32" onclick="adjustLocation()">地点微调</i> -->
	    <div class="ck-submit" >
	 	<div class="ck-submit-inner" id="sign_save">
	 	<h4 class="colorWhite">签到</h4>
	 	</div>
	 </div>
<div class="bottom">
	<div  class="col-2" id="main_page">
		<i class="homenor"></i>
		<h4 >信息管理</h4>
	</div>
	<div class="col-2"  id="ask_price">
		<i class="asknor"></i>
		<h4 >询价报价</h4>
	</div>
	<div class="col-2">
		<i class="signinpre"></i>
		<h4 style="color: #f4370b;">签到</h4>
	</div>
	<div class="col-2" id="mine">
		<i class="mynor"></i>
		<h4>我的</h4>
	</div>
</div>
 <div class="sign-success" id="sign_success"></div>
	<script src="${ctxPath}/module/resources/js/iscroll.js"></script>
	<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
	<script src="${ctxPath}/module/staff/js/staff.js"></script>
	<script src="${ctxPath}/module/staff/signIn/js/map.js"></script>
</body>
</html>