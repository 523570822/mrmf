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
    <title>日店铺收益</title>
   	<link href="${ctxPath}/module/resources/css/style_pos.css" rel="stylesheet">
</head>
<body style="background: #f7f7f7">
<div class="register_nav">
    <i class="back" id="back"></i>
    <h4 class="font-34">日店铺收益</h4>
	<input type="hidden" value="${organId }" id="organId"/>
</div>
<div class="bg_20"></div>
<ul class="busytime_setting">
    <li >
        <!-- <div class="col-1"></div> -->
        <div class="col-5 ">现金消费</div>

        <div class="col-5 float_right"><div><span>${earn.cashConsumption }</span>元</div></div>
    </li>
    <li >
        <!-- <div class="col-1"></div> -->
        <div class="col-5 ">非会员消费</div>
        <div class="col-5 float_right"><div><span>${earn.nonMember }</span>元</div></div>
    </li>
    <li >
        <!-- <div class="col-1"></div> -->
        <div class="col-5 ">办会员卡</div>

        <div class="col-5 float_right"><div><span>${earn.cardNum }</span>张</div></div>
    </li>
    <li id="">
        <!-- <div class="col-1"></div> -->
        <div class="col-5 ">会员卡消费</div>

        <div class="col-5 float_right"><div><span>${earn.member }</span>元</div></div>
    </li>
    <li id="">
        <!-- <div class="col-1"></div> -->
        <div class="col-5 ">微信分账消费</div>

        <div class="col-5 float_right"><div><span>${earn.weChat }</span>元</div></div>
    </li>
    <li id="">
        <!-- <div class="col-1"></div> -->
        <div class="col-5 ">会员卡充值</div>

        <div class="col-5 float_right"><div><span>${earn.memberRecharge }</span>元</div></div>
    </li>
    <li id="">
        <!-- <div class="col-1"></div> -->
        <div class="col-5 ">补缴欠费</div>

        <div class="col-5 float_right"><div><span>${earn.payArrearage }</span>元</div></div>
    </li>
    <li id="">
        <!-- <div class="col-1"></div> -->
        <div class="col-5 ">任性猫卡余额</div>

        <div class="col-5 float_right">
        	<div>
        		<span>${earn.rxmCard }</span>元
        	</div>
        </div>
    </li>
</ul>
<script src="${ctxPath}/module/organ/store/js/organset.js"></script>
</body>
</html>