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
        <div class="common_nav feedback_top">
            <h4 class="font-34 ">意见反馈</h4>
            <div>发送</div>
        </div>
        <form id="commentF" class="comment_form feedback_form" method="post" action="${ctxPath}/w/userMy/comFeedBack">
            <textarea name="comment" id="comment" placeholder="请输入您的评论..." ></textarea>
            <div class="orderpayheader_div amount_div feedback_phone_div">
            <div class="amount_left feedback_phone_left">
               	 联系方式
            </div>
            <div class="amount_right feedback_phone_right">
                <input id="phone" type="text" name="phone" placeholder="输入您联系方式" />
            </div>
            <input type="hidden" value="feedback" id="feedback" name="feedback"/>
        </div>
        </form>
     <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
     <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
     <script src="${ctxPath}/module/user/usermy/js/wallet.js"></script>
     <script type="text/javascript">
     	$(".feedback_top div").click(function() {
     	 	var comment = $.trim($("#comment").val());
     	 	var phone = $.trim($("#phone").val());
     	 	if(comment == "") {
     	 	layer.msg("评论内容不能为空");
     			return;
            }
            if(phone!=""){
	            var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
				if(!phone_reg.test(phone)){
					alert("你输入的手机号不正确,请重新输入!");
					return;
			    }
            }
            
            $("#commentF").submit();
     	});
     </script>
</body>
</html>