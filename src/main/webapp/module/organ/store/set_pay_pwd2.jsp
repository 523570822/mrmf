<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
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
    <i id="setpwd2back"></i>
    <h4 class="font-34">设置支付密码</h4>
</div>
<div class="gray_input_pwd">
    <div>
            <span>
              请再次输入密码
            </span>
    </div>
</div>
<div class="input_pwd_div">
    <div class='virbox1'>
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        <span></span>
    </div>
    <form method="post" id="organPwdTwo">
    <input type="password" class="realbox1" maxlength="6" name="pwd2" id="pwd2">
    <input type="hidden" name="pwd1" value="${pwd }" id="pwd1"/>
    <input type="hidden" name="organId" value="${organId }" id="organId"/>
    </form>
</div>
<div class="btn_next" id="pwdTwoSub">
    <button>
        提交
    </button>
</div>
     <script src="${ctxPath}/module/resources/js/common.js"></script>
    <script src="${ctxPath}/module/organ/store/js/organset.js"></script>
</body>
</html>