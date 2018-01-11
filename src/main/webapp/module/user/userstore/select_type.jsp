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
   
    <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
     <link rel="stylesheet" href="${ctxPath}/module/resources/css/style_user_index.css"/>
</head>
	<body class="bg_gray">
	   <!--  /*String appointTime = request.getParameter("appointTime");
		String organName = request.getParameter("organName");*/ -->
		<form method="post" action="" name="type_form" id="type_form">
			<input type="hidden" value="${organId }" name="organId" id="organId"/>
			<input type="hidden" value="${organ.gpsPoint }" name="gpsPoint" id="gpsPoint"/>
			<input type="hidden" value="${userId }" name="userId" id="userId"/>
			<input type="hidden" value="${city }" name="city" id="city"/>
			<input type="hidden" value="${cityId }" name="cityId" id="cityId"/>
			<input type="hidden" value="${orderServiceId }" name="orderServiceId" id="orderServiceId"/>
			<input type="hidden" value="${orderService }" name="orderService" id="orderService"/>
			<input type="hidden" value="${select_time }" name="select_time" id="select_time"/>
	</form>
		<div class="common_nav">
	        <i   class="organ_type_sub"></i>
	        <h4 class="font-34">服务项目</h4>
	        <label class="t_confirm organ_type_sub" >确定</label>
	    </div>
	   <%--  <i><img src="${ctxPath}/module/resources/images/icon_choiced.png" alt="" /></i> --%>
	    <ul class="type_list">
	    	<c:forEach items="${typeList}" var="type" varStatus="status">
	    		        <li class="font-28">${type.name}
		    				<input type="hidden" value="${type.name }"/>
		    				<input type="hidden" value="${type._id }" />
	    				</li>
			</c:forEach>
	    </ul>
	    
	    
        <script src="${ctxPath}/module/user/userstore/js/appoint_info.js"></script>
	</body>
</html>