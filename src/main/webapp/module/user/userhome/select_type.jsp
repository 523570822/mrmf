<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/style_user_index.css"/>
    <style type="text/css">
    	.comfirt {
    		display: block;
		    height: auto;
		    width: auto;
		    position: absolute;
		    right: 1rem;
		    top: 1.6rem;
		    color: red;
		    font-size: 1.1rem;
		    margin-top: -0.8rem;
    	}
    </style>
</head>
	<body class="bg_gray">
	   <!--  /*String appointTime = request.getParameter("appointTime");
		String organName = request.getParameter("organName");*/ -->
		<form id="typeForm" action="${ctxPath}/w/home/reAppointByType" method = "post" >
			<input type="hidden" value="${ appointTime }" name="appointTime" />
			<input type="hidden" value="${ organName }" name="organName" />
			<input type="hidden"  name="type" id="type" />
			<input type="hidden" id="staffId" name="staffId" value="${staffId}"/>
			<input id="organId" name="organId" type="hidden" value="${organId}" /> 
		</form>
		<div class="common_nav">
	        <i id="back"></i>
	        <h4 class="font-34">服务类型</h4>
	        <span class="comfirt" id="comType">确定</span>
	    </div>
	    <ul class="type_list">
	    	<c:forEach items="${codes}" var="code" varStatus="status">
	    		<c:choose>
	    			<c:when test="${status.index == 0}">
	    				<li class="font-28">${code.name}<i id="choiced"><img src="${ctxPath}/module/resources/images/icon_choiced.png"  /></i></li>
	    			</c:when>
	    			<c:otherwise>
	    				<li class="font-28">${code.name}</li>
	    			</c:otherwise>
	    		</c:choose>
			</c:forEach>
	    </ul>
	    
	    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
        <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
        <script src="${ctxPath}/module/user/userhome/js/appointDetail.js"></script>
	</body>
</html>