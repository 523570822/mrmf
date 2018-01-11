$(function() {
	/*  密码框  */
    $('.btn_next button').click(function() {
		  var realboxLength = Number($('.realbox').val().length);
		  if(realboxLength != 6) {
			  alert("你的输入不合法");
			  return;
		  } 
		  var password =$.trim($('.realbox').val());
		  var amount = $.trim($("#amount").val());
		  var receiveUserId = $.trim($("#receiveUserId").val());
		  ///0 表示转赠失败  1 表示转赠成功    2.表示密码错误      3.表示余额不足   4.表示你还没设置支付密码
	      $.post(_ctxPath+"/w/userMy/comDonation",{'receiveUserId':receiveUserId,'amount':amount,'password':password}, function(data) {
				if(data == '0') {
					$('.mb1').fadeIn('fast');
					$('.modal_dialog_f').fadeIn('fast');
					//此处应该弹出支付失败的对话框
				} else if(data == '1') {  
					$('.mb1').fadeIn('fast');
					$('.modal_dialog_s').fadeIn('fast');
				} else if(data == '2') {
					alert('密码错误，请重新输入！');
				} else if(data == '3') {
					alert('余额不足！请充值');	
				} else if(data == '4'){
					alert("表示你还没设置支付密码");
				}else {
					alert("程序内部有错误,请反馈到任性猫平台！");
				}
		  });
    });
    $('.virbox').click(function(){
        $('.realbox').focus();
        $('.virbox span').css('background','#FFFECF');
    });
    $('.realbox').blur(function(){
        $('.virbox span').css('background','#ffffff');
    });
    $('.realbox').bind('input propertychange', function() {
        $('.virbox span').html('');
        var realboxLength = Number($('.realbox').val().length);
        if(realboxLength === 0){
        }
        for(var i=0;i<realboxLength;i++){ 
            var words = $('.realbox').val();
            $('.virbox span').eq(i).html('.');
        }
    });
    /*  密码框 end*/ 
    $('.dialog_s_down').click(function() {
    	window.location.href = _ctxPath+'/w/userMy/myWallet';
    });
    $('.modal_pwd_f').click(function() {
    	window.location.href = _ctxPath+'/w/userMy/myDonation';
    });
    
    //点击背景以后对话框全部消失
    $('.com_back_bg').click(function() {
    	$(this).fadeOut('fast');
    	$('.modal_dialog_s').fadeOut('fast');
    	$('.modal_dialog_f').fadeOut('fast');
    });
});