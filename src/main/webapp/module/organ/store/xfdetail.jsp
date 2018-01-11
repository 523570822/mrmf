<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
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
    <title>订单详情</title>
    <link href="${ctxPath}/module/organ/store/css/style_pos.css" rel="stylesheet">
</head>
<body style="background: #f7f7f7">
<div class="register_nav">
    <i class="back"></i>
    <h4 class="font-34">会员消费详情</h4>
</div>

<ul class="custom_list" style="margin-top:0.7rem;">
    <li class="cutorm_arrow">
        <div class="col-3" style="text-align: center;margin-top:1.2rem;">
            <img src="images/img-pos/p.jpg" alt="头像"/>
        </div>
        <div class="col-5">
            <h4 class="font-32">刘德华</h4>
            <p class="font-30">电话 18001399666</p>
            <span>2015/09/09 12:00</span>
        </div>
        <div class="col-2">
            <i>会员</i>
        </div>
    </li>
</ul>

<div class="time_line">
    <ul class="line">
        <li>
            <i></i>
            <p>12/01</p>
        </li>
        <li>
            <i></i>
            <p>12/01</p>
        </li>
        <li>
            <i></i>
            <p>12/01</p>
        </li>
    </ul>
    <ul class="content">
        <li>
            <div class="txt">
                <div class="col-5 font-32">烫发</div>
                <div class="col-5 font-34" style="text-align: right;color:#f4370b;">￥360.00</div>
            </div>
            <p>北京沙宣造型设计中心（上地店）</p>
        </li>
        <li>
            <div class="txt">
                <div class="col-5 font-32">烫发</div>
                <div class="col-5 font-34" style="text-align: right;color:#f4370b;">￥360.00</div>
            </div>
            <p>北京沙宣造型设计中心（上地店）</p>
        </li>
        <li>
            <div class="txt">
                <div class="col-5 font-32">烫发</div>
                <div class="col-5 font-34" style="text-align: right;color:#f4370b;">￥360.00</div>
            </div>
            <p>北京沙宣造型设计中心（上地店）</p>
        </li>
    </ul>
</div>


<script src="${ctxPath}/module/organ/store/js/xfdetail.js"></script>
</body>
</html>