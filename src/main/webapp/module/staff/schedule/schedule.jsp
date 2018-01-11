<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML >
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
    <link href="${ctxPath}/module/staff/css/style_schedule.css" rel="stylesheet">
    <link href="${ctxPath}/module/organ/store/css/style_pos.css" rel="stylesheet">
</head>
<body style="background: #f7f7f7">
<div class="register_nav">
    <i class="back" id="back"></i>
    <h4 class="font-34">当前繁忙程度</h4>
    <i class="time_setting" id="busytime_set">繁忙时间设置</i>
</div>
<h4 class="week font-30">本周</h4>
<input type="hidden" value="${staffId }" id="staff_id">
<input type="hidden" value="${day }" id="day">
<input type="hidden" value="schedule" id="status">
<ul class="weeklist">
<c:forEach items="${weekDays}" var="time" varStatus="status">
	<li <c:if test="${time.daytime == day}">class='active'</c:if> >
        <p>${time.week}</p>
        <span>${time.day} </span>
        <input type="hidden" value="${time.daytime }" >
 	</li>
</c:forEach>
</ul>
<div class="organ" id="select_organ">
	<div class="col-2 font-30" style="width: 30%;">服务店铺</div>
	<div class="col-6 font-32" >
		
		<c:if test="${!empty organ }">${organ.name }
		<input type="hidden" value="${organ._id }" id="organId"></c:if>
		<c:if test="${empty organ }">
		<input type="hidden" value="${organId }" id="organId">
		</c:if>
	</div>
	<div class="col-1 next" ></div>
</div>
<h4 class="setting_time_list" style="float: left;">设置可服务时间</h4>
<ul class="time_list"style="float: left;">

    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time0 || empty weStaffCalendar.time0}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        00:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time1 || empty weStaffCalendar.time1}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        01:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time2 || empty weStaffCalendar.time2}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        02:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time3 || empty weStaffCalendar.time3}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        03:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time4 || empty weStaffCalendar.time4}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        04:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time5 || empty weStaffCalendar.time5}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        05:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time6 || empty weStaffCalendar.time6}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        06:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time7 || empty weStaffCalendar.time7}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        07:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time8 || empty weStaffCalendar.time8}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        08:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time9 || empty weStaffCalendar.time9}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        09:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time10 || empty weStaffCalendar.time10}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        10:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time11 || empty weStaffCalendar.time11}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        11:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time12 || empty weStaffCalendar.time12}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        12:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time13 || empty weStaffCalendar.time13}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        13:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time14 || empty weStaffCalendar.time14}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        14:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time15 || empty weStaffCalendar.time15}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        15:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time16 || empty weStaffCalendar.time16}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        16:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time17 || empty weStaffCalendar.time17}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        17:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time18 || empty weStaffCalendar.time18}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        18:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time19 || empty weStaffCalendar.time19}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        19:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time20 || empty weStaffCalendar.time20}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        20:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time21 || empty weStaffCalendar.time21}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        21:00
    </li>
    <li <c:choose> 
        	<c:when test="${!weStaffCalendar.time22 || empty weStaffCalendar.time22}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        22:00
    </li>
    <li<c:choose> 
        	<c:when test="${!weStaffCalendar.time23 || empty weStaffCalendar.time23}">class='ok'</c:when>
        	<c:otherwise>class='ok active'</c:otherwise>
         </c:choose>>
        <i></i>
        23:00
    </li>
</ul>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
</body>
</html>