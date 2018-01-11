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
    <title>设置</title>
    <link href="${ctxPath}/module/resources/css/style_pos.css" rel="stylesheet">
</head>
<body style="background: #f7f7f7">
<div class="register_nav">
    <i class="back" id="back"></i>
    <h4 class="font-34">请选择店铺</h4>
    <input type="hidden" value="${staffId}" id="staff_id"/>
    <input type="hidden" value="setup" id="status"/>
</div>
<div class="bg_20"></div>
<ul class="busytime_setting">
   <c:forEach var="organList" items="${organ}">
        <li>
            <div class="col-7 " organId = "${organList._id}">${organList.name}</div>
            <div class="col-2 right"><i></i></div>
        </li>
   </c:forEach>
</ul>
<script src="${ctxPath}/module/staff/js/mine.js"></script>
<script type="text/javascript">
    $(function(){
        $(".busytime_setting li div:nth-child(1)").click(function () {
            var organId = $(this).attr('organId');
            var staffId = $("#staff_id").val();

            window.location.href = _ctxPath + "/w/staffMy/toMyCodes.do?staffId=" + staffId +"&organId=" + organId;
        })
    })






</script>
</body>
</html>