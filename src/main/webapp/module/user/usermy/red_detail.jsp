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
        <i onclick="window.location.href='${ctxPath}/w/userMy/toUserMyHome'"></i>
        <h4 class="font-34">红包详情</h4>
    </div>
    <div class="red_package_info">
    	<c:choose>
    		<c:when test="${empty organ}">
    			 <div>
              		<img src="${ctxPath}/icon_logo.png">
       			 </div>
       		     <h4>任性猫平台</h4>
    		</c:when>
    		<c:otherwise>
    			 <div>
              		<img src="${ossImageHost}${staff.logo}@!avatar">
       			 </div>
       		     <h4>${organ.abname}</h4>
    		</c:otherwise>
    	</c:choose>
       
        <p class="red_package_name">
              ${ weRed.desc }
        </p>
        <p class="red_pack_money">&yen;&nbsp;<fmt:formatNumber value ="${weRedRecord.amount}" pattern="#0.00"/>元</p>
        <p class="red_pack_message">红包已存入钱包余额</p>
    </div>
    <div class="red_pack_strip">
        <div>
            <span>已经领取<i>${weRed.count - weRed.restCount }/${ weRed.count }</i>,共<i>${ weRed.amount }元</i></span>
        </div>
    </div>
    <ul class="red_pack_div">
        <c:forEach items="${weRedRecords}" var="weRedRecord">
	        <li>
	            <div class="red_pack_header">
	                <img src="${ossImageHost}${weRedRecord.userAvatar}">
	            </div>
	            <div class="red_pack_time">
	                <h4>${weRedRecord.userNick}</h4>
	                <p><fmt:formatDate value="${weRedRecord.createTime}"  pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></p>
	            </div >
	            <div class="red_pack_r">
	                <span>
	                	<fmt:formatNumber value ="${weRedRecord.amount}" pattern="#0.00"/>元
	                </span>
	            </div>
	        </li>
        </c:forEach>
     </ul>
     <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
     <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
</body>
</html>