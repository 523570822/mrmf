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
    <i id="organ_search_back"></i>
    <input type="text" value="${search }" placeholder="店铺名称关键字" class="stroe_search_input" maxlength="50" id="searchContent"/>
    <img src="${ctxPath}/module/resources/images/home/icon_search.png" class="stroe_search_icon" id="search">
	</div>
    <form action="" method="post" id="stroe_list_form">
	<input type="hidden" value="${cityId }" name="cityId" id="cityId"/>
    <input type="hidden" value="${city }" name="city" id="city"/>
    <input type="hidden" value="${staff._id }" name="staffId" id="staffId"/>
    <input type="hidden" value="${longitude}" name="longitude" id="longitude"/>
    <input type="hidden" value="${latitude }" name="latitude" id="latitude"/>
    <input type="hidden" value="" id="pages"/>
    <input type="hidden" value="" id="page"/>
    <input type="hidden" value="" id="followCount"/>
	<input type="hidden" value="" id="organ_type"/>
	<input type="hidden" value="distance" id="distance"/>
	<input type="hidden" value="" id="districtId"/>
	<input type="hidden" value="" id="district"/>
	<input type="hidden" value="" id="region"/>
	</form>
    <ul class="store_list " style="margin-top:0.5rem;" id="store_list">

    </ul>


    <!--modal select list  -->
    <div class="com_back_bg"></div>
    
    
    
	<script src="${ctxPath}/module/user/userstore/js/searchstore.js"></script>
<!-- 	<script src="${ctxPath}/module/resources/js/userStore.js"></script> -->
    <script>
       
    </script>

</body>

</html>