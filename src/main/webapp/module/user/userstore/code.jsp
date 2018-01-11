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
		<h4 class="font-34">${organ.name }</h4>
	</div>
	<div class="txt_wrap">
		<h4 class="font-32">二维码</h4>
		<img src="${ctxPath }/w/organ/qr/${organ._id}.do" style="margin-left: 22%;margin-top: 13%;"/>
		<div class="turn_money_btn" id="turn_money_btn" onclick="toPay()">
			立即支付
		</div>
	</div>
     <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
     <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
     <script type="text/javascript">
     	function toPay(){
     		window.location.href ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=${organAppID}&redirect_uri=${encode}%2Fmrmf%2Fw%2Fpay%2FwxSaoMaToPay.do&response_type=code&scope=snsapi_userinfo&state=${organ._id}#wechat_redirect";
     	}
     </script>
</body>
</html>