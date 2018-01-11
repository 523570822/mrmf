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
        <h4 class="font-34">设置生日 </h4>
        <span id="birthdayFinished" class="finished">完成</span>
    </div>
    <input type="hidden" id="id" value="${ user._id }" />
    <div class="change_box">
        <ul>
            <li>
                <div class="col-2">生日</div>
                <div class="col-8">
                    <input id="birthday" class="laydate-icon" type="text" readonly="readonly" placeholder="请选择生日" value="<fmt:formatDate value="${ user.birthday }" type="date" />"/>
                </div>
            </li>
        </ul>
    </div>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
     <script type="text/javascript">
    	$("#birthdayFinished").click(function(){
    		var id = $("#id").val();
	    	var birthday = $("#birthday").val();
	    	if(isEmpty(birthday)) {
	    		alert('生日不能为空！');
	    		return;
	    	}
	        window.location.href=_ctxPath+"/w/userMy/toSetHomeByBirthday?id=" + id+"&birthday="+encodeURIComponent(encodeURIComponent(birthday));
    	});
    	/*  判断是否为空！
		*/
		function isEmpty(str) {
			if(str == '' || typeof str=='undefined' || str == null) {
				return true;
			} else {
				return false;
			}
		}
    	laydate({
		    elem: '#birthday',
		    event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		});
     	
    </script>
</body>
</html>