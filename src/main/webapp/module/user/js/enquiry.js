$(function(){
	$("#send_enquiry").click(function() {//发送询价
		var type=$("#service_type").val();
		var desc=$("#desc").val();
		if (type=='') {
			alert("请选择询价类型");
			return false;
		}else if (desc=='') {
			alert("请详细说明您想做的发型及发质状况等。");
			return false;
		}else if (desc !="" && desc.length>75) {
			alert("您输入的内容过长");
			return false;
		} else if ($("#logo0").val()=="" && $("#logo1").val()=="" && $("#logo2").val()=="") {
			alert("请添加照片");
			return false;
		}
		$('#send_enquiry').attr('disabled',"true");
		$.ajax({
			url:_ctxPath + "/w/user/saveEnquiry",
			type:"post",
			data:$('#enquiry_form').serialize(),
			success : function(msg) { 
				if (msg=="true") {
					alert("您的询价已发送");
					window.location.href = _ctxPath + "/w/user/enquiryPriceList.do";
				}else {
					alert("您已发送过询价");
					return false;
				}
				$('#send_enquiry').removeAttr("disabled");
			},
			error:function(data) {
				$('#send_enquiry').removeAttr("disabled");
			}
		},"json");
	});
	$("#delete_enquiry").click(function() {//删除询价
		window.location.href = _ctxPath + "/w/user/deleteEnquiry";
	});
	$("#appoint_time").click(function() {//预约时间
		var userId=$("#user_id").val();
		var staffId=$("#staff_id").val();
		var replyId=$("#reply_id").val();
		window.location.href = _ctxPath + "/w/user/toAppointTime.do?userId="+userId+"&staffId="+staffId+"&replyId="+replyId;
	});
	$("#select_organ").click(function() {//选择店铺
		var userId=$("#user_id").val();
		var staffId=$("#staff_id").val();
		var quoteId=$("#quote_id").val();
		window.location.href = _ctxPath + "/w/user/selectOrgan.do?userId="+userId+"&staffId="+staffId+"&quoteId="+quoteId;
	});
	$("#appoint_save").click(function() {//保存预约
		var organId=$("#organ_id").val();
		var orderTime=$("#orderTime").val();
		if (orderTime =="" || organId=="") {
			alert("请选择预约时间");
			return false;
		}
		$("#quote_form").attr("action",_ctxPath+"/w/user/appointSave").submit();
	});
	
	$("#user_organ").click(function(){//跳转店铺列表
		var cityId=$("#city_id").val();
		var userId=$("#user_id").val();
 		window.location.href = _ctxPath + "/w/organ/toOrganList.do?cityId="+cityId+"&userId="+userId;
	});
	$("#user_my").click(function(){//跳转我的页面
		var userId=$("#user_id").val();
		window.location.href = _ctxPath + "/w/userMy/toUserMyHome.do?userId="+userId;
	});
	$("#user_home").click(function(){//跳转首页页面
		var userId=$("#user_id").val();
		window.location.href = _ctxPath + "/w/user/toHomePage.do?userId="+userId;
	});
    $("#back").click(function() {//跳转上一页
		var userId=$("#user_id").val();
		var status=$("#status").val();
		window.location.href = _ctxPath + "/w/user/tolastPage.do?userId="+userId+"&status="+status;
	});
	$("#change_type li").click(function(){//选择当前类型
		$(this).addClass("active").siblings().removeClass('active');
		var codeId=$(this).children('input:eq(0)').val();
		$("#codeId").val(codeId);
	});
	 $("#change_type li").each(function(){
		 var codeId = $("#codeId").val();
		 if($(this).children('input:eq(0)').val() == codeId) {
			 $(this).addClass("active").siblings().removeClass('active');
		 }
	 });
});
 
 function selectOrgan(str) {//选择店铺跳回询价
	 var userId=$("#userId").val();
	 var quoteId=$("#quoteId").val();
	 window.location.href = _ctxPath + "/w/user/enquiryDetail.do?organId="+str+"&userId="+userId+"&quoteId="+quoteId;
 }
 function toChangeType() {//跳转类型选择
	 $("#enquiry_form").attr("action",_ctxPath+"/w/user/selectType").submit();
	/* var userId=$("#user_id").val();
	 var type=$("#type").val();
	 window.location.href = _ctxPath + "/w/user/selectType.do?userId="+userId+"&type="+type;*/
 }
/* function changeType(str) {//选择类型跳回
	$("#code_id").val(str);
	 if (type == "enquiry") {
		 window.location.href = _ctxPath + "/w/user/enquiryPrice.do?codeId="+str+"&userId="+userId+"&staffId="+staffId;
	}else if (type == "addExample") {
		window.location.href = _ctxPath + "/w/staff/enquiryPrice.do?codeId="+str+"&userId="+userId+"&staffId="+staffId;
	}
 }*/
 function toBack() {
	var type=$("#type").val();
	if (type == "enquiry") {
		$("#form").attr("action",_ctxPath+"/w/user/enquiryPrice").submit();
	}else if (type == "addExample") {
		window.location.href = _ctxPath + "/w/staff/enquiryPrice.do?codeId="+codeId+"&userId="+userId+"&staffId="+staffId;
	}
	
}