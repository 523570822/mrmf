<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
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
<title>选择店铺</title>
<link href="${ctxPath}/module/staff/css/style_technician.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8">
<div class="nav">
    <i class="back" onClick="window.history.go(-1)"></i>
    <h4 class="font-34">选择店铺</h4>
</div>
   <form action="" method="post" id="form">
        <input type="hidden" id="userId" value="${userId }">
        <input type="hidden" id="userId" value="${userId }">
        <input type="hidden" id="quoteId" value="${quoteId }">
    <div class="change_box">
    <ul class="sex_select" >
    <c:forEach items="${organs.data }" var="organ" varStatus="status">
        <li  onclick="selectOrgan('${organ._id}')">
            <div class="col-6">${organ.name }</div>
        </li>
    </c:forEach>
    </ul>
       <!--  <span id="submit" class="change_submit">完成</span> -->
</div>
   </form>

<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/js/enquiry.js"></script>
</body>
</html>