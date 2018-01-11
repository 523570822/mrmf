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
    <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
</head>
<body class="bg_gray" >
    <div class="common_nav">
        <h4 class="font-34">申请加入</h4>
    </div>
    <input type="hidden" value="" id="organId"/>
    <input type="hidden" value="" id="organName"/>
	<div class="search_wrap">
		<div class="search_box">
			<input type="text" class="search_input" placeholder="输入店铺名字后,按搜索按钮选择店铺"/>
			<img id="search_organ" src="${ctxPath}/module/resources/images/home/icon_search.png" class="search_btn" />
			<ul class="search_suggest" style="display:none;" >
			</ul>
		</div>	
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


 <script src="${ctxPath}/module/staff/login/js/search.js"></script>  
</body>
</html>