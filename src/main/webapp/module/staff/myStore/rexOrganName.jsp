<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2017/7/2
  Time: 10:30
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
    <title>任性猫</title>
    <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
    <link href="${ctxPath}/module/organ/store/css/nodata.css" rel="stylesheet">
</head>
<body class="bg_gray">
<div class="common_nav">
    <i id="organ_search_back" onclick="history.go(-1)"></i>
    <input type="text" value="${organName}" placeholder="店铺名称关键字" class="stroe_search_input" maxlength="50" id="searchContent"/>
    <img src="${ctxPath}/module/resources/images/home/icon_search.png" class="stroe_search_icon" id="search">
</div>
<form action="" method="post" id="stroe_list_form">
    <input type="hidden" value="${cityId }" name="cityId" id="cityId"/>
    <input type="hidden" value="${staffId }" name="staffId" id="staffId"/>
    <input type="hidden" value="${regionId }" name="regionId" id="regionId"/>
    <input type="hidden" value="${districtId }" name="districtId" id="districtId"/>
    <input type="hidden" value="${organName }" name="organName" id="organName"/>
</form>
<ul class="store_list " style="margin-top:0.5rem;" id="store_list">

</ul>


<div class="com_back_bg"></div>

<!-- 	<script src="${ctxPath}/module/resources/js/userStore.js"></script> -->
<script>
    $(function () {
        var cityId = $("#cityId").val();
        var staffId = $("#staffId").val();
        var regionId = $("#regionId").val();
        var districtId = $("#districtId").val();
        $("#search").click(function(){
            var search=$("#searchContent").val().trim();
            $("#organName").val(search);
            $("#store_list").html("");
            $("#stroe_list_form").attr("action",_ctxPath+"/w/staff/stores");
            $("#stroe_list_form").submit();
            //window.location.href = _ctxPath+"/w/staff/stores?cityId="+cityId+"&organName="+search+"&staffId="+staffId+"&regionId="+regionId+"&districtId="+districtId;
        });
    })
</script>

</body>

</html>