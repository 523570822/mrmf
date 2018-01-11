<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
</head>
<body class="bg_gray">
    <div class="common_nav">
        <i onclick="window.history.go(-1);"></i>
        <h4 class="font-34">验证手机号码</h4>
    </div>

    <div class="pay_pwd_div">
        <div>
            <div class="pay_pwd_email">
                <span>手机号</span>
            </div>
            <div class="pay_pwd_input">
                <input type="text" placeholder="输入手机号码" id="phone"/>
            </div>
            <div class="get_code">
                <div>
                    获取验证码
                </div>
            </div>
        </div>
    </div>

    <div class="code_div">
        <div>
            <div class="phone_code_name">
                <span>验证码</span>
            </div>

            <div class="pay_code_value">
                <input type="text" placeholder="输入验证码" id="code"/>
            </div>
        </div>
    </div>
    <div class="btn_next">
        <button>
            提交
        </button>
    </div>


 <script src="${ctxPath}/module/user/userlogin/js/phone.js"></script>  
</body>
</html>