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
							$("#hidPhone").val(phone);
							alert(data);
					  },
					  "text");//这里返回的类型有：json,html,xml,text
		}
	});
	$("#back").click(function(){
		var organId=$("#organId").val();
		window.location.href = _ctxPath + "/w/organ/toIndex.do?organId="+organId;
	});
	$("#tofeedback").click(function(){
		var organId=$("#organId").val();
		window.location.href = _ctxPath + "/w/organ/toFeedBack.do?organId="+organId;
	});
	$("#savefeedback").click(function(){
			var content=$("#content").val();
			if(content==""){
				alert("请输入反馈意见");
				return;
			}
			var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
			var phone=$("#phone").val();
			if(!phone_reg.test(phone)){
				alert("输入正确的手机号码！");
				return;
			}
			var organId=$("#organId").val();
			$.post(_ctxPath + "/w/organ/saveFeedBack",{'organId':organId,'fbcontent':content,'amount':phone},
					  function(data){
				//var obj=eval(data);
				alert(data);
				if("意见反馈成功"==data){
					window.location.href = _ctxPath + "/w/organ/toOrganSet.do?organId="+organId;
				}else{
					alert(data);
				}
				
				},"text");//这里返回的类型有：json,html,xml,text
	});
	$("#feedback_back").click(function(){
		
		$("#feedback_form").attr("action",_ctxPath+"/w/organ/toOrganSet").submit();
	});
	$("#twoCode").click(function () {
        var organId=$("#organId").val();
        location.href = _ctxPath + "/w/organ/toTwoCode.do?organId="+organId;
    });
	$("#setpwd1back").click(function(){
		$("#organPwdOne").attr("action",_ctxPath+"/w/organ/toPayPhone").submit();
	});
	$("#setpaypwdphone").click(function(){
		var organId=$("#organId").val();
		window.location.href = _ctxPath + "/w/organ/toPayPhone.do?organId="+organId;
	});
	$("#setpwd2back").click(function(){
		$("#organPwdTwo").attr("action",_ctxPath+"/w/organ/toSetPwdOne").submit();
	});
	$("#pwd_phone").click(function(){
		var phone=$("#hidPhone").val();
		var code=$("#code").val();
		var organId=$("#organId").val();
		if(code=="输入验证码"){
			alert("输入验证码");
			return;
		}
		$.post(_ctxPath + "/w/organ/toverifycodepwd",{'phone':phone,'organId':organId,'code':code},
				  function(data){
			var obj=eval(data);
			if("验证成功"==obj.msg){
				window.location.href = _ctxPath + "/w/organ/toSetPwdOne.do?organId="+obj.organId;
			}else{
				alert(obj.msg);
			}
			
			},"json");//这里返回的类型有：json,html,xml,text
	});
	$("#pwdOneSub").click(function(){
		var pwd=$("#pwd").val();
		if(pwd==""){
			alert("请输入密码");
			return;
		}
		$("#organPwdOne").attr("action",_ctxPath+"/w/organ/toSetPwdTwo").submit();
	});
	$("#pwdTwoSub").click(function(){
		var pwd2=$("#pwd2").val();
		if(pwd2==""){
			alert("请输入密码");
			return;
		}
		var pwd1=$("#pwd1").val();
		var organId=$("#organId").val();
		$.post(_ctxPath + "/w/organ/savePwd",{'organId':organId,'pwd1':pwd1,'pwd2':pwd2},
				  function(data){
			var obj=eval(data);
			if("更换成功"==obj.msg){
				window.location.href = _ctxPath + "/w/organ/toOrganSet.do?organId="+obj.organId;
			}else{
				alert(obj.msg);
			}
			
				  },
				  "json");//这里返回的类型有：json,html,xml,text
	});
	$("#for_us").click(function(){
		var organId=$("#organId").val();
		window.location.href = _ctxPath + "/w/organ/toForUs.do?organId="+organId;
	});
});
