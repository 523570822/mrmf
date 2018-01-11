$(function(){
	$(".get_code").click(function(){
		var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
		var phone=$("#phone").val();
		if(!phone_reg.test(phone)){
			alert('手机号格式有错误，请从新输入');
		}else{
			$.post(_ctxPath + "/w/userMy/getCodeByPhone",{'phone':phone},
				function(data){
					alert(data);
			});
		}
	});
	
	$(".btn_next").click(function(){
		var phone=$("#phone").val();
		var code=$.trim($("#code").val());
		if(code==""){
		    alert('请输入验证码');
			return;
		}
		
		$.post(_ctxPath + "/w/staffMy/toVerifyCode",{'phone':phone,'code':code},
				  function(data){
				if("验证成功"==data){
					window.location.href = _ctxPath + "/w/staff/changePhone";
				}else{
					alert(data);
				}
		 	}
		 );
	});
});