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
<title>提现</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/module/staff/css/style_mainPage.css"/>
</head>
<body class="withdraw">
<input type="hidden" value="${staff._id }" id="staff_id" name="staffId">
<input type="hidden" value="withdraw" id="status">
	<div class="nav">
	    <i class="back" id="back"></i>
	    <h4 class="font-34">申请提现</h4>
	</div>
	<div class="w-mess">
		<div class="w-pic">
			<img src="${ctxPath }/module/staff/images/img/icon_wechat_pay.png">
		</div>
		<div class="w-weixin">
			<h4 class="h4">微信账号</h4>
			<h4 class="h4-2">${staff.nick }</h4>
		</div>
	</div>
	<div class="w-money">
		<label>提现金额</label>
		<input type="hidden" id="staff_income" value="${staff.totalIncome }">
		<input type="text" id="money" placeholder="本次申请最多可提现<fmt:formatNumber value ="${empty staff.totalIncome?0:staff.totalIncome }" pattern="#0.00"/>元,最少提现1元">
	</div>
	<div class="confirm">
	 	<button id="withdraw_submit">提交</button>
	 </div>
	 <div class="cash_bg">
     </div>
     <div class="ment_mony m_pwderror">
		<div class="icon_success">
			<img src="${ctxPath}/module/resources/images/my/icon_pay_fail.png" />
		</div>
		<div class="arrive_time" style="width:100%;" >
			   你还没有设置支付密码
		</div>
		<div class="sure_btn" id ="reInput">
			<span  class="font-32">设置支付密码</span>
		</div>
	</div>
	<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
	<script src="${ctxPath}/module/staff/js/mine.js"></script>
</body>
</html>