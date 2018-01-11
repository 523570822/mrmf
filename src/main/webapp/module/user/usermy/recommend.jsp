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
    <h4 class="font-34">推荐人二维码</h4>
</div>
<div class="txt_wrap">
    <h4 class="font-32"></h4>
    <img src="${showqrcode}" style="margin-left: 22%;margin-top: 13%;" width="50%" height="50%"/>
</div>
<script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
</body>
</html>