$(function(){
	$("#back").click(function() {//返回上一页
		var userId=$("#user_id").val();
		var status=$("#status").val();
		window.location.href = _ctxPath + "/w/userMy/toLastPage.do?userId="+userId+"&status="+status;
	});
	$("#reward").click(function() { 
		$.ajax({
			url:_ctxPath + "/w/userMy/verifyWallet",
			type:"post",
			data:$('#form').serialize(),
			success : function(data) { 
				if(data ==  true) {
					$('#form').submit();
				} else if(data ==  false){
					alert("你的余额已经不足,无法完成打赏");
					return;
				}
			}
		},"json");
	});
	$("#tiaoguo").click(function() { 
		console.info($("#orderId").val());
		window.location.href = _ctxPath+'/w/userMy/toEvaluateOrgan?orderId='+$("#orderId").val();
	});
	$("#comment_submit").click(function() {//保存评价
		var desc=$.trim($("#comment").val());
		var commentType=$.trim($("#comment_type").val());
		if (desc !="" && desc.length>50) {
			layer.msg("您输入的内容过长");
			return false;
		}
		var zan = $("#zanCount").val();
		var qiu = $("#qiuCount").val();
		if(zan==0&&qiu==0){
			layer.msg("赞和糗不能全为空");
			return false;
		}
		$('#comment_submit').attr('disabled',"true");
		$.ajax({
			url:_ctxPath + "/w/userMy/evaluateOrder",
			type:"post",
			data:$('#organ_index_form').serialize(),
			success : function(msg) { 
				if (msg.status=="1") {
					if (commentType=="commentStaff") {//评价技师后跳转评价店铺
						//打赏技师
						window.location.href = _ctxPath + "/w/userMy/toRewardStaff.do?orderId="+$("#order_id").val();
					}else {//评价店铺后跳转订单
						window.location.href = _ctxPath + "/w/userMy/orderDetail.do?orderId="+$("#order_id").val()+"&type="+$("#type").val();
					}
				}else {
					layer.msg(msg.message);
				}
				$('#comment_submit').removeAttr("disabled");
			}
		
		},"json");
	});
	$("#pay_ul li").click(function() {    //选择支付类型
		$(this).children('i:eq(0)').addClass('choiced').siblings().removeClass('choiced');
	});
});
function cancelOrder() {//取消订单
	var userId=$("#user_id").val();
	var orderId=$("#order_id").val();
	$.ajax({
		url:_ctxPath + "/w/userMy/cancelOrder.do",
		type:"post",
		data:{'orderId':orderId,'userId':userId},
		success : function(msg) { 
			if (msg.status=="1") {
				window.location.href = _ctxPath + "/w/userMy/myOrder.do";
			}else {
				layer.msg(msg.message);
			}
		}
	},"json");
}
function comment() {//跳转评价
	var userId=$("#user_id").val();
	var orderId=$("#order_id").val();
	window.location.href = _ctxPath + "/w/userMy/toEvaluateOrder.do?orderId="+orderId+"&userId="+userId;
}
function returnOrder() {//返回订单
	var type=$("#type").val();
	var orderId=$("#order_id").val();
	window.location.href = _ctxPath + "/w/userMy/orderDetail.do?orderId="+orderId+"&type="+type;
}
function pay() {//跳转订单支付
	var orderId=$("#order_id").val();
	window.location.href = _ctxPath + "/w/userMy/orderPay.do?orderId="+orderId;
}
