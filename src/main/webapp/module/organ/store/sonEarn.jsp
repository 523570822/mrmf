<%--
  Created by IntelliJ IDEA.
  User: 蔺哲
  Date: 2017/7/6
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
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
    <title>子公司列表</title>
    <link href="${ctxPath}/module/resources/css/style_pos.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${ctxPath}/module/staff/css/style_mainPage.css"/>
</head>
<body style="background: #f7f7f7">
<div class="register_nav">
    <i class="back" id="back" onclick="history.go(-1)"></i>
    <h4 class="font-34">子公司列表</h4>
    <input type="hidden" value="${organId }" id="organId"/>
</div>
<c:if test="${list != null}">
<div class="bg_20"></div>
    <ul class="busytime_setting">
            <c:forEach items="${list}" var="organ">
            <li data="${organ._id}">
                <div class="col-2">${organ.name}</div>
             </li>
            </c:forEach>
    </ul>
</c:if>
<c:if test="${list == null}">
    <div class="list">
        <ul id="order_list">
            <li style="background:#eee;border:0;height: 20rem">
                <div class="notOrder"></div>
                <div class="notOrderTitle">暂无子店铺</div>
            </li>
        </ul>
    </div>
</c:if>

<script>
    $(document).ready(function () {
       $(".busytime_setting li").click(function () {
           var organId = $(this).attr("data");
           var organ = $("#organId").val();
           window.location.href = _ctxPath+"/w/organ/toEarnList?organId="+organId+"&organ="+organ;
       })
    })
</script>
</body>
</html>