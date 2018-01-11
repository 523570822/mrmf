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
<title>会员卡详情</title>
<link rel="stylesheet" href="${ctxPath}/module/user/css/my_style.css"/>
</head>
<body>
<input type="hidden" value="${userincard._id }" id="incard_id">
<input type="hidden" value="${cardId }" id="cardId">
<input type="hidden" value="${userId }" id="user_id">
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="1" id="type">
<!-- common_nav start -->
	<div class="common_nav card_nav">
        <i id="back"></i>
        <h4 class="font-34">会员卡子卡</h4>
    </div>
	<!-- common_nav end -->

    <!-- card_img start -->
    <div class="card_img">
		<a>
			<p>${userincard.organName }</p>
			<p>${userincard.cardLevel }</p>
		</a>
    </div>
    <!-- card_img end -->

    <!-- card_info start -->
    <div class="incard_info">
		<a class="font-28 cA">基本信息</a><a class="font-28">消费记录</a><!--<a class="font-28">充值记录</a> -->
    </div>
    <!-- card_info end -->

    <!-- basic_info_list start -->
    <ul class="basic_info_list" id="card_list">
		
    </ul>
    <!-- basic_info_list end -->
    <!-- button_box start -->
   <!--  <div class="button_box">
    	<div>
			<a class="but_com stores" onclick="cardStore()">查看门店</a>
			<a href="javascript:void(0);" class="but_com top-up">在线充值</a>
    	</div>
    </div> -->
    <!-- button_box end-->
<script src="${ctxPath}/module/user/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/myvip/js/my_incard_detail.js"></script>
</body>
</html>