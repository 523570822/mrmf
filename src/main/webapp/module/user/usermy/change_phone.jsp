<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/my_style.css"/>
</head>
 
<body class="bg_gray">
    <div class="common_nav">
        <i onclick="window.history.go(-1);"></i>
        <h4 class="font-34">设置手机号 </h4>
        <span id="phoneFinished" class="finished">完成</span>
    </div>
    <div class="change_box">
        <ul>
            <li>
                <div class="col-2">手机号</div>
                <div class="col-8">
                    <input id="phoneVal" type="text" placeholder="请输入手机号" value="${ user.phone }"/>
                </div>
            </li>
        </ul>
    </div>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
     <script type="text/javascript">
    	$("#phoneFinished").click(function(){
	    	var phoneVal = $.trim($("#phoneVal").val());
	    	if(isEmpty(phoneVal)){
	    		alert('输入为空，请重新输入！');
	    		return;
	    	}
	    	var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
			if(!phone_reg.test(phoneVal)){
				alert("你输入的手机号格式不正确,请重新输入!");
				return;
		    } else {
		    	$.post(_ctxPath+'/w/userMy/toSetHomeByPhone',{'phoneVal':phoneVal},function(data) {
		    		if(data == 1) {
		    			window.location.href = _ctxPath+'/w/userMy/toInfoSet';
		    		} else if(data == 2) {
		    			alert('此手机号已经有用户使用,请核对！');
		    		} else if(data == 3) {
		    			alert('手机号码不能为空！');
		    		}
		    	});
		    }
    	});
    	/*
    	      判断是否为空！
    	*/
    	function isEmpty(str) {
    		if(str == '' || typeof str=='undefined' || str == null) {
    			return true;
    		} else {
    			return false;
    		}
    	}
    </script>
</body>
</html>