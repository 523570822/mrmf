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
    <style>
        body{text-align: center}
        .queding{
            background: #ff0000;
            height: 3.14rem;
            width: 60%;
            font-size: 2rem;
            font-weight: bold;
            color: #ffffff;
            margin-top: 25rem;
        }
    </style>
</head>
<body>
<form>
<input class="queding" type="button" value="领取" onclick="toRedWallet()">
<input type="hidden"  id="userId"  name="userId" value="${userId}">
</form>
</body>
</html>
<script>
    //确定抢红包
    function toRedWallet() {
        alert($("#userId").val());
        window.location.href="http://test.wangtiansoft.cn/mrmf/w/home/toQiangRed?userId="+$("userId").val();
    }
</script>
