$(function() {
	$('#pInfo').click(function() {
		var userId = $('#userId').val();
		window.location.href=_ctxPath+'/w/userMy/toInfoSet';
	});
	/*设置头像*/
	$('#set_Header').click(function() {
		var id = $('#id').val();
		window.location.href=_ctxPath+'/w/userMy/toSetHeader?id=' + id;
	});
	/* 设置昵称 */
	$('#set_Nick').click(function() {
		var id = $('#id').val();
		window.location.href=_ctxPath+'/w/userMy/toSetNick?id=' + id;
	});
	$('#set_Phone').click(function() {
		window.location.href=_ctxPath+'/w/userMy/sendCode';
	});
	
	$('#set_Email').click(function() {
		var id = $('#id').val();
		window.location.href=_ctxPath+'/w/userMy/toSetEmail';
	});
	
	$('#set_Birthday').click(function() {
		var id = $('#id').val();
		window.location.href=_ctxPath+'/w/userMy/toSetBirthday?id=' + id;
	});
	/*
	 安全设置
	 */
	$('#set_Safe').click(function() {
		window.location.href=_ctxPath+'/w/userMy/toMyPayPassword';//toSetSafe
	});

	$(".wallet").click(function() {
		window.location.href=_ctxPath+'/w/userMy/toMyWallet';
	});
	$("#my_collect").click(function() {//我的收藏
		var id = $('#userId').val();
		window.location.href=_ctxPath+'/w/staffMy/myCollection?userId='+id;
	});
	$("#my_order").click(function() {//我的订单
		var id = $('#userId').val();
		window.location.href=_ctxPath+'/w/userMy/myOrder?userId='+id;
	});
    $("#my_orCode").click(function () {//跳转我的二维码
        var id = $('#userId').val();
        window.location.href=_ctxPath+'/w/userMy/toMyOrCode?userId='+id;
    });
	$("#my_coupon").click(function () {//我的优惠券
        var id = $('#userId').val();
        window.location.href=_ctxPath+'/w/userMy/myCoupon?userId='+id;
    });
	$("#my_message").click(function() {//我的消息
		var id = $('#userId').val();
		window.location.href=_ctxPath+'/w/userMy/myMessage?userId='+id;
	});
	$("#my_compensate").click(function() {//申请赔付
		var id = $('#userId').val();
		window.location.href=_ctxPath+'/w/userMy/compensate?userId='+id;
	});
	$("#user_enquiry").click(function(){//询价页面
	 		window.location.href = _ctxPath + "/w/user/enquiryPriceList.do?userId="+$("#userId").val();
	});
	$("#user_organ").click(function(){//店铺列表页面
 		window.location.href = _ctxPath + "/w/organ/toOrganList.do?cityId="+$("#cityId").val();
	});
	$("#my_vip").click(function(){//我的会员卡
		window.location.href = _ctxPath + "/w/user/myvipCard.do";
	});
	
	$("#red_pack").click(function() {
		window.location.href=_ctxPath+'/w/userMy/toRedPack';
	});
});










