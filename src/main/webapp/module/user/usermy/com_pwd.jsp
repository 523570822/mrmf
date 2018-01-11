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
        <h4 class="font-34">支付密码</h4>
    </div>
    <div class="gray_input_pwd">
        <div>
            <span>
                                         请输入6位支付密码
            </span>
        </div>
    </div>
	<input type="hidden" id="receiveUserId" name="receiveUserId" value="${ receiveUserId }" />
	<input type="hidden" id="amount" name="amount" value="${ amount }" />
    <div class="input_pwd_div">
        <div class='virbox'>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
        </div>
        <input id="pwd" type="password"  style="background:rgba(0, 0, 0, 0);" name="pwd" class="realbox" maxlength="6">
    	<div style="height:2rem;width:8rem;position:fixed;bottom:0;z-index:200" class="bg_gray" ></div>
    </div>
    <div class="btn_next">
        <button type="button">
                                    确认支付
        </button>
    </div> 
      <div class="com_back_bg mb1">
      </div>
      <div class="modal_dialog_s">
        <div class="dialog_s_up">
            <div>
               <img src="${ctxPath}/module/resources/images/icon_pay_success-.png">
            </div>
            <h4>转账成功</h4>
        </div>
        <div class="dialog_s_down">
            <span>确定</span>
        </div>
    </div>
    
    <div class="modal_dialog_f">
        <div class="modal_t_f">
            <img src="${ctxPath}/module/resources/images/icon_warning.png"><h4>转赠失败，重新支付</h4> <i></i>
        </div>
        <div class="modal_c_f">
            <p><i>转账失败!</i></p>
        </div>
        <div class="modal_pwd_f">
            <h4>重新支付</h4>
        </div> 
     </div>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
     <script src="${ctxPath}/module/user/usermy/js/comPwd.js"></script>
  </body>
</html>