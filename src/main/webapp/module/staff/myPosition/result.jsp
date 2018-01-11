<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<!DOCTYPE html>
<html>
<head>
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
    <%--<i id="back"></i>--%>
    <h4 class="font-34">预定结果</h4>
</div>

    <div class="result">
        <div class="result-div">
            <c:if test="${positionOrder.leaseType==0}">
                <div><img src="${ctxPath}/module/staff/images/img/icon_successed_pay.png"></div>
                <div style="text-align:center;"class="font-34"><span >支付成功</span></div>
                <div style="text-align: center">请您查看工位分配详情</div>
            </c:if>
            <c:if test="${positionOrder.leaseType==1}">
                <div><img src="${ctxPath}/module/staff/images/img/icon_successed_pay.png"></div>
                <div style="text-align:center;"class="font-34"><span >提交成功</span></div>
                <div>审核通过后我们会通知您，并为您分配工位</div>
            </c:if>
        </div>
    </div>
<div class="result" style="background-color: #f8f8f8">
    <div class="result-div">
        <c:if test="${positionOrder.leaseType==0}">
            <button class="button_submit1" id="myPositon">我的工位租赁</button>
        </c:if>
        <c:if test="${positionOrder.leaseType==1}">
            <button class="button_submit1" id="backIndex">返回首页</button>
        </c:if>
    </div>
</div>


<input type="hidden" id="staffId" value="${positionOrder.staffId}">
<input type="hidden" id="orderId" value="${positionOrder._id}">
<input type="hidden" id="organId" value="${positionOrder.organId}">
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script>
    $(document).ready(function () {
        var staffId = $("#staffId").val();
        var organId = $("#organId").val();
        var positionOrderId = $("#orderId").val();
        $("#myPositon").click(function () {//跳转我的工位租赁
            window.location.href="${ctxPath}/w/staffMy/myStationDetail?organId="+organId+"&staffId="+staffId+"&positionOrderId="+positionOrderId;
        });
        $("#backIndex").click(function () {//跳转首页
            window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa60e8354c2a33648&redirect_uri=http%3a%2f%2frxmao.cn%2fmrmf%2fw%2fstaff%2fwxlogin.do&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect"
        });
    })
</script>
</body>
</html>
