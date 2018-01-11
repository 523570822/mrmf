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
    <title>日程管理</title>
    <link href="${ctxPath}/module/organ/store/css/style_pos.css" rel="stylesheet">
</head>
<body style="background: #f7f7f7">
<div class="register_nav">
    <i class="back" id="back"></i>
    <h4 class="font-34">当前繁忙程度</h4>
    <i class="time_setting" id="busytime_set">繁忙时间设置</i>
</div>
<h4 class="week font-30">本周</h4>
<input type="hidden" value="${organId }" id="organId">
<input type="hidden" value="${day }" id="day">
<ul class="weeklist">
<c:forEach items="${weekDays}" var="time" varStatus="status">
	<li <c:if test="${time.daytime == day}">class='active'</c:if> >
        <p>${time.week}</p>
        <span>${time.day} </span>
        <input type="hidden" value="${time.daytime }" >
 	</li>
</c:forEach>
</ul>
<h4 class="setting_time_list">设置可服务时间</h4>
<ul class="time_list">

    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time0 || empty weOrganCalendar.time0}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        00:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time1 || empty weOrganCalendar.time1}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        01:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time2 || empty weOrganCalendar.time2}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        02:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time3 || empty weOrganCalendar.time3}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        03:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time4 || empty weOrganCalendar.time4}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        04:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time5 || empty weOrganCalendar.time5}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        05:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time6 || empty weOrganCalendar.time6}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        06:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time7 || empty weOrganCalendar.time7}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        07:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time8 || empty weOrganCalendar.time8}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        08:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time9 || empty weOrganCalendar.time9}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        09:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time10 || empty weOrganCalendar.time10}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        10:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time11 || empty weOrganCalendar.time11}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        11:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time12 || empty weOrganCalendar.time12}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        12:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time13 || empty weOrganCalendar.time13}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        13:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time14 || empty weOrganCalendar.time14}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        14:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time15 || empty weOrganCalendar.time15}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        15:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time16 || empty weOrganCalendar.time16}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        16:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time17 || empty weOrganCalendar.time17}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        17:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time18 || empty weOrganCalendar.time18}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        18:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time19 || empty weOrganCalendar.time19}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        19:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time20 || empty weOrganCalendar.time20}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        20:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time21 || empty weOrganCalendar.time21}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        21:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time22 || empty weOrganCalendar.time22}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        22:00
    </li>
    <li<c:choose> 
        	<c:when test="${!weOrganCalendar.time23 || empty weOrganCalendar.time23}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        23:00
    </li>
</ul>
<script src="${ctxPath}/module/organ/store/js/organ.js"></script>
</body>
</html>