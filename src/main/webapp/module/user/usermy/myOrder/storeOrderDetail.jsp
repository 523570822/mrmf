<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
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
<title>订单详情</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/module/staff/css/style_mainPage.css"/>
</head>
<body class="orderDetail">
<input type="hidden" value="orderDetail" id="status">
<input type="hidden" value="${order._id }" id="order_id">
	<div class="nav">
	    <i class="back" id="back"></i>
	    <h4 class="font-34">订单详情</h4>
	 </div>
	 <div>
	 	<ul>
	 		<li class="detailTitle">
	 			<div class="col-7">
	 				<div class="col-1">
	 				<c:if test="${order.serverType==1 }"><img src="${ctxPath }/module/staff/images/img/icon_haircut_bill.png"></c:if>
	 				<c:if test="${order.serverType==2 }"><img src="${ctxPath }/module/staff/images/img/icon_cosmetology.png"></c:if>
	 				<c:if test="${order.serverType==3 }"><img src="${ctxPath }/module/staff/images/img/icon_nail.png"></c:if>
	 				<c:if test="${order.serverType==4 }"><img src="${ctxPath }/module/staff/images/img/icon_foot.png"></c:if>
	 				</div>
	 				<div class="col-6"><span>${order.title }</span> </div>
	 			</div>
	 			<div class="status">
	 			<span>
	 				<c:if test="${order.state==1 }">预约中</c:if>
	 				<c:if test="${order.state==2 }">待支付</c:if>
	 				<c:if test="${order.state==3 }">待评价</c:if>
	 				<c:if test="${order.state==10 }">已完成</c:if>
	 			</span> 
	 			</div>
	 		</li>
	 		<li class="detailTitle" style="height: 2.2rem;">
	 			<div class="col-2"><span>预约时间</span> </div>
	 			<div class="col-2-7" style="width: 10.8rem;"><span>${order.orderTime }</span>
	 			 </div>
	 			 <!-- <div class="cake">
	 				<img src="../../images/img/icon_cake.png">
	 			 </div> -->
	 		</li>
	 		<li class="detailTitle" style="height: 2.2rem;margin-top:0.7rem;" >
	 			<a href="tel:${organ.phone }">
	 			<div class="col-2"><span>店铺电话</span></div>
	 			<div class="col-2-7" style="padding-left: 0.9rem;"><span>${organ.phone}</span> </div>
	 			<i class="staff_phone"></i>
	 			</a>
	 		</li>
	 		<li class="detailTitle" style="height: 2.2rem;margin-top:0.35rem; ">
	 			<div class="col-2"><span>服务店铺</span> </div>
	 			<div class="col-2-7"><span>${organ.name }</span> </div>
	 		</li>
	 		<li class="detailTitle" style="height: 2.2rem;margin-top:0.35rem; ">
	 			<div class="col-2"><span>店铺地址</span> </div>
	 			<div class="col-2-7"><span>${organ.address }</span> </div>
	 		</li>
	 		<c:if test="${order.state==3 ||order.state==10 }">
		 		<li class="detailTitle" style="height: 2.2rem;margin-top:0.35rem; ">
		 			<div class="col-2"><span>支付价格</span></div>
		 			<div class="col-2-7"><span>¥${order.orderPrice }</span> </div>
		 		</li>
	 		</c:if>
	 		
	 		<c:if test="${order.state==10 }">
	 			<li class="detailTitle" style="height: 2.2rem;margin-top:0.35rem; ">
	 			<div class="col-2"><span>顾客评价</span> </div>
	 			<div class="col-1-4">
	 				<c:if test="${order.starZan==0 }">
	 				</c:if>
	 				<c:if test="${order.starZan==1 }">
	 					<i class="comment"></i>
	 				</c:if>
	 				<c:if test="${order.starZan==2 }">
	 					<i class="comment"></i>
	 					<i class="comment"></i>
	 				</c:if>
	 				<c:if test="${order.starZan==3 }">
	 					<i class="comment"></i>
	 					<i class="comment"></i>
	 					<i class="comment"></i>
	 				</c:if>
	 				<c:if test="${order.starZan==4 }">
	 					<i class="comment"></i>
	 					<i class="comment"></i>
	 					<i class="comment"></i>
	 					<i class="comment"></i>
	 				</c:if>
	 				<c:if test="${order.starZan==5 }">
	 					<i class="comment"></i>
	 					<i class="comment"></i>
	 					<i class="comment"></i>
	 					<i class="comment"></i>
	 					<i class="comment"></i>
	 				</c:if>
	 			</div>
	 		</li>
	 		<li class="detailTitle" style="height: 4.2rem;letter-spacing: 2px; ">
	 			<div class="col-text"><span>${order.content }</span> </div>
	 		</li>
	 		</c:if>
	 	</ul>
	 </div>
	 <div class="bottom">
		<c:if test="${order.state==1 }">
		<button class="button-left font-34" style="float: right" onclick="cancelOrder()">取消订单</button>
		</c:if>
		<c:if test="${order.state==2 }">
		<button class="button-left font-34" onclick="cancelOrder()">取消订单</button>
		<button class="button-right font-34" onclick="pay()">支付</button>
		</c:if>
		<c:if test="${order.state==3 }">
		<button class="button-right font-34" onclick="comment()">评价</button>
		</c:if>
	</div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/myOrder/js/my_order.js"></script>
</body>
</html>