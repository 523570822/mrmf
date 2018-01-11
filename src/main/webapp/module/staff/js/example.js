$(function(){
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
function toBack() {
	$("#form").attr("action",_ctxPath+"/w/staff/addExample").submit();
}