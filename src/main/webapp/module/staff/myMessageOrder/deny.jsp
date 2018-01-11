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
<link rel="stylesheet" type="text/css" href="${ctxPath}/module/staff/css/style_mainPage.css"/>
</head>
<body class="orderDetail">
	<div class="nav">
	    <i class="back" onclick="window.history.go(-1)"></i>
	    <h4 class="font-34">拒绝理由</h4>
	 </div>
	<form class="comment_form" id="order_refuse_form" method="post" >
		 <div class="reason">
		 	<textarea placeholder="请输入您的拒绝理由..." id="refuseComment" name="refuseComment"></textarea>
		 </div>
		 <input type="hidden" value="${staffId }" id="staff_id" name="staffId">
		 <input type="hidden" value="${orderId}" id="order_id" name="orderId">
		 <div class="confirm">
		 	<button type="button" id="refuse_order" class="button-1" style="height: 3rem;">提交回复</button>
		 </div>
	</form>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script type="text/javascript">
$(function(){
	$('#refuse_order').click(function() {
		var staffId = $('#staff_id').val();
		var orderId = $('#order_id').val();
		$.ajax({
			url:_ctxPath + "/w/staffMy/refuseOrderSave.do",
			type:"post",
			data:$('#order_refuse_form').serialize(),
			success : function(msg) { 
				if (msg.status=="1") {
					window.location.href = _ctxPath + '/w/staffMy/messageToOrderDetail?staffId='+staffId+'&orderId='+orderId;
				}else {
					alert(msg.message);
				}
			}
		},"json");
	});
});
</script>
</body>
</html>