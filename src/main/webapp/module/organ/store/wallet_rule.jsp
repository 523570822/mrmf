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
		<i onclick="window.history.go(-1)"></i>
		<h4 class="font-34">任性猫钱包规则</h4>
	</div>
	<div class="txt_wrap">
		<p class="text">1、满返现：满30元返10元、满50元返20元、满100元返40元；例：满150元返40+20=60元，以此类推;</p>
		<p class="text">2、转增：实时到对方帐上，仅限于返现;</p>
		<p class="text">3、提现：2个工作日到账;</p>
		<p class="text">4、钱包：用户在消费的时候，优先使用钱包里的余额;</p>
		<p class="text">5、打赏：用户在消费过程中，如对技师感到满意，可对其打赏。</p>
	</div>
     <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
     <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
</body>
</html>