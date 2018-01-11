$(function(){
	$("#to_select_scope").click(function() {//跳转红包发送范围
		$("#send_form").attr("action",_ctxPath+"/w/staffMy/toScope").submit();
	});
	$("#select_scope li").click(function(){//选择范围
		$(this).addClass('active').siblings().removeClass('active');
	});
	$("#select_scope li").each(function(index){
		var state=$("#scope").val();
		if(state-1==index){
			$(this).addClass('active').siblings().removeClass('active');
		}
	});
	$("#select_organ li").click(function(){//选择所属店铺
		$(this).addClass('active').siblings().removeClass('active');
		$("#organ_id").val($(this).children('input:eq(0)').val());
	});
	$("#organ_back").click(function(){//店铺跳回
		var organId=$("#organ_id").val();
		window.location.href = _ctxPath + "/w/staffMy/toScope?organId="+organId;
	});
	$("#scope_back").click(function(){//范围跳回
	    var scope=$("#select_scope .active input").val();
	    $('#scope').val(scope);
	    $('#scopeForm').attr("action",_ctxPath+"/w/staffMy/sendRedPacket").submit();
	});
	
	$("#have_sent").click(function(){//跳转已发红包
		window.location.href = _ctxPath + "/w/staffMy/haveSentRedPacketList";
	});
	$("#back").click(function(){//发红包页面跳回
		window.location.href = _ctxPath + "/w/staff/mine";
	});
	$("#red_detail_back").click(function(){//红包详情页面跳回
		window.location.href = _ctxPath + "/w/staffMy/haveSentRedPacketList";
	});
	$("#redPacket_list_back").click(function(){//红包列表页面跳回
		var scope="";
		var organId="";
		window.location.href = _ctxPath + "/w/staffMy/sendRedPacket.do?scope="+scope+"&organId="+organId;
	});
});
function redColor(str) {
	$(str).addClass("colorRed");
	if ($("#money").val() !="") {
		$(".totalMoney").html("￥"+$("#money").val());
	}else {
		$(".totalMoney").html("");
	}
}
function blackColor(str) {
	$(str).removeClass("colorRed");
}
function selectOrgan() {//跳转店铺选择
	window.location.href = _ctxPath + "/w/staffMy/selectOrgan";
}
