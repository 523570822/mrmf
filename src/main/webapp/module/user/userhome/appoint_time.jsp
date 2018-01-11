<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<% 
String time = new java.text.SimpleDateFormat("HH").format(new java.util.Date());
String day = new java.text.SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date());
String dayArray[]=day.split("/");
int intDay=Integer.parseInt(dayArray[0])*10000+Integer.parseInt(dayArray[1])*100+Integer.parseInt(dayArray[2]);
%>
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
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/style_user_index.css"/>
</head>
<body>
<div class="common_nav"> <!-- style="position: fixed;top:0;left:0;" -->
    <i onclick="window.history.go(-1);"></i>
    <h4 class="font-34">预约时间</h4>
    <p class="rightCom" >确定<p>
</div>
<input id="int_time" type="hidden" value="<%=time %>"/>
<input id="int_day" type="hidden" value="<%=intDay %>"/>
<input type="hidden" id="start" value="${start }"/>
<input type="hidden" id="end" value="${end }"/>
 <form  id="formField" method="post" action="${ctxPath}/w/home/${ (empty caseId)?'reAppointStaff':'reAppoint'}">
        <input  name="day" type="hidden" id="dayF" />
	    <input  name="staffId" type="hidden" id="staffIdF" />
		<input  name="organId"type="hidden"  id="organIdF" />
	    <input  name="organName" type="hidden" id="organNameF"  />
		<input  name="time1"  type="hidden" id="time1F"  />
		<input  name="caseId"  type="hidden" id="caseIdF"  />
	    <input  name="type"  type="hidden" id="typeF" value="${type}" />
	    
    	<input id="city_id" name="cityId" type="hidden" value="${ cityId }" />
    	<input id="city" name="city" type="hidden" value="${ city }" />
    	<input id="come" name="come" type="hidden" value="${come }"/>
    	<input id="distance" name="distance" type="hidden" value="${distance }" />
    	<input id="userId" name="userId" type="hidden" value="${user._id }"/>
</form>
<div id="wrapper">
    <div id="scroller">
        <ul id="week">
        	<c:forEach items="${weStaffCalendars}" var="weStaffCalendar" varStatus="status">
	             <li <c:if test="${weStaffCalendar.day == day}">class='active'</c:if> >
	                <h4 class="font-30">${weStaffCalendar.week} ${weStaffCalendar.monthDay}</h4>
	                <p> ${weStaffCalendar.organName} </p>
	                <input  id="day" type="hidden" value="${ weStaffCalendar.day }" />
	                <input  id="staffId" type="hidden" value="${ staffId }" />
		            <input  id="organId"type="hidden" value="${ weStaffCalendar.organId }" />
		            <input  id="organName" type="hidden" value="${weStaffCalendar.organName}" />
		            <!-- <input  id="time1" type="hidden"  /> -->
				    <input  id="caseId" type="hidden"  value="${ caseId }" />
				    <input  id="type" type="hidden"  value="${ type }" />
	            </li>
            </c:forEach>
        </ul>
    </div>
</div>

<div id="wrapper2">
    <div id="scroller2" class="test">
        <ul id="time">
        </ul>
    </div>
</div>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/resources/js/common.js"></script>
    <script src="${ctxPath}/module/user/userhome/js/appointTime.js"></script>
    <script src="${ctxPath}/module/resources/js/iscroll.js"></script>
</body>
</html>