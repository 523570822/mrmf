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
</head>
<body class="bg_gray">
    <div class="common_nav">
        <h4 class="font-34">申请提示</h4>
    </div>
	<input type="hidden" value="${staffState }" id="staffState"/>
	
	<div class="tip">
		<p class="">您的申请已经通过</p>
	</div>
	
<!-- 	<div class="tip"> -->
<!-- 		<p class="failure through">您的申请已经通过</p> -->
<!-- 	</div> -->
 




 <script src="${ctxPath}/module/staff/login/js/searchInfo.js"></script>  
</body>
</html>