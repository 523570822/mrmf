$(function(){
	$(".fm_list li").click(function(){
	       $(this).addClass('active').siblings().removeClass('active');
	       $("#change_organ_state").val($(this).index());
	 });
	$(".fm_list li").each(function(index){
		var state=$("#organ_state").val();
		if(state==index){
			$(this).addClass('active').siblings().removeClass('active');
		}
	});
	$("#back").click(function(){
		var organ_id=$("#organ_id").val();
		var organ_state=$("#change_organ_state").val();
		$("#fm_form").attr("action",_ctxPath+"/w/organ/changeState.do").attr("method","post").submit();
		//window.location.href = _ctxPath + "/w/organ/changeState.do?organId="+organ_id+"&organState="+organ_state;
	});
});