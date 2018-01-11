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
        <i id="appoint_time_back"></i>
        <h4 class="font-34">预约时间</h4>
        <label class="t_confirm" id="appont_type_sub">确定</label>
    </div>
    <form method="post" action="" id="appoint_time_form">
    <input type="hidden" value="${organId }" name="organId" id="organId">
	<input type="hidden" value="${day }" id="day" name="day">
	<input type="hidden" value="${select_time }" id="select_time" name="select_time"/>
	<input type="hidden" value="${orderServiceId }" name="orderServiceId" id="orderServiceId"/>
	<input type="hidden" value="${orderService}" name="orderService" id="orderService"/>
	<input type="hidden" value="${city}" name="city" id="city"/>
	<input type="hidden" value="${cityId}" name="cityId" id="cityId"/>
	<input type="hidden" value="${userId}" name="userId" id="userId"/>
	</form>
    <ul class="appoint_week_list">
    <c:forEach items="${weekDays}" var="time" varStatus="status">
        <li <c:if test="${time.daytime == day}">class="appoint_active"</c:if>>
            <p>${time.week}</p>
            <span>${time.day}</span>
            <input type="hidden" value="${time.daytime }" id="hid_day">
        </li>
     </c:forEach>   
    </ul>
    <ul class="appoint_time_list">
        <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time0 || empty weOrganCalendar.time0}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>00:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time1 || empty weOrganCalendar.time1}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>01:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time2 || empty weOrganCalendar.time2}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>02:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time3 || empty weOrganCalendar.time3}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>03:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time4 || empty weOrganCalendar.time4}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>04:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time5 || empty weOrganCalendar.time5}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>05:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time6 || empty weOrganCalendar.time6}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>06:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time7 || empty weOrganCalendar.time7}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>07:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time8 || empty weOrganCalendar.time8}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>08:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time9 || empty weOrganCalendar.time9}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>09:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time10 || empty weOrganCalendar.time10}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>10:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time11 || empty weOrganCalendar.time11}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>11:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time12 || empty weOrganCalendar.time12}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>12:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time13 || empty weOrganCalendar.time13}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>13:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time14 || empty weOrganCalendar.time14}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>14:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time15 || empty weOrganCalendar.time15}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>15:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time16 || empty weOrganCalendar.time16}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>16:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time17 || empty weOrganCalendar.time17}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>17:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time18 || empty weOrganCalendar.time18}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>18:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time19 || empty weOrganCalendar.time19}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>19:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time20 || empty weOrganCalendar.time20}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>20:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time21 || empty weOrganCalendar.time21}"></c:when>
        	<c:otherwise>class='ok '</c:otherwise>
         </c:choose>>
        <i></i>
        <span>21:00</span>
    </li>
    <li <c:choose> 
        	<c:when test="${!weOrganCalendar.time22 || empty weOrganCalendar.time22}"></c:when>
        	<c:otherwise>class='ok'</c:otherwise>
         </c:choose>>
        <i></i>
        <span>22:00</span>
    </li>
    <li<c:choose> 
        	<c:when test="${!weOrganCalendar.time23 || empty weOrganCalendar.time23}"></c:when>
        	<c:otherwise>class='ok'</c:otherwise>
         </c:choose>>
        <i></i>
        <span>23:00</span>
    </li>
    </ul>
    <script src="${ctxPath}/module/user/userstore/js/appoint_info.js"></script>
</body>
</html>