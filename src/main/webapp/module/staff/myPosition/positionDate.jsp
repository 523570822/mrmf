<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/staff/myPosition/js/layer_mobile/need/layer.css">
    <link rel="stylesheet" href="${ctxPath}/module/staff/myPosition/css/style.css">
</head>
<nav class="calendar-nav">
    <div class="col-2 back" onclick="window.history.go(-1)"></div>
    <div class="col-6"><h4>请选择您的租赁时间？</h4></div>
    <div class="col-2 clean">清除</div>
</nav>
<div class="calendar-box" id="day-div">
    <c:forEach items="${map}" var="MonthDays" varStatus="dayStatus">
    <section class="calendar">
        <div class="month">
            <h4 month="${MonthDays.key}">${MonthDays.key}月</h4>
            <ul class="week">
                <li>日</li>
                <li>一</li>
                <li>二</li>
                <li>三</li>
                <li>四</li>
                <li>五</li>
                <li>六</li>
            </ul>
        </div>
        <div>
            <ul class="day clear">
                <c:forEach items="${MonthDays.value}" var="day">
                    <c:if test="${day.state==0}"><li check="nor" date="${day.time}" style="visibility: hidden">${day.title} <i></i></li></c:if>
                    <c:if test="${day.state==1}"><li check="nor" date="${day.time}">${day.title} <i></i></li></c:if>
                    <c:if test="${day.state!=1&&day.state!=0}">
                        <c:if test="${day.flag<organPositionSetting.num}"><li date="${day.time}">${day.title} <i></i></li></c:if>
                        <c:if test="${day.flag>=organPositionSetting.num}"><li check="nor" date="${day.time}">${day.title} <i></i></li></c:if>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </section>
    </c:forEach>
</div>

<footer>
    <ul class="clear" style="display: none" id="day-day">
        <li class="col-3">
            <div>
                <p id="money1"></p>
                <p id="weekDay1"></p>
            </div>
        </li>
        <li class="col-4">
            共 <span id="days">0</span> 天可用
        </li>
        <li class="col-3">
            <div>
                <p id="money2"></p>
                <p id="weekDay2"></p>
            </div>
        </li>
    </ul>
    <button id="next">下一步</button>
</footer>
<form action="" method="post" id="fom">
    <input type="hidden" id="organId" name="organId" value="${organPositionSetting.organId}"/>
    <input type="hidden" id="staffId" name="staffId" value="${staffId}">
</form>
<body>
<script src='${ctxPath}/module/staff/myPosition/js/jquery-1.8.3.min.js'></script>
<script src="${ctxPath}/module/staff/myPosition/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/myPosition/js/layer_mobile/layer.js"></script>
<script src="${ctxPath}/module/staff/myPosition/js/common.js"></script>
</body>
</html>