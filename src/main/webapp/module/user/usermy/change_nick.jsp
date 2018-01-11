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
        <h4 class="font-34">设置昵称</h4>
        <span id="nameFinished" class="finished">完成</span>
    </div>
    <input type="hidden" id="id" value="${ user._id }" />
    <div class="change_box">
        <ul>
            <li>
                <div class="col-2">昵称</div>
                <div class="col-8">
                    <input id="nameVal" type="text" placeholder="请输入昵称" value="${ user.nick}"/>
                </div>
                <div class="col-8" style="margin-top: 10px;line-height: 20px;color:#ccc;">
                    <span>昵称为2-8位中文、字母、数字、下划线</span>
                </div>
            </li>
        </ul>
    </div>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script type="text/javascript">
    	$("#nameFinished").click(function(){
    		var id = $("#id").val();
	    	var nameVal = $("#nameVal").val();
	    	var reg=/^[a-zA-Z0-9_\u4e00-\u9fa5]{2,8}$/;
	    	if(!reg.test(nameVal)){
	    		layer.msg("不符合昵称标准",{icon:2});
	    		return;
	    	}
	        window.location.href=_ctxPath+"/w/userMy/toSetHomeByNick?id=" + id+"&nameVal="+encodeURIComponent(encodeURIComponent(nameVal));
    	});
    </script>
</body>
</html>