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
        <h4 class="font-34">设置邮箱 </h4>
        <span id="emailFinished" class="finished">完成</span>
    </div>
    <div class="change_box">
        <ul>
            <li>
                <div class="col-2">邮箱</div>
                <div class="col-8">
                    <input id="email" type="text" placeholder="请输入邮箱" value="${user.email}"  />
                </div>
            </li>
        </ul>
    </div>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
     <script type="text/javascript">
    	$("#emailFinished").click(function(){
	    	var email = $("#email").val();
	    	if(isEmpty(email)) {
	    		alert('邮箱不能为空！');
	    	}
	    	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	    	if(!reg.test(email)) {
	    		alert("邮箱格式输入不正确！");
	    		return;
	    	}
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
	        window.location.href=_ctxPath+"/w/userMy/toSetHomeByEmail?email="+encodeURIComponent(encodeURIComponent(email));
    	});
    </script>
</body>
</html>