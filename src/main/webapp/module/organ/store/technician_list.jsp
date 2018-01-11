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
    <title>店铺技师</title>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/style_user_index.css"/>
    <link href="${ctxPath}/module/organ/store/css/nodata.css" rel="stylesheet">
</head>
<body class="bg_gray">
   <div class="register_nav">
    <i class="staff_back" id="back"></i>
    <h4 class="font-34">技师列表</h4>
</div>
<input type="hidden" value="${organId }" id="organId"/>
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
   <div style="width:100%;height:0.71rem;background: #f8f8f8;"></div>
    <div id="staffList" >
    
    </div>
    
   <script src="${ctxPath}/module/organ/store/js/technician_list.js"></script>  
</body>
</html>