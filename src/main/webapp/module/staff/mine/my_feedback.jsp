<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML >
<html>
  <head>
    <base href="<%=basePath%>">
    
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
  	<link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
</head>

<body class="bg_gray">
<input type="hidden" value="feedBack" id="status"/>
        <div class="common_nav feedback_top">
            <i id="back"></i>
            <h4 class="font-34 ">意见反馈</h4>
            <div id="savefeedback" class="font-28">发送</div>
        </div>
		
        <form class="comment_form feedback_form" id="feedback_form" method="post">
        <input type="hidden" value="${staffId }" name="staffId" id="staff_id"/>
            <textarea placeholder="请输入您的反馈意见..." name="fbcontent" id="content" style="color: #22242a;"></textarea>
        
        <div class="orderpayheader_div amount_div feedback_phone_div">
            <div class="amount_left feedback_phone_left">
                联系方式
            </div>
            <div class="amount_right feedback_phone_right">
                <input type="text" id="phone" name="contact" value="${staff.phone }" readonly="readonly"/>
            </div>
        </div>
        </form>
        <script src="${ctxPath}/module/staff/js/mine.js"></script>
</body>
</html>