<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
<title>修改工作年限</title>
<link href="${ctxPath}/module/staff/css/style_technician.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8">
<div class="nav">
    <i class="back" onclick="window.history.go(-1)"></i>
    <h4 class="font-34">修改工作年限</h4>
    <i class="i0 confirm" id="submit">确定</i>
</div>
   <form action="" method="post" id="form">
        <input type="hidden" name="staffId" value="${staff._id }">
        <input type="hidden" name="status" value="workYears">
        <input type="hidden" name="val" value="${staff.workYears }" id="new_workYears">
        <input type="hidden" value="${staff.workYears }" id="workYears">
    <div class="change_box">
    <ul class="sex_select" id="work_years">
        <li >
            <div class="col-2">0年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li >
            <div class="col-2">1年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">2年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">3年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">4年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">5年</div>
            <div class="col-8"><i></i></div>
        </li>
        <!-- <li>
            <div class="col-2">6年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">7年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">8年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">9年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">10年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">11年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">12年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">13年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">14年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">15年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">16年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">17年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">18年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">19年</div>
            <div class="col-8"><i></i></div>
        </li>
        <li>
            <div class="col-2">20年</div>
            <div class="col-8"><i></i></div>
        </li> -->
        <li>
            <div class="col-3">10年及以上</div>
            <div class="col-7"><i></i></div>
        </li>
    </ul>
</div>
</form>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
</body>
</html>