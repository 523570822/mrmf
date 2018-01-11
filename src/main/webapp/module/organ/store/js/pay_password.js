$(document).ready(function(){
    /*  密码框  */
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
        for(var i=0;i<realboxLength;i++){ 
            var words = $('.realbox').val();
            $('.virbox span').eq(i).html('.');
        }
    });
    /*  密码框   end   */
    
    
    /*  密码框1  */
    $('.virbox1').click(function(){
        $('.realbox1').focus();
        $('.virbox1 span').css('background','#FFFECF');
    });
    $('.realbox1').blur(function(){
        $('.virbox1 span').css('background','#ffffff');
    });
    
    $('.realbox1').bind('input propertychange', function() {
        $('.virbox1 span').html('');
        var realboxLength = Number($('.realbox1').val().length);
        for(var i=0;i<realboxLength;i++){ 
            var words = $('.realbox1').val();
            $('.virbox1 span').eq(i).html('.');
        }
    });
   $("#getCode").click(function() {
	    var phone = $.trim($("#phone").val());
	    var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
		if(!phone_reg.test(phone)){
			alert("请输入正确的手机号码！");
			return;
		} else{
			$.post(_ctxPath + "/w/organ/getCodeByPhone",{'phone':phone},
					  function(data){
					alert(data);
			});
		}
   })
   function isEmpty(str) {
	    if(str == '' || typeof str=='undefined' || str == null) {
			return true;
	    } else {
			return false;
		}
	}
   $("#back").click(function() {
	   var donationPhone = $.trim($("#donationPhone").val());
	   if(isEmpty(donationPhone)) {
		   window.location.href=_ctxPath+'/w/organ/wallet';
	   } else {
		   window.location.href=_ctxPath+'/w/organ/myDonation';
	   }
   })
   $("#commit").click(function(){
		var phone=$("#phone").val();
		var code=$("#code").val();
		if(isEmpty(phone)) {
			alert('请输入手机号！');
			return;
		} 
		if(isEmpty(code)) {
			alert('请输入验证码');
			return;
		}
		var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
		if(!phone_reg.test(phone)){
			alert("请输入正确的手机号码！");
			return;
		}
	    var password=$("#password").val();
		var confirmPassword=$("#confirmPassword").val();
		if(password.length != 6 && confirmPassword != 6 ) {
			alert("密码必须是6位！");
			return;
		}
		if(password != confirmPassword) {
			alert("两次输入的密码不一致！");
			return;
		}
		console.info("form:" + $("#form").formobj());
		$('#commit').attr('disabled',"true");
		$.post(_ctxPath + "/w/organ/setPayPassword",$("#form").formobj(),
		    function(data){
				if(data.status  == 1) {  //设置成功
                    window.location.href=_ctxPath+'/w/organ/toInputMoney';
					 /*window.location.href=_ctxPath+'/w/organ/comfirtDonation?phone='+data.message;*/
				} else if(data.status  == 2) {
					alert(data.message);
				} else if(data.status  == 3) {
					alert(data.message);
				} else if(data.status  == 4) {
					alert(data.message);
				} else if(data.status  == 5) {
					window.location.href=_ctxPath+'/w/organ/toInputMoney';
				} else if(data.status  == 6) {
					alert(data.message);
				}
				$('#commit').removeAttr("disabled");
		 	}
		 );
   });
})