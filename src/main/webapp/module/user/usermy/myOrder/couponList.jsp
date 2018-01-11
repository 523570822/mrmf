<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
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
    <title>我的优惠券</title>
    <%--<link rel="stylesheet" type="text/css" href="${ctxPath}/module/staff/css/style_mainPage.css"/>--%>
    <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
    <style>
        .coupom-list-box li {
            padding: 0 1rem;
            margin-bottom: 1rem;
            background: #fff;
        }
    </style>
</head>
<body class="orderList" style="background-color:#f8f8f8">
<input type="hidden" value="${user._id}" id="user_id" name="userId">
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="1" id="type"/>
<input type="hidden" value="myOrder" id="status">
<nav class="nav">
    <div class="col-3">
        <i class="back" id="back"></i>
    </div>
    <div class="col-4">
        <h4 class="font-34">使用优惠券</h4>
    </div>
    <div class="col-3"></div>
</nav>
<div class="coupon-list-box" >
    <ul class="coupon_list" style="padding-top: 4.2rem;">
        <c:if test="${myCouponList.size() > 0}">
            <c:forEach items="${myCouponList}" var="mycoupon">
                 <li class="coupon-use clearfix" onclick="coupon('${mycoupon._id}')" >
                    <div>
                        <c:if test="${mycoupon.minConsume!=0}">
                            <c:if test="${mycoupon.moneyType==0}"><p>￥<span><fmt:formatNumber value="${mycoupon.moneyOrRatio}" pattern="#0.0"/></span></p></c:if>
                            <c:if test="${mycoupon.moneyType==1}"><p><span><fmt:formatNumber type="number" value="${mycoupon.moneyOrRatio*10}" maxFractionDigits="2"/></span>折</p></c:if>
                            <span>满${mycoupon.minConsume}可用</span>
                        </c:if>

                        <c:if test="${mycoupon.minConsume==0}">
                            <c:if test="${mycoupon.moneyType==0}"><p style="height: 6.8rem; line-height: 6.8rem;">￥
                                <span><fmt:formatNumber value="${mycoupon.moneyOrRatio}" pattern="#0.0"/></span></p></c:if>
                            <c:if test="${mycoupon.moneyType==1}"><p style="height: 6.8rem; line-height: 6.8rem;">折
                                <span><fmt:formatNumber type="number" value="${mycoupon.moneyOrRatio*10}" maxFractionDigits="2"/> </span></p></c:if>
                        </c:if>
                    </div>
                    <div>
                        <div>
                            <c:if test="${mycoupon.couponType=='店面'}">
                                <c:if test="${mycoupon.typeName!='通用类型'}">
                                    <c:if test="${not empty mycoupon.bigSortName}">
                                       <h4>${mycoupon.shopName}专用(${mycoupon.typeName}&nbsp;${mycoupon.bigSortName})</h4>
                                    </c:if>
                                    <c:if test="${empty mycoupon.bigSortName}">
                                        <h4>${mycoupon.shopName}专用(${mycoupon.typeName})</h4>
                                    </c:if>
                                </c:if>
                                <c:if test="${mycoupon.typeName=='通用类型'}">
                                    <h4>${mycoupon.shopName}专用</h4>
                                </c:if>
                            </c:if>
                            <c:if test="${mycoupon.couponType!='店面'}">
                                <c:if test="${mycoupon.typeName!='通用类型'}">
                                    <c:if test="${not empty mycoupon.bigSortName}">
                                        <h4>平台通用(${mycoupon.typeName}&nbsp;${mycoupon.bigSortName})</h4>
                                    </c:if>
                                    <c:if test="${empty mycoupon.bigSortName}">
                                        <h4>平台通用(${mycoupon.typeName})</h4>
                                    </c:if>
                                </c:if>
                                <c:if test="${mycoupon.typeName=='通用类型'}">
                                    <h4>平台通用</h4>
                                </c:if>
                            </c:if>
                        </div>
                        <div>
                            <time> 有效期至 <fmt:formatDate value="${mycoupon.endTime}"></fmt:formatDate></time>
                        </div>
                    </div>
                 </li>
            </c:forEach>
        </c:if>
    </ul>
</div>
<input type="hidden" id="order_id" value="${orderId}">
<input type="hidden" id="price" value="${price}">

<script>

    function coupon(id) {
        var orderId = $("#order_id").val();
        var price = $("#price").val();
        window.location.href = _ctxPath + "/w/userMy/orderPay.do?orderId=" + orderId + "&couponId=" + id + "&price=" + price;
    }

    $("#back").click(function () {
        history.go(-1);
    })

</script>
</body>
</html>