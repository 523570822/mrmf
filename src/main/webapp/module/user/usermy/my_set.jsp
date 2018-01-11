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
        <i onclick="window.location.href='${ctxPath}/w/userMy/toUserMyHome'"></i>
        <h4 class="font-34">设置个人信息</h4>
    </div>
	<input type="hidden" id="id" value="${ user._id }"/>
    <div class="header_div">
        <div>
            <span>头像</span> <img src="${ossImageHost}${user.avatar}@!avatar" /> <i id="set_Header"></i>
        </div>
    </div>
    <div class="wallet_item">
        <div id='set_Nick'>
            <span>昵称</span>
            <p>${user.nick}</p>
            <i></i>
        </div>
    </div>
    <div class="wallet_item" id='set_Phone'>
        <div>
            <span>手机号</span>
            <p>${user.phone }</p>
            <i></i>
        </div>
    </div>
    <div class="wallet_item"  id='set_Email'>
        <div>
            <span>邮箱</span>
            <p> ${ user.email }</p>
            <i></i>
        </div>
    </div>
    <div class="wallet_item_second" id='set_Birthday'>
        <div>
            <span>生日</span>
            <p><fmt:formatDate value="${ user.birthday }" type="date" /> </p>
            <i></i>
        </div>
    </div>
    <div class="wallet_item_last" id="set_Safe">
        <div>
            <span>设置支付密码</span>
            <i ></i>
        </div>
    </div>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/user/usermy/js/myinfo.js"></script>
</body>
</html>