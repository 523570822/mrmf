<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/module/resources/wecommon.jsp" %>
<%
    String path = request.getContextPath( );
    String basePath = request.getScheme( ) + "://" + request.getServerName( ) + ":" + request.getServerPort( ) + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/my_style.css"/>
    <link rel="stylesheet" href="${ctxPath}/module/aboutus/welcome.css"/>
</head>

<body>
<div style="padding: 0 1rem;">
    <h6 class="rich_media_title">终于等到你~欢迎来到任性猫</h6>
</div>

<div class="txt_wrap">
    <section class="rich_media_title1" data-brushtype="text" >
        当你点进来时,我的心跳漏了一拍
    </section>
    <section class="rich_media_title2" data-width="100%">
    </section>
    <p class="rich_media_title3">欢迎光临！奴家等你很久啦~</span>
    </p>
    <p class="rich_media_title3"><span style="line-height: 1.75em;">任性猫美业互联网平台</span><br>
    </p>
    <p class="rich_media_title3">一个集美发、美容、美甲于一体的微信平台。</p>
    <p class="rich_media_title3">平台下单，不用办卡直接享受六折哟~</p>
    <p class="rich_media_title3">下面给大家介绍如何使用平台吧！</p>
</div>
<div>
   <div class="clearfix" style="margin-left: 1rem">
       <p class="rich_media_title4">&nbsp;<br></p>
       <p class="rich_media_title5"><span style="font-size: 1rem; color: #F87A7A;">如何在平台里预约支付？</span></p>
   </div>
    <div style="text-align: center" >
    <video controls="controls" style="width: 24rem; height: 18rem">
        <source src="${ctxPath}/module/aboutus/my/pay.mp4" type="video/mp4"/>
    </video>
    </div>
    <p class="rich_media_title6">
        <span style="line-height: 1.75em;">你Get到了吗？</span><br>
    </p>
    <div class="clearfix" style="margin-left: 1rem">
    <p class="rich_media_title4">&nbsp;<br></p>
    <p class="rich_media_title5"><span style="font-size: 1rem; color: #F87A7A;">如何在平台里支付？</span></p>
    </div>
    <div style="text-align: center">
        <video controls="controls" style="width: 24rem; height: 18rem">
            <source src="${ctxPath}/module/aboutus/my/tixian.mp4" type="video/mp4"/>
        </video>
    </div>
    <p class="rich_media_title6">
        <span style="line-height: 1.75em;">花钱不用教！大家都是尖子生！</span><br>
    </p>
    <div class="clearfix" style="margin-left: 1rem">
    <p class="rich_media_title4">&nbsp;<br></p>
    <p class="rich_media_title5"><span style="font-size: 1rem; color: #F87A7A;">如何在平台里提现？</span></p>
    </div>
    <div style="text-align: center">
    <video controls="controls" style="width: 24rem; height: 18rem">
        <source src="${ctxPath}/module/aboutus/my/order.mp4" type="video/mp4"/>
    </video>
    </div>
    <p class="rich_media_title6">
        <span style="line-height: 1.75em;">没毛病！一分都要提出来！</span><br>
    </p>

</div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
</body>
</html>