<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
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
<title>会员卡详情</title>
<link rel="stylesheet" href="${ctxPath}/module/user/css/my_style.css"/>
</head>
<body>
<input type="hidden" value="${userCard._id }" id="card_id">
<input type="hidden" value="${user._id }" id="user_id">
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="1" id="type">
<!-- common_nav start -->
	<div class="common_nav card_nav">
        <i id="back"></i>
        <h4 class="font-34">我的会员卡</h4>
        <c:if test="${ !(empty userinincard) }">
           <a class="to_view font-28" id="my_incard">查看子卡</a>
        </c:if>
    </div>
	<!-- common_nav end -->

    <!-- card_img start -->
    <div class="card_img">
		<a>
			<p>${userCard.organName }</p>
			<p>${userCard.cardLevel }</p>
		</a>
    </div>
    <!-- card_img end -->

    <!-- card_info start -->
    <div class="card_info">
		<a class="font-28 cA">基本信息</a><a class="font-28">消费记录</a><a class="font-28">充值记录</a>
    </div>
    <!-- card_info end -->

    <!-- basic_info_list start -->
    <ul class="basic_info_list" id="card_list">
		
    </ul>
    <!-- basic_info_list end -->
	<div style="height:3.5rem;width:100%;float:right;background:#fff"></div>
    <!-- button_box start -->
    <div class="button_box">
    	<div>
			<a class="but_com stores" onclick="cardStore()">查看门店</a>
			<c:choose>
				<c:when test="${canCharge==false}">
					<a href="javascript:void(0);" class="but_com top-down" >在线充值</a>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${usersort.flag1==1002||usersort.flag1==1003 }">
							<a href="javascript:void(0);" class="but_com top-up" onclick="payOnline()">在线充值</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:void(0);" class="but_com top-down" >在线充值</a>
						</c:otherwise>
					</c:choose>
			</c:otherwise>
			</c:choose>
			<input type="hidden" value="${usersort.flag1}" id="usersort"/>
    	</div>
		
    </div>
    <!-- button_box end-->
<script src="${ctxPath}/module/user/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/myvip/js/my_card_detail.js"></script>
</body>
</html>