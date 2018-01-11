<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/my_style.css"/>
</head>
<body class="bg_gray">
    <div class="common_nav">
        <i id="back"></i>
        <h4 class="font-34">设置支付密码</h4>
    </div>
    <form  method="post" id="form" >
		<input type="hidden" id="donationPhone" name="donationPhone" value="${donationPhone }" />
	    <div class="pay_pwd_div">
	        <div>
	            <div class="pay_pwd_email">
	                <span>手机号</span>
	            </div>
	            <div class="pay_pwd_input">
	                <input id="phone" name="phone" type="text" placeholder="请输入手机号"/>
	            </div>
	            <div class="get_code" id="getCode">
	                <div>
	                  	  获取验证码
	                </div>
	            </div>
	        </div>
	    </div>
	
	    <div class="code_div">
	        <div>
	            <div class="pay_code_name">
	                <span>验证码</span>
	            </div>
	
	            <div class="pay_code_value">
	                <input id='code' name="code" type="text" placeholder="输入验证码"/>
	            </div>
	        </div>
	    </div>
	    <div class="gray_input_pwd pay_input_pwd">
	        <div>
	            <span>
	          	  请输入6位支付密码
	            </span>
	        </div>
	    </div>
	    <div class="input_pwd_div pay_input_pwd_div">
	        <div class='virbox'>
	            <span></span>
	            <span></span>
	            <span></span>
	            <span></span>
	            <span></span>
	            <span></span>
	        </div>
	    </div>
	    <div class="gray_input_pwd pay_input_pwd">
	        <div>
	                <span>
	                                      请再次输入密码
	                </span>
	        </div>
	    </div>
	    <div class="input_pwd_div pay_input_pwd_div">
	        <div class='virbox1'>
	            <span></span>
	            <span></span>
	            <span></span>
	            <span></span>
	            <span></span>
	            <span></span>
	        </div>
	    </div>
	    <div class="btn_next">
	        <button id="commit" type="button" >
	                                 提交
	        </button>
	    </div>
	    
	    <input type="password" id="password" name="password" class="realbox" maxlength="6">
	    <input type="password" id="confirmPassword" name="confirmPassword" class="realbox1" maxlength="6">
    </form>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/organ/store/js/pay_password.js"></script>
</body>
</html>
			