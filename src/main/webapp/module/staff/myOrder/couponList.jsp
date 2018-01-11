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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
		.coupom-list-box li{padding: 0 1rem ; margin-bottom: 1rem; background: #fff;}
	</style>

</head>
<body class="couponList" style="background: #f8f8f8;">
	 <input type="hidden" value="${userId}" id="user_id" name="userId">
	 <input type="hidden" value="" id="page"/>
	 <input type="hidden" value="" id="pages"/>
	 <input type="hidden" value="1" id="type"/>
	 <input type="hidden" value="myCoupon" id="status">
	<nav class="nav">
		<div class="col-3">
			<i class="back" id="back"></i>
		</div>
	    <div class="col-4">
			<h4 class="font-34">我的优惠券</h4>
		</div>
		<div class="col-3"></div>
	 </nav>

	 <%--<div class="messTitle">--%>
	 	<%--<div class="order-title sel_message">--%>
			<%--<h4 class="customer-title2" id="pMess" style="margin-left: 2rem;" >全部</h4>--%>
		<%--</div>--%>
		<%--<div class="order-title ">--%>
			<%--<h4 class="customer-title2" id="cMess" >未使用</h4>--%>
		<%--</div>--%>
		<%--<div class="order-title ">--%>
			<%--<h4 class="customer-title2" id="cMess" >已使用</h4>--%>
		<%--</div>--%>
		<%--<div class="order-title " >--%>
			<%--<h4 class="customer-title2" id="cMess" >已过期</h4>--%>
		<%--</div>--%>
	 <%--</div>--%>

	 <section class="control-tab">
		 <ul class="clearfix">
			 <li class="active">全部<i></i></li>
			 <li>未使用<i></i></li>
			 <li>已使用<i></i></li>
			 <li>已过期<i></i></li>
		 </ul>
	 </section>

	 <div class="coupon-list-box" style="background-color:#f8f8f8">
	 	<ul id="coupon_list" >
			<%--<li class="coupon-dued clear">
				<div>
					<p>￥<span>200</span></p>
					<span>满99元可用</span>
				</div>
				<div>
					<div>
						<h4>任性猫代金券</h4>
					</div>
					<div>
						<time>有效期至 2016/12/31</time>
					</div>
				</div>
			</li>
			<li class="coupon-used">
				<div>
					<p>￥<span>200</span></p>
					<span>满99元可用</span>
				</div>
				<div>
					<div>
						<h4>任性猫代金券</h4>
					</div>
					<div>
						<time>有效期至 2016/12/31</time>
					</div>
				</div>
			</li>
			<li class="coupon-use">
				<div>
					<p>￥<span>200</span></p>
					<span>满99元可用</span>
				</div>
				<div>
					<div>
						<h4>任性猫代金券</h4>
					</div>
					<div>
						<time>有效期至 2016/12/31</time>
					</div>
				</div>
			</li>--%>
		</ul>
	 </div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/myOrder/js/coupon_page.js"></script>
<script src="${ctxPath}/module/user/usermy/myOrder/js/mine1.js"></script>
</body>
</html>