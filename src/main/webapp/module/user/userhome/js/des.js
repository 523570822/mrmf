$(function() {
	$('#staffCase').click(function(){
//		var sId = $.trim($("#staffId").val());
//		var come = $("#come").val();
//		var organId = $("#organ_id").val();
		//window.location.href=_ctxPath + "/w/home/staffDetail2?staffId="+sId+'&longitude=116.391786289962&latitude=34.9077741797592&come='+come+'&organId='+organId;
		$("#staffDetailForm").attr("action",_ctxPath + "/w/home/staffDetail2").submit();
	});
});