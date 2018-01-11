$(function() {
	$('#com_dona').click(function() {
		var phone = $.trim($("#phone").val());
		if(phone == "") {
			alert('请输入转赠账号');
			return;
		}
		
		var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
		if(!phone_reg.test(phone)){
			alert("输入正确的转赠手机号码！");
			return;
		}
		$('#com_dona').attr('disabled',"true");
		$.post(_ctxPath+"/w/userMy/commitDonation",{'phone':phone}, function(data) {
			if(data.status  == 1) {
				window.location.href=_ctxPath+'/w/userMy/comfirtDonation?phone='+phone;
				//跳转到输入转赠金额界面
			} else if(data.status == 2) {
				alert(data.message);
			} else if(data.status == 3){
				alert(data.message);
			} else if(data.status == 4) {
				window.location.href=_ctxPath+'/w/userMy/toSetPayPassword?phone='+phone;
			} else {
				alert('对不起,程序内部有异常了');
			}
			$('#com_dona').removeAttr("disabled");
		});
	});
	
	$('.sub_info').click(function() { 
		var amount = parseFloat($.trim($("#amount").val()));
		var isNum = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
        if(!isNum.test(amount)){
            alert("请输入正确的金钱");
            return;
        }
        var walletAmount = parseFloat($("#walletAmount").val()); 
        if(amount>walletAmount) {
        	 alert("你的余额已经不足,请充值！");
             return;
        }
		$('#form').submit();
	});
	
	$('#backWallet').click(function() {
		window.location.href=_ctxPath+ '/w/userMy/myWallet';
	});
});