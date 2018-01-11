<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
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
        <i onclick="window.location.href='${ctxPath}/w/organ/wallet'"></i>
        <h4 class="font-34">申请提现</h4>
    </div>
    <div class="apply_cash_info">
        <div>
            <div class="wchat_header">
                <img src="${ctxPath}/module/resources/images/wchat.png">
            </div>
            
            <div class="wchart_account">
                <p>微信账号</p>
                <span>${ user.nick }</span>
            </div>
        </div>
    </div>
    <div class="cash_number">
        <div>
            <span>
                                             提现金额
            </span>
            <input type="text" id="money" placeholder="提现金额必须大于1元" />
        </div>
    </div>
    <div  class="btn_next">
        <button id="com_cash">
           	 提交
        </button>
    </div>
    <div class="cash_bg">
    </div>
    <div class="ment_mony m_pwderror">
		<div class="icon_success">
			<img src="${ctxPath}/module/resources/images/my/icon_pay_fail.png" />
		</div>
		<div class="arrive_time" style="width:100%;" >
			你还没有设置支付密码
		</div>
		<div class="sure_btn">
			<span id ="reInput" class="font-32">设置支付密码</span>
		</div>
	</div>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script type="text/javascript">
    	$("#com_cash").click(function() {
    		var money = $.trim($("#money").val());
    		if(money=='') {
    			alert('请输入提现金额');
    			return;
    		}
    		var isNum = /^(([1-9]\d{0,9})|0)?$/;
	        if(!isNum.test(money)){
	            alert("请输入正确的金钱格式");
	            return;
	        }
	  		$('#com_cash').attr('disabled',"true");
	        $.post(_ctxPath+"/w/organ/isPayPassword",{"money":money},function(data) {
	              if(data == 1) { 
	              	 $(".cash_bg").fadeIn("fast");
	              	 $(".m_pwderror").fadeIn("fast");
	              } else if(data ==0) {
	                 window.location.href=_ctxPath+"/w/organ/inputPwd?money=" + encodeURIComponent(encodeURIComponent(money));
	              } else if(data == 2) {
	              	 alert('钱包余额不足,不能提现！');
	              }else {
	              	 alert('程序内部错误,请反馈到任性猫平台！');
	              }
	          	  $('#com_cash').removeAttr("disabled");
			}); 
    	});
    </script>
</body>
</html>
			