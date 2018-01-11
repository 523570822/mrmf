$(function(){
	
	$(".get_code").click(function(){
		var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
		var phone=$("#phone").val();
		if(!phone_reg.test(phone)){
			alert("输入正确的手机号码！");
		}else{
			$.post(_ctxPath + "/w/home/getCodeByPhone",{'phone':phone,'type':'user'},
					  function(data){
							alert(data);
			});
		}
	});
	$(".btn_next").click(function(){
		var phone=$("#phone").val();
		var code=$("#code").val();
		if(code=="输入验证码"){
			alert("输入验证码");
			return;
		}
		$.post(_ctxPath + "/w/home/toVerifyCode",{'phone':phone,'type':'user','code':code},
				  function(data){
				if("绑定成功"==data){
					//跳转到上一个界面
					window.history.go(-1);
				}else{
					alert(data);
				}
		 	}
		 );//这里返回的类型有：json,html,xml,text
	});
});