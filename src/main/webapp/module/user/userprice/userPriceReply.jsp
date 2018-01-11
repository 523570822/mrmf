<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<title>询价回复表</title>
<link href="${ctxPath}/module/user/css/style.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8;">
<div class="enquiring_nav">
<input type="hidden" value="${userId }" id="user_id">
<input type="hidden" value="${inquiryId }" id="inquiry_id">
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="" id="page"/>
<input type="hidden" value="price" id="orderStatus"/>
<input type="hidden" value="quoteList" id="status">
<div class="register_nav">
	<i class="back" onclick="window.location.href='${ctxPath}/w/home/toHomePage'"></i>
    <h4 class="font-34">询价中<span class="cancel" id="delete_enquiry">取消</span></h4>
</div>
</div>

<div class="sort_div">
   <ul class="sort_enq">
       <li id="price" class="color_red"> 价格排序</li>
       <li id="hot">热度排序</li>
       <li id="distance">距离排序</li>
   </ul>
</div>
<input type="hidden" id="orderStatus">
<ul id="quote_list">

</ul>

<div class="enquiring_time">
    <span>
        三天内可取消当前询价，更新询价需求重新发布，或者选定技师预约后也可发布新的询价需求
    </span>
</div>
<script src="${ctxPath}/module/user/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/js/enquiry.js"></script>
<script src="${ctxPath}/module/user/userprice/js/page.js"></script>
</body>
</html>