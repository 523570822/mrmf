$(function(){
	
	$(".get_code").click(function(){
		var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
		var phone=$("#phone").val();
		if(!phone_reg.test(phone)){
			alert("输入正确的手机号码！");
			return;
		}else{
			$.post(_ctxPath + "/w/organ/getPhoneCode",{'phone':phone,'type':'organ'},
					  function(data){
				alert(data);
		    },
		    "text");//这里返回的类型有：json,html,xml,text
		}
	});
	$(".btn_next").click(function(){
		var phone=$("#phone").val();
		var code=$("#code").val();
		if(code=="输入验证码"){
			alert("输入验证码");
			return;
		}
		$.post(_ctxPath + "/w/organ/toverifycode",{'phone':phone,'type':'organ','code':code},
				  function(data){
			var obj=eval(data);
			if("绑定成功"==obj.msg){
				window.location.href = _ctxPath + "/w/organ/toIndex.do?organId="+obj.organId;
			}else{
				alert(obj.msg);
			}
			
				  },
				  "json");//这里返回的类型有：json,html,xml,text
	});
});