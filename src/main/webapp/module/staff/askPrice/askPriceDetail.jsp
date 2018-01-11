<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
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
<title>询价详情</title>
<link href="${ctxPath}/module/staff/css/style_mainPage.css" rel="stylesheet">
</head>
<form action="" id="askprice_form" method="post">
<input type="hidden" value="${staff._id }" name="staffId">
<input type="hidden" value="${weUserInquiry._id }" name="inquiryId">
<!-- <input type="hidden" value="116.397194" name="longitude" />
<input type="hidden" value="39.917073" name="latitude" /> -->
<body class="priceDetail">
	<div class="nav" style="margin-bottom: 0.7rem;">
	    <i class="back" onclick="window.location.href='${ctxPath}/w/staff/askPrice'"></i>
	    <h4 class="font-34">询价详情</h4>
	 </div>
	 <div class="pd-title">
	 	<h4 class="colorGray-light font-30">询价项目</h4>
	 	<h4 class="font-32">${weUserInquiry.type }</h4>
	 </div>
	 <div class="pd-detail-title colorGray font-30">客户需求描述</div>
	 <div class="pd-detail font-28">
	 	<h4>${weUserInquiry.desc }</h4>
	 </div>
	 <div class="p-pic">
	 	<c:forEach items="${weUserInquiry.images }" var="img">
			<img src="${ossImageHost}${img}@!style1000">
	 	</c:forEach>
	</div>
	<div class="pd-detail-title colorGray font-32">我的报价</div>
	<div class="pd-title font-30">
		<input placeholder="￥0.00" name="myPrice" id="my_price">
	</div>
	<div class="pd-detail-title colorGray font-32">报价说明</div>
	<div class="textarea font-32">
		<textarea placeholder="输入报价说明" name="mypriceDesc" id="price_desc" ></textarea>
	</div>
</form>
	<div class="bottom" style="background: #fff;">
		<button class="button font-34" id="askprice_save" style="height: 2.5rem;">发送</button>
	</div>
	<div id="showDiv" class="askprice-hide" onclick="hide()"></div>
		<div id="content" class="askprice-show" >
			<div class="title">发送成功</div>
			<div class="botton-left" id="return_mainPage">返回首页</div>
			<div class="botton-right" onclick="return_price('${staffId}')">确定</div>
		</div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script src="${ctxPath}/module/staff/js/changejs.js"></script>
</body>
</html>