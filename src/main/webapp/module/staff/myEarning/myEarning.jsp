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
<body class="myEarning" >
<input type="hidden" value="${staffId }" name="staffId" id="staff_id">
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="1" id="type"/>
<input type="hidden" value="myEarn" id="status">
<input type="hidden" value="${flag}" id="flag">
	<div class="nav">
	    <i class="back" id="back"></i>
	    <div class="title">
	    	我的收益
	    <i class="font-34" id="withdraw_rule">收益兑换规则</i>
	    </div>
	 </div>
	 <div class="count">
	 	<div class="money">
	 		<h4 class="current">当前收益</h4>
	 		<h4 class="currentMoney">&yen;<fmt:formatNumber value ="${empty staff.totalIncome?0:staff.totalIncome }" pattern="#0.00"/></h4>
	 	</div>
	 	<div class="withdraw-2" >
	 		<img src="${ctxPath}/module/resources/images/img/icon_cash.png">
	 		<c:if test="${empty staff.totalIncome||staff.totalIncome==0 }">
	 			<h4 class="current" id="withdrawMoney" >提现</h4>
	 		</c:if>
	 		<c:if test="${staff.totalIncome !=0 }">
	 			<h4 class="current" onclick="withdraw()">提现</h4>
	 		</c:if>
	 	</div>
	 </div>
	 <div class="messTitle">
		<div class="title-1 sel_message">
			<h4 class="title-2" id="pMess">收益记录</h4>
		</div>
		<div class="title-1">
			<h4 class="title-2" id="cMess">项目提成</h4>
		</div>
	 </div>
	 <div class="earnList">
	 	<ul id="earn_list"></ul>
	 </div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/myEarning/js/earn_page.js"></script>
<script src="${ctxPath}/module/staff/js/mine.js"></script>
</body>
</html>