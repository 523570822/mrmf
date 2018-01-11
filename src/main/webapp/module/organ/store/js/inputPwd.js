/**
 * 提现密码js
 */
$(function() {
	    /*  密码框  */
	    $('.btn_next button').click(function() {
	    	 var realboxLength = Number($('.realbox').val().length);
	    	 var money = $.trim($('#money').val());
		  	 if(realboxLength != 6) {
		  		  alert("你的输入不合法");
		  		  return;
		  	 } 
	    	 var password =  $.trim($('.realbox').val());
	    	 $('.btn_next button').attr('disabled',"true");
	    	 $.post(_ctxPath+"/w/organ/toCashWeChat",{ 'password':password,'money':money }, function(data) {
	    		if(data == 1) {
	    			$('.cash_bg').fadeIn('fast');
	    	    	$('.m_success').fadeIn('fast');
	    		} else if(data == 2 ) {
	    			$('.cash_bg').fadeIn('fast');
	    			$('.m_pwderror').fadeIn('fast');
	    		} else {
	    			$('.cash_bg').fadeIn('fast');
	    			$('.m_fail').fadeIn('fast');
	    		};
	    		$('.btn_next button').removeAttr("disabled");
			});
	    });
	    $('#reInput').click(function() {
	    	$('.cash_bg').fadeOut('fast');
			$('.m_pwderror').fadeOut('fast');
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
	    $('#btn_fail').click(function() {
	    	 window.location.href= _ctxPath + '/w/organ/wallet';
	    });
	    
	    $('#btn_success').click(function() {
	    	 window.location.href= _ctxPath + '/w/organ/wallet';
	    });
});	









