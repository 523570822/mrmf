$(function() {
	$('#refuse').click(function() {//跳转拒绝订单
		var orderId=$("#order_id").val();
		var staffId=$("#staff_id").val();
		window.location.href = _ctxPath + "/w/staffMy/toRefuseMessageOrder.do?staffId="+staffId+"&orderId="+orderId;
	})
	$('#confirm').click(function() {//确认订单
		var orderId=$("#order_id").val();
		var staffId=$("#staff_id").val();
		$.ajax({
			url:_ctxPath + "/w/staffMy/confirmOrder.do",
			type:"post",
			data:{'orderId':orderId,'staffId':staffId },
			success : function(msg) { 
				if (msg.status=="1") {
					//location.reload();
					window.location.href = _ctxPath + "/w/staffMy/messageToOrderDetailByComfirt?staffId="+staffId+'&orderId='+orderId;
				}else {
					alert(msg.message);
				}
			}
		},"json");
	});
});