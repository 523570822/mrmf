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
<title>理赔结果</title>
<link rel="stylesheet" href="${ctxPath}/module/user/css/my_style.css"/>
</head>
<body class="bg_gray">
<input type="hidden" value="${userId }" id="user_id"/>
<input type="hidden" value="compensateDetail" id="status"/>
<div class="common_nav ">
    <i id="back"></i>
    <h4 class="font-34 ">申请处理结果</h4>
</div>

<div class="status_div result_title">
    <div class="my_status border_bottom_94">
        <span>申诉结果</span>
    </div>
</div>

<div class="status_div result_info top_ziro">
    <div class="my_status border_bottom_94">
    	<div>平台反馈结果:&nbsp;&nbsp;<span style="color:red;">${ compensate.result}</span></div>
        <span>结果描述:&nbsp;&nbsp;${empty compensate.resultDesc?'':compensate.resultDesc }</span>
    </div>
</div>
<div class="status_div result_time top_ziro">
    <div class="result_status">
        <label>处理结果</label>
        <span><c:if test="${compensate.state==0 }">待处理</c:if>
        	<c:if test="${compensate.state==1 }">完成</c:if>
         </span>
    </div>
    <div class="result_time_info">
        <label>申请时间</label>
        <span>${compensate.timeFormat }</span>
    </div>
</div>

<script src="${ctxPath}/module/user/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/js/compensate.js"></script>
</body>
</html>