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
<input type="hidden" id="id" value="${user._id }"/>
<body class="bg_gray">
    <div class="common_nav">
        <i onclick="window.location.href='${ctxPath}/w/userMy/toInputMoney'"></i>
        <h4 class="font-34">提现密码</h4>
    </div>
    <div class="gray_input_pwd">
        <div>
            <span>
                                   请输入6位提现密码
            </span>
        </div>
    </div>
    	<input type="hidden" name="money" id="money" value="${money}"> 
	    <div class="input_pwd_div">
	        <div class='virbox'>
	            <span></span>
	            <span></span>
	            <span></span>
	            <span></span>
	            <span></span>
	            <span></span>
	        </div>
	        <input id="pwd" type="password"  style="border:0px;background:rgba(0, 0, 0, 0);" name="pwd" class="realbox" maxlength="6">
	        <div style="height:2rem;width:8rem;position:fixed;bottom:0;z-index:100" class="bg_gray" >
		</div>
	    </div>
	    <div class="btn_next">
	        <button type="button">
	                               确定
	        </button>
	    </div>
    <div class="cash_bg">
    </div>
    <div class="ment_mony m_success">
		<div class="icon_success">
			<img src="${ctxPath}/module/resources/images/my/icon_pay_success.png" />
		</div>
		<div class="arrive_time">
			<p class="font-30">提现成功</p>
		</div>
		<div class="sure_btn" id="btn_success">
			<span  class="font-32">确定</span>
		</div>
	</div>
	<div class="ment_mony m_fail">
		<div class="icon_success">
			<img src="${ctxPath}/module/resources/images/my/icon_pay_fail.png" />
		</div>
		<div class="arrive_time">
			<p class="font-30">提现失败</p>
		</div>
		<div class="sure_btn" id="btn_fail">
			<span class="font-32">确定</span>
		</div>
	</div>
	<div class="ment_mony m_pwderror">
		<div class="icon_success">
			<img src="${ctxPath}/module/resources/images/my/icon_pay_fail.png" />
		</div>
		<div class="arrive_time">
			<p class="font-30">密码不正确</p>
		</div>
		<div class="sure_btn" id ="reInput">
			<span  class="font-32">重新输入</span>
		</div>
	</div>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/user/usermy/js/inputPwd.js"></script>
  </body>
</html>