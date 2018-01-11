<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>

<!DOCTYPE html >
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
<input type="hidden" id="id" value="${ user._id }"/>
<body class="bg_gray">

    <div class="common_nav">
        <i onclick="window.location.href='${ctxPath}/w/staff/mine'"></i>
        <h4 class="font-34">设置支付密码</h4>
    </div>
    <div class="gray_input_pwd">
        <div>
            <span>
                                  请输入6位支付密码
            </span>
        </div>
    </div>
    <form action="${ctxPath}/w/staffMy/toPwd2" id="form" method="post">
	    <div class="input_pwd_div">
	        <div class='virbox'>
	            <span></span>
	            <span></span>
	            <span></span>
	            <span></span>
	            <span></span>
	            <span></span>
	        </div>
	        <div style="clear:both;"></div>
	        <input id="pwd" type="password"  style="border:0px;background:rgba(0, 0, 0, 0);" name="pwd" class="realbox" maxlength="6">
	        <div style="height:2rem;width:8rem;position:fixed;bottom:0;z-index:200" class="bg_gray" >
		</div>
	    </div>
	    <div class="btn_next">
	        <button type="button">
	                           下一步
	        </button>
	    </div>
    </form>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/resources/js/common.js"></script>
  </body>
</html>