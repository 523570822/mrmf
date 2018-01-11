<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
    <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
</head>
<body class="bg_gray">
    <div class="common_nav">
        <i id="appoint_into_back"></i>
        <h4 class="font-34">预约信息</h4>
    </div>
    <form method="post" action="" name="appoint_info_form" id="appoint_info_form">
	<input type="hidden" value="${organ._id }" name="organId" id="organId"/>
	<input type="hidden" value="${organ.gpsPoint }" name="gpsPoint" id="gpsPoint"/>
	<input type="hidden" value="${user._id }" name="userId" id="userId"/>
	<input type="hidden" value="${city }" name="city" id="city"/>
	<input type="hidden" value="${cityId }" name="cityId" id="cityId"/>
	<input type="hidden" value="${orderServiceId }" name="orderServiceId" id="orderServiceId"/>
	<input type="hidden" value="${orderService }" name="orderService" id="orderService"/>
	<input type="hidden" value="${select_time }" name="select_time" id="select_time"/>
	<input type="hidden" value="${search }"  id="search" name="search"/>
	</form>
    <div class="appoint_store">
        <div>
        <c:choose> 
        	<c:when test="${ empty organ.logo}">
        	<img src="${ctxPath}/module/resources/images/nopicture.jpg">
        	</c:when>
        	<c:otherwise><img src='${ossImageHost}${organ.logo }@!style400' id="organ_logo"/></c:otherwise>
         </c:choose>
            
            <span>
                ${organ.name }
            </span>
        </div>
    </div>
    <div class="appoint_div">
        <div id="organ_type">
            <span>预约项目</span>
            <c:choose> 
        	<c:when test="${empty orderService }">
        	<label>选择预约项目</label>
        	</c:when>
        	<c:otherwise><label>${orderService}</label></c:otherwise>
            </c:choose>
            
            
            <i></i>
        </div>
    </div>
    <div class="appoint_div">
        <div id="appoint_time">
            <span>预约时间</span>
            
            <c:choose> 
        	<c:when test="${empty select_time }">
        	<label>选择预约时间</label>
        	</c:when>
        	<c:otherwise><label>${select_time }</label></c:otherwise>
            </c:choose>
            
            <i></i>
        </div>
    </div>
    <div class="appoint_div">
        <div id = "appoint_store">
            <span>预约店铺</span>
            <label id="selected_items">${organ.address }</label>
            <img class="location" src="${ctxPath }/module/user/images/icon_location_list.png">
        </div>
     </div>

    <div class="appoint_com">
        <button id="appoint_submit">提交</button>
    </div>
    <script src="${ctxPath}/module/user/userstore/js/appoint_info.js"></script>
</body>
</html>