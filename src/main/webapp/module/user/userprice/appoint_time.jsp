<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<% 
String time = new java.text.SimpleDateFormat("HH").format(new java.util.Date());
String day = new java.text.SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date());
String dayArray[]=day.split("/");
int intDay=Integer.parseInt(dayArray[0])*10000+Integer.parseInt(dayArray[1])*100+Integer.parseInt(dayArray[2]);
%>
<!DOCTYPE html >
<html>
<head>
	<meta charset="utf-8">
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
<input type="hidden" id="start" value="${start }"/>
<input type="hidden" id="end" value="${end }"/>
 <form  id="formField" method="post" action="${ctxPath}/w/user/reAppoint/">
        <input  name="day" type="hidden" id="dayF" />
	    <input  name="staffId" type="hidden" id="staffIdF" />
	    <input  name="userId" type="hidden" id="user_id" value="${userId }"/>
		<input  name="organId"type="hidden"  id="organIdF" />
	    <input  name="organName" type="hidden" id="organNameF"  />
		<input  name="time1"  type="hidden" id="time1F"  />
		<input  name="timeNum"  type="hidden" id="timeNum"  />
		<input  name="replyId"  type="hidden" id="quoteIdF"  />
	    <input  name="type"  type="hidden" id="typeF" />
</form>
<input id="calendar_id" type="hidden"/>
<input id="int_time" type="hidden" value="<%=time %>"/>
<input id="int_day" type="hidden" value="<%=intDay %>"/>
<div id="wrapper">
    <div id="scroller">
        <ul id="week">
        	<c:forEach items="${weStaffCalendars}" var="weStaffCalendar" varStatus="status">
	             <li <c:if test="${weStaffCalendar.day == day}">class='active'</c:if> >
	                <h4 class="font-30">${weStaffCalendar.week} ${weStaffCalendar.monthDay}</h4>
	                <p> ${weStaffCalendar.organName} </p>
					   <input  name="day" type="hidden" value="${ weStaffCalendar.day }" />
					   <input  name="staffId" type="hidden" value="${ staffId }" />
					   <input  name="organId"type="hidden" value="${ weStaffCalendar.organId }" />
					   <input  name="organName" type="hidden" value="${weStaffCalendar.organName}" />
					   <input  name="quoteId" id="replyId" type="hidden"  value="${ replyId }" />
					   <input  name="time1" id="time1" type="hidden"  />
					   <input  name="timeNum" id="timeNum" type="hidden"  />
					   <input  name="userId" id="userId" type="hidden"  value="${ userId }" />
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
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/resources/js/common.js"></script>
    <script src="${ctxPath}/module/user/userprice/js/appoint_time.js"></script>
    <script src="${ctxPath}/module/resources/js/iscroll.js"></script>
</body>
</html>