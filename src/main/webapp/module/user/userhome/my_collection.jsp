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
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/user/css/my_style.css"/>
<title>我的收藏</title>
</head>
<body class="bg_gray">
    <div class="common_nav">
        <i onclick="window.location.href='${ctxPath}/w/userMy/toUserMyHome'"></i>
        <h4 class="font-34">我的收藏</h4>
    </div>
    <input type="hidden" value="${userId }" id="user_id" name="userId"/>
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="1" id="type"/>
    <div class="my_fun_div">
        <div class="title-1 sel_message">
			<h4 class="title-2" id="pMess">精美发型</h4>
		</div>
		<div class="title-1">
			<h4 class="title-2" id="cMess">收藏技师</h4>
		</div>
		<div class="title-1">
			<h4 class="title-2" id="cMess">收藏店铺</h4>
		</div>
    </div>
	<ul id="collection_list" class="ul case_ul">
	
	</ul>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/userhome/js/collection_page.js"></script>
</body>
</html>