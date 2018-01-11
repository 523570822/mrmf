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
<title>选择类型</title>
<link href="${ctxPath}/module/staff/css/style_technician.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8">
<div class="nav">
    <i class="back" onclick="window.history.go(-1)"></i>
    <h4 class="font-34">选择类型</h4>
    <i class="i0 confirm" onClick="toBack()">确定</i>
</div>
   <form action="" method="post" id="form">
  		<input type="hidden" id="staffId" name="staffId" value="${staff._id }">
		<input type="hidden" id="title" name="title" value="${title }">
		<input type="hidden" id="codeId" name="codeId" value="${codeId }">
		<input type="hidden" id="desc" name="desc" value="${desc }">
		<input type="hidden" id="price" name="price" value="${price }">
		<input type="hidden" id="consumeTime" name="consumeTime" value="${consumeTime }">
	    <div class="change_box">
		    <ul class="sex_select" id="change_type">
		    	<c:forEach items="${ types }" var="code" varStatus="status">
			        <li >
			        	<input type="hidden" value="${code._id}">
			            <div class="col-2">${code.name }</div>
			            <div class="col-8"><i></i></div>
			        </li>
		    	</c:forEach>
		    </ul>
	    </div>
   </form>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/example.js"></script>
</body>
</html>