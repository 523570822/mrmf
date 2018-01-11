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
<body class="bg_gray">
     <div class="common_nav">
	    <i id="backWallet"></i>
	    <h4 class="font-34">转赠</h4>
     </div>
     <div class="dona_account">
        <div>
            <h4 class="col-2">
                                             转赠账号
            </h4>
            <input id="phone" class="col-7" type="text" placeholder="请输入转赠手机号">
        </div>
      </div>
      <div class="btn_next">
        <button id="com_dona">
         	   提交
        </button>
   	  </div>
     <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
     <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
     <script src="${ctxPath}/module/user/usermy/js/donation.js"></script>
</body>
</html>