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
    <title>订单详情</title>
    <link href="${ctxPath}/module/organ/store/css/style_pos.css" rel="stylesheet">
</head>
<body style="background: #f7f7f7">
<div class="register_nav">
    <i class="back" id="orderDetail_back"></i>
    <h4 class="font-34">订单详情</h4>
    <input type="hidden" value="${organId }" id="organId"/>
    <input type="hidden" value="${weOrganOrder.userId }" id="user_id"/>
</div>
<div class="pos_top">
<div class="order_top">
    <div class="order_top_1">
        <div class="col-5">
        <c:choose> 
        	<c:when test="${ empty weOrganOrder.organLogo}">
        	<img src="${ctxPath}/module/resources/images/nopicture.jpg">
        	</c:when>
        	<c:otherwise><img src="${ossImageHost}${weOrganOrder.organLogo }@!style400" alt="头像"/></c:otherwise>
         </c:choose>
            
            <span class="font-30">${weOrganOrder.title }</span>
        </div>
        <div class="col-5 state">已完成</div>
    </div>
    <div class="order_top_2">
        <div class="col-2">预约时间</div>
        <div class="col-8">${weOrganOrder.orderTime }</div>
    </div>
</div>
<ul class="info_list">
    <li>
        <div class="col-2">用      户</div>
        <div class="col-8">
        <c:choose> 
        	<c:when test="${ empty weOrganOrder.userImg}">
        	<img src="${ctxPath}/module/resources/images/nopicture.jpg">
        	</c:when>
        	<c:otherwise><img src="${ossImageHost}${weOrganOrder.userImg }@!style400" alt="头像"/></c:otherwise>
         </c:choose>
            
            <span>${weOrganOrder.userNick }</span>
        </div>
    </li>
    <li class="tel_pos">
    	<a href="tel:${weOrganOrder.userPhone }">
        <div class="col-2">联系电话</div>
        <div class="col-8">${weOrganOrder.userPhone }</div>
        <img class="tel" src="${ctxPath}/module/resources/images/store/icon_telephone.png">
        </a>
    </li>
    <li>
        <div class="col-2">服务店铺</div>
        <div class="col-8">${weOrganOrder.organName }</div>
    </li>
    <li>
        <div class="col-2">用户类型</div>
        <c:if test="${weOrganOrder.isMember==true }">
	        <div class="col-7">会员客户</div>
	        <div class="col-1 tag_arrow" onclick="customDetail()"></div>
        </c:if>
        <c:if test="${weOrganOrder.isMember==false }">
	        <div class="col-7">普通客户</div>
        </c:if>
    </li>
    <li>
        <div class="col-2">付款金额</div>
        <div class="col-8">￥${weOrganOrder.orderPrice }</div>
    </li>
    <li>
        <div class="col-2">顾客评价</div>
        <div class="col-8">
        <input type="hidden" value="${weOrganOrder.starZan }" id="starZan"/>
            <ul class="flower" id="starZan_flower">
            </ul>
        </div>
    </li>
    <div class="judge_txt">
        <p class="font-30">${weOrganOrder.content }</p>
    </div>
</ul>
</div>

<script src="${ctxPath}/module/organ/store/js/order.js"></script>
</body>
</html>