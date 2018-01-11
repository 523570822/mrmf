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
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
<title>发红包</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/module/staff/css/style_mainPage.css"/>
</head>
<body class="sendMoneyDetail">
	<div class="nav">
	    <i class="back" id="red_detail_back"></i>
	    <h4 class="font-34">红包详情</h4>
	</div>
	<div class="moneyDetail">
		<div class="moneyRemainTitle">
			<h4>红包剩余金额</h4>
		</div>
		<div class="moneyRemain">
			<h4 class="moneyRemain-left">￥</h4><h4 class="moneyRemain-right">${weRed.restAmount==0 ? weRed.amount:weRed.restAmount }</h4>
		</div>
	</div>
	<div class="haveSent">
		<h4 style="color: #999;">已被领取</h4> <h4 style="color: #22242a;">${weRed.count-weRed.restCount }/${weRed.count }</h4>
	</div>
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="${weRed._id }" id="redId"/>
	<ul id="getUser_list">
		<!-- <li>
			<div class="getMoney">
				<div class="getMoney-photo">
					<img src="../../images/1.png" >
				</div>
				<div class="col-7">
					<h4 class="h4">尼古拉斯.赵四</h4>
					<h4 class="h4-2">14:39</h4>
				</div>
				<div class="col-1">
					<h4>21.14&nbsp;元</h4>
				</div>
			</div>
		</li> -->
	</ul>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/myhongbao/js/hongbao.js"></script>
<script src="${ctxPath}/module/staff/myhongbao/js/redPacketDetail_page.js"></script>
</body>
</html>