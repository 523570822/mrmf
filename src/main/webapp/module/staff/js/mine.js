$(function(){
	$("#reInput").click(function() {
		window.location.href=_ctxPath + "/w/staffMy/setup";
	});
	$("#back").click(function() {//返回上一页
		var flag = $("#flag").val();
		if(flag==1){
			var staffId = $("#staff_id").val()
            window.location.href = _ctxPath + "/w/staffMy/toMyProfit.do?staffId="+staffId;
		}else {
            var status=$("#status").val();
            window.location.href = _ctxPath + "/w/staffMy/toLastPage.do?status="+status;
		}
	});
	$("#my_customer").click(function() {//跳转我的客户
		var staffId=$("#staff_id").val();
		window.location.href = _ctxPath + "/w/staffMy/toMyCustomer.do?staffId="+staffId;
	});
	$("#my_twoCode").click(function() {//跳转我的二维码
		var staffId=$("#staff_id").val();
		location.href = _ctxPath + "/w/staffMy/toTwoCode.do?staffId="+staffId;
	});
	$("#my_station").click(function() {//跳转我的预约工位
		var staffId=$("#staff_id").val();
		window.location.href = _ctxPath + "/w/staffMy/toStation.do?staffId="+staffId;
	});
    $("#my_code").click(function() {//跳转选择店铺
        var staffId=$("#staff_id").val();
        var url = _ctxPath + "/w/staffMy/organList.do";
        $.post(url,{"staffId":staffId},function (data) {
			if(1 == data.status){
				window.location.href =_ctxPath + "/w/staffMy/toMycode.do?staffId="+staffId;
			}else if(2 == data.status){
                window.location.href =_ctxPath + "/w/staffMy/toChooseCode.do?staffId="+staffId;
			}else{
                layer.open({
                	content: '<div style="padding: 2rem 1rem 1rem; font-size: 1.1rem;">'+ data.message + '</div>',
                    skin: 'layui-layer-demo', //样式类名
                    closeBtn: 0, //不显示关闭按钮
                    anim: 2,
                    shadeClose: true, //开启遮罩关闭
                    type: 1,
                    area: ['80%', '7rem'], //宽高
					title:false,
                    time: 2000, //2秒后自动关闭
				});
                //layer.msg(data.message);
			}
        })
    });
    $("#my_order").click(function() {//跳转我的订单
        var staffId=$("#staff_id").val();
        window.location.href = _ctxPath + "/w/staffMy/myOrder.do?staffId="+staffId;
    });
	$("#my_estimation").click(function() {//跳转我的评价
		$("#mine_form").attr("action",_ctxPath+"/w/staffMy/myEstimation").submit();
	});
	$("#my_message").click(function() {//跳转我的消息
		$("#mine_form").attr("action",_ctxPath+"/w/staffMy/myMessage").submit();
	});
    $("#my_profit").click(function() {//跳转我的收入
        $("#mine_form").attr("action",_ctxPath+"/w/staffMy/toMyProfit").submit();
    });
	$("#my_earn").click(function() {//跳转我的收益
		$("#mine_form").attr("action",_ctxPath+"/w/staffMy/myEarn").submit();
	});
	$("#withdraw_rule").click(function() {//跳转收益兑换规则
		var staffId=$("#staff_id").val();
		window.location.href = _ctxPath + "/w/staffMy/withdrawRule.do?staffId="+staffId;
	});
	$("#my_setup").click(function() {//跳转我的设置
		$("#mine_form").attr("action",_ctxPath+"/w/staffMy/setup").submit();
	});
	$("#tofeedback").click(function() {//跳转信息反馈
		var staffId=$("#staff_id").val();
		window.location.href = _ctxPath + "/w/staffMy/myFeedBack.do?staffId="+staffId;
	});
	$("#send_redPacket").click(function() {//跳转发红包
		var scope="";
		window.location.href = _ctxPath + "/w/staffMy/sendRedPacket.do?scope="+scope;
	});
	$("#savefeedback").click(function() {//保存反馈
		var desc=$("#content").val();
		if (desc !="" && desc.length>160) {
			layer.msg("<div style='text-align:center;'>您输入的内容过长</div>");
			return false;
		}
		$("#feedback_form").attr("action",_ctxPath+"/w/staffMy/saveFeedBack").submit();
	});
	
	$("#setpaypwdphone").click(function() {//跳转验证手机号
		window.location.href = _ctxPath + "/w/staffMy/toVerifyPhone.do";
	});
	$("#withdraw_submit").click(function() {//跳转输入提现密码
		var money = $.trim($("#money").val());
		var staffIncome = $.trim($("#staff_income").val());
		var isNum = /^(([1-9]\d{0,9})|0)?$/;
        if(!isNum.test(money) || money==""){
            alert("请输入正确的金钱格式");
            return false;
        }
        if (parseFloat(money)>parseFloat(staffIncome)) {
			alert("您的余额不足");
			return false;
		} 
        $('#withdraw_submit').attr('disabled',"true");
        $.get(_ctxPath+"/w/staffMy/isPayPassword",function(data) {
      	    console.info(data);
            if(data == true) {   
            	 // 表示还没有设置过密码
            	 $(".cash_bg").fadeIn("fast");
            	 $(".m_pwderror").fadeIn("fast");
            } else if(data == false ) { //false 表示已经已经设置过密码
            	window.location.href=_ctxPath+"/w/staffMy/toInputPwd?money=" + encodeURIComponent(encodeURIComponent(money));
            } else {
            	alert("任性猫程序出错！");
            }
            $('#withdraw_submit').removeAttr("disabled");
		}); 
	}); 
});
function member(str) {//跳转会员消费详情
	window.location.href = _ctxPath + "/w/staffMy/customerDetail2.do?userId="+str;
}
function refuse() {  //跳转拒绝订单
	var orderId=$("#order_id").val();
	var staffId=$("#staff_id").val();
	window.location.href = _ctxPath + "/w/staffMy/toRefuseOrder.do?staffId="+staffId+"&orderId="+orderId;
}
function confirm() {//确认订单
	var orderId=$("#order_id").val();
	var staffId=$("#staff_id").val();
	$.ajax({
		url:_ctxPath + "/w/staffMy/confirmOrder.do",
		type:"post",
		data:{'orderId':orderId,'staffId':staffId},
		success : function(msg) { 
			if (msg.status=="1") {
				window.location.href = _ctxPath + "/w/staffMy/myOrder.do";
			}else {
				layer.msg(msg.message);
			}
		}
	},"json");
}
function withdraw() {//跳转提现
	window.location.href = _ctxPath + "/w/staffMy/withdraw.do";
}