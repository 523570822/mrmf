$(function(){
	
	$(".get_code").click(function(){
		var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
		var phone=$("#phone").val();
		if(!phone_reg.test(phone)){
			alert("输入正确的手机号码！");
		}else{
			$.post(_ctxPath + "/w/userMy/getCodeByPhone",{'phone':phone},
					  function(data){
					alert(data);
			});
		}
	});
	$(".btn_next").click(function(){
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
		var phone=$("#phone").val();
		if(!phone_reg.test(phone)){
			alert("输入正确的手机号码！");
			return;
		}
		$.post(_ctxPath + "/w/userMy/toVerifyCode",{'phone':phone,'code':code},
				  function(data){
				if("绑定成功"==data){
					window.location.href = _ctxPath + "/w/userMy/toSetSafe";
				}else{
					alert(data);
				}
		 	}
		 );
	});
	function isEmpty(str) {
	    if(str == '' || typeof str=='undefined' || str == null) {
			return true;
	    } else {
			return false;
		}
	}
});