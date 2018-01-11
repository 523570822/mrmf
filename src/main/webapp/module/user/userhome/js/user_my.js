$(function(){
	$("#user_my").click(function(){
		var userId=$("#userId").val();
		window.location.href = _ctxPath + "/w/userMy/toUserMyHome.do?userId="+userId;
	});
	
});