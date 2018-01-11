<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/module/resources/wecommon.jsp" %>
<%
    String path = request.getContextPath( );
    String basePath = request.getScheme( ) + "://" + request.getServerName( ) + ":" + request.getServerPort( ) + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <meta name="description" content="This is  a .."/>
    <title>任性猫</title>
    <link rel="stylesheet" type="text/css" href="${ctxPath}/module/user/css/my_style.css"/>
</head>
<body>
<input type="hidden" name="list" id="list"  value="${userList}">
<div class="qiangRed">
    <div class="qiang_head">
        <i onclick="history.go(-1)"></i>
        <h4>红包详情</h4>
    </div>
    <div>
        <img src="${ctxPath}/module/user/images/tonxiang_try.png">
        <span>Joker</span>
        <span>恭喜发财，大吉大利</span>
        <span>0.10</span>
        <span>元</span>
    </div>
</div>
<section class="red-list">
    <ul>
        <li class="clearfix">
            <div>
                <img src="${ctxPath}/module/user/images/tonxiang_try.png">
            </div>
            <div>
                <p class="red-name">Joker</p>
                <p class="red-price">0.09元</p>
            </div>
        </li>
        <li class="clearfix">
            <div>
                <img src="${ctxPath}/module/user/images/tonxiang_try.png">
            </div>
            <div>
                <p class="red-name">Joker</p>
                <p class="red-price">0.09元</p>
            </div>
        </li>
        <li class="clearfix">
            <div>
                <img src="${ctxPath}/module/user/images/tonxiang_try.png">
            </div>
            <div>
                <p class="red-name">Joker</p>
                <p class="red-price">0.09元</p>
            </div>
        </li>
        <li class="clearfix">
            <div>
                <img src="${ctxPath}/module/user/images/tonxiang_try.png">
            </div>
            <div>
                <p class="red-name">Joker</p>
                <p class="red-price">0.09元</p>
            </div>
        </li>
    </ul>
</section>
<section class="red-code">
    <span>扫描下边不像二维码，但是真的是二维码的码</span>
    <img src="${ctxPath}/module/user/images/tonxiang_try.png">
</section>
<script>
    $(function () {
       alert($("#list").val());
    });
</script>
</body>
</html>
