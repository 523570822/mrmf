$(function(){
	$("#back").click(function() {//返回上一页
		var userId=$("#user_id").val();
		var status=$("#status").val();
		window.location.href = _ctxPath + "/w/userMy/toLastPage.do?userId="+userId+"&status="+status;
	});
	$("#create_compensate").click(function() {//我要赔付
		var userId=$("#user_id").val();
		window.location.href = _ctxPath + "/w/userMy/createCompensate.do?userId="+userId;
	});
	$("#compensate_rule").click(function() {//赔付规则说明
		window.location.href = _ctxPath + "/w/userMy/compensateRules";
	});
	$("#selectOrder").click(function() {//选择项目
		var userId=$("#user_id").val();
		window.location.href = _ctxPath + "/w/userMy/toSelectOrder.do?userId="+userId;
	});
	$("#selectProvider").click(function() {//跳转选择赔付者
		var userId=$("#user_id").val();
		var orderId=$("#order_id").val();
		if (orderId=="") {
			layer.msg('请先选择理赔项目');
			return false;
		}else {
			window.location.href = _ctxPath + "/w/userMy/toSelectProvider.do?userId="+userId+"&orderId="+orderId;
		}
	});
	$("#select_provicer").click(function() {//选择赔付者
		var userId=$("#user_id").val();
		var orderId=$("#order_id").val();
		window.location.href = _ctxPath + "/w/userMy/toSelectProvider.do?userId="+userId+"&orderId="+orderId;
	});
	$(".sex_select li").click(function(){//选择当前对象
	       $(this).addClass('active').siblings().removeClass('active');
	       $("#selected").val($(this).index()+1);
	 });
	$("#submit").click(function() {//提交赔付对象
		$("#form").attr("action",_ctxPath+"/w/userMy/returnCompensate").submit();
	});
	$("#type_submit").click(function() {//提交赔付类型
		$("#type_form").attr("action",_ctxPath+"/w/userMy/returnCompensate").submit();
	});
	$("#selectType").click(function() {//跳转选择类型
		var userId=$("#user_id").val();
		var orderId=$("#order_id").val();
		var target=$("#target").val();
		if (target=="") {
			layer.msg('请先选择需理赔者');
			return false;
		}else {
			window.location.href = _ctxPath + "/w/userMy/selectType.do?userId="+userId+"&orderId="+orderId+"&target="+target;
		}
	});
	$("#saveCompensate").bind('click',saveCompensate);
	function saveCompensate() {//保存赔付
			var orderId=$("#order_id").val();
			var target=$("#target").val();
			var codeId=$("#code_id").val();
			var desc=$("#desc").val();
			if (orderId==""||target==""||codeId==""||desc=="") {
				layer.msg('请补全理赔信息');
				return false;
			}
			if (desc !="" && desc.length>100) {
				layer.msg('您输入的内容过长');
				return false;
			}
			$("#saveCompensate").unbind('click',saveCompensate);
			$("#compensate_form").attr("action",_ctxPath+"/w/userMy/saveCompensate").submit();
	}
});
function changeType(str) {//选择类型跳回
	 //var userId=$("#user_id").val();
	 //var orderId=$("#order_id").val();
	 $("#code_id").val(str);
	 //window.location.href = _ctxPath + "/w/userMy/returnCompensate.do?codeId="+str+"&userId="+userId+"&orderId="+orderId;
}