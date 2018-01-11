<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
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
    <title>任性猫</title>
    <link href="${ctxPath}/module/organ/store/css/style_pos.css" rel="stylesheet">
</head>
<body style="background: #f7f7f7">
<div class="register_nav">
    <i class="back" id="back"></i>
    <h4 class="font-34">当前繁忙程度</h4>
    <i class="screen"></i>
</div>

<form id="fm_form" action="">
<input type="hidden" value="${organId}" id="organ_id" name="organId"/>
<input type="hidden" value="${organState }" id="organ_state"/>
<input type="hidden" value="${organState }" id="change_organ_state" name="organState"/>
</form>
<ul class="fm_list pos_top">
    <li class="active">
        <div class="col-4">
            <i style="background: #acf149;"></i>
            <span>空闲</span>
        </div>
        <div class="col-6"><i></i></div>
    </li>
    <li>
        <div class="col-4">
            <i style="background: #ffde00;"></i>
            <span>一般</span>
        </div>
        <div class="col-6"><i></i></div>
    </li>
    <li>
        <div class="col-4">
            <i style="background: #ff6262;"></i>
            <span>繁忙</span>
        </div>
        <div class="col-6"><i></i></div>
    </li>
</ul>

<script src="${ctxPath}/module/organ/store/js/fm.js"></script>
</body>
</html>