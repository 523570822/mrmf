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
		    <h4 class="font-34">设置支付密码</h4>
		</div>
		<div class="gray_input_pwd">
		    <div>
	            <span>
	            	  请再次输入密码
	            </span>
		    </div>
		 </div>
		<input name="pwd1" type="hidden" value="${pwd}" id="pwd1"/>
		<div class="input_pwd_div">
		    <div class='virbox1'>
		        <span></span>
		        <span></span>
		        <span></span>
		        <span></span>
		        <span></span>
		        <span></span>
		    </div>
		    <div style="clear:both;"></div>
		    <input id="pwd2" name="pwd2" type="password" style="background:rgba(0, 0, 0, 0);" class="realbox1" maxlength="6">
			<div style="height:2rem;width:8rem;position:fixed;bottom:0;z-index:200" class="bg_gray" >
		    </div>
		</div>
		<div class="btn_next">
		    <button>
		                   提交
		    </button>
		</div>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
   <%--  <script src="${ctxPath}/module/resources/js/common.js"></script> --%>
    <script type="text/javascript">
    	$(function() {
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
		        if(realboxLength === 0){
		        }
		        for(var i=0;i<realboxLength;i++){
		            var words = $('.realbox1').val();
		            $('.virbox1 span').eq(i).html('.');
		        }
            });
		    /*  密码框1 end   */
    		$(".btn_next").find("button").click(function() {
    			var pwd1 = $.trim($("#pwd1").val());
    			var pwd2 = $.trim($("#pwd2").val());
    			if(pwd2.length != 6) {
    				alert("输入不合法");
    				return;
    			}
    			$.post(_ctxPath + "/w/userMy/toSetHomeByPwd",{'pwd1':pwd1,'pwd2':pwd2},
						  function(data){
		    		if(data=='success'){
		    			alert("设置密码成功!");
						window.location.href = _ctxPath + "/w/userMy/toInfoSet";
		    		} else if(data=='noConsist') {
		    			alert("两次输入的密码不一致");
		    		} else {
		    			alert("修改密码失败");
		    		}
				});
    		});
    	});
    </script>
</body>
</html>