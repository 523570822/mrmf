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
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/my_style.css"/>
</head>
	<body>
		 <div class="common_nav">
	        <i></i>
	        <h4 class="font-34">打赏技师</h4>
	        <span id="tiaoguo" style="float: left;position: absolute; right: 1rem; top: 1.67rem; margin-top: -0.9rem;
          color: red; font-size: 1.2rem">跳过</span>
         </div>
		<div class="my_info my_info_line">
			<div class="my_info_img"> <img src="${ossImageHost}${staff.logo}@!avatar" /></div>
			<div class="my_info_name"><span>技师名称:${staff.name}</span></div>
			<div class="my_info_phone"><span>电话:</span>&nbsp;<span>${staff.phone }</span></div>
		</div>
		 <form id="form" action="${ctxPath}/w/userMy/toEvaluateOrgan" method="post">
			<div class="fill_info">
				<label>打赏金额:</label><input id="money" type="text" name="money" >
			</div>
			<input type="hidden"  name="staffId" id="staffId" value="${ staff._id }" />
			<input type="hidden"  name="orderId" id="orderId" value="${ orderId }" />
			<div class="sub_info button" id="reward">打赏</div>
		 </form> 
     <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
     <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
     <script src="${ctxPath}/module/user/usermy/myOrder/js/my_order.js"></script>
</body>
</html>