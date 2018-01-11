<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/my_style.css"/>
</head>
	<body>
		 <div class="common_nav">
	        <i onclick="window.location.href='${ctxPath}/w/organ/myDonation'"></i>
	        <h4 class="font-34">确认转赠信息</h4>
         </div>
		<div class="my_info my_info_line">
			<div class="my_info_img"> <img src="${ossImageHost}${receiveUser.avatar}@!avatar" /></div>
			<div class="my_info_name"><span>${receiveUser.nick}</span></div>
			<div class="my_info_phone"><span>电话:</span>&nbsp;<span>${receiveUser.phone }</span></div>
		</div>
		 <form id="form" action="${ctxPath}/w/organ/toComPwd" method="post">
			<div class="fill_info">
				<label>转账金额：</label><input id="amount" type="text" name="amount" >
			</div>
			<input type="hidden"  name="walletAmount" id="walletAmount" value="${ organ.walletAmount }" />
			<input type="hidden"  name="userId" value="${ receiveUser._id }" />
			<div class="sub_info">提交</div>
		 </form> 
    
     <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
     <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
     <script src="${ctxPath}/module/organ/store/js/donation.js"></script>
</body>
</html>