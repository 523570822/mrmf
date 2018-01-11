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
        <div class="common_nav ">
            <i onclick="window.location.href='${ctxPath}/w/userMy/toUserMyHome'"></i>
            <h4 class="font-34 ">设置</h4>
        </div>

        <div class="status_div setup_head_div bottom_one" onclick="window.location.href= '${ctxPath}/w/userMy/toMyFeedBack'">
            <div class=" border_bottom_94 setup_feedback_div">
                <span>意见反馈</span>
                <div class="staff_name" >
                    <i></i>
                </div>
            </div>
        </div>
        <div class="status_div setup_head_div top_ziro " onclick="window.location.href='${ctxPath}/w/userMy/toMyUs'">
            <div class="border_bottom_94 setup_feedback_div none_bottom_border">
                <span>关于我们</span>
                <div class="staff_name" >
                    <i></i>
                </div>
            </div>
        </div>

       <!--  <div class="status_div loginout_div">
                         退出登录
        </div> -->
        <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
     <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
     <script src="${ctxPath}/module/user/usermy/js/wallet.js"></script>
</body>
</html>