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
<title>我的赔付</title>
<link rel="stylesheet" href="${ctxPath}/module/user/css/my_style.css"/>
</head>
<body class="bg_gray">
<input type="hidden" value="${userId }" id="user_id"/>
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="" id="type"/>
<input type="hidden" value="compensate" id="status"/>
    <div class="common_nav">
        <i id="back"></i>
        <h4 class="font-34">申请理赔</h4>
        <span class="compensate_des" id="compensate_rule">
                                  理赔规则说明
        </span>
    </div>

    <div class="min_stripe"></div>
    <ul id="compensate_list">
     
    </ul>
    <div class="com_commit">
        <button id="create_compensate">
                           我要理赔
        </button>
    </div>
<script src="${ctxPath}/module/user/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/js/compensate_page.js"></script>
<script src="${ctxPath}/module/user/usermy/js/compensate.js"></script>
</body>
</html>