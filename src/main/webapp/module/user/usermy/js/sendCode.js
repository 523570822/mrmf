$(function(){
	
	$(".get_code").click(function(){
		var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
		var phone=$("#phone").val();
		if(!phone_reg.test(phone)){
			alert("输入正确的手机号码！");
		}else{
			$.post(_ctxPath + "/w/userMy/getCode",{'phone':phone},
					  function(data){
					alert(data);
			});
		}
	});
	
	$(".btn_next").click(function(){
		var phone=$("#phone").val();
		var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
		if(!phone_reg.test(phone)){
			alert("输入正确的手机号码！");
			return;
			
		}
		var code=$("#code").val();
		if(code==""){
			alert("输入验证码");
			return;
		}
		$.post(_ctxPath + "/w/userMy/toVerifyCodeByPhone",{'phone':phone,'code':code},
				  function(data){
				if(data.status == 0) {
					alert(data.message);
				} else if(data.status == 2) {
					window.location.href = _ctxPath + "/w/userMy/toSetPhone";
				} else if(data.status == 1) {
					window.location.href = _ctxPath + "/w/userMy/toInfoSet";
				}
		 	}
		 );
	});
});