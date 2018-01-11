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
<input type="hidden" value="${staffId }" id="staff_id">
<input type="hidden" value="orderDetail" id="status">
<input type="hidden" value="${order._id }" id="order_id">
	<div class="nav">
	    <i class="back" id="back"></i>
	    <h4 class="font-34">订单详情</h4>
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
	 				<c:if test="${order.state==1 }">待确认</c:if>
	 				<c:if test="${order.state==2 }">待支付</c:if>
	 				<c:if test="${order.state==3 }">待评价</c:if>
	 				<c:if test="${order.state==10 }">已完成</c:if>
	 			</span> 
	 			</div>
	 		</li>
	 		<li class="detailTitle" style="height: 2.2rem;">
	 			<div class="col-2"><span>预约时间</span> </div>
	 			<div class="col-2-7" style="width: 12rem;"><span>${order.orderTime }</span>
	 			 </div>
	 			 <!-- <div class="cake">
	 				<img src="../../images/img/icon_cake.png">
	 			 </div> -->
	 		</li>
	 		<li class="detailTitle" style="height: 2.2rem;margin-top:0.7rem; ">
	 			<div class="col-2"><span>用&#12288;&#12288;户</span> </div>
	 			<div class="col-1-1"><img src="${ossImageHost}${order.userImg}@!avatar"></div>
	 			<div class="col-2-7" style="padding-left: 0.9rem;"><span>${empty order.userName?order.userNick:order.userName }</span> </div>
	 		</li>
	 		<li class="detailTitle" style="height: 2.2rem;margin-top:0.35rem; ">
	 			<a class="custom_tel" href="tel:${order.userPhone }">
	 			<div class="col-2"><span>联系电话</span> </div>
	 			<div class="col-2-7"><span>${order.userPhone }</span> </div>
	 			<img src="${ctxPath }/module/resources/images/store/icon_telephone.png">
	 			</a>
	 		</li>
	 		<li class="detailTitle" style="height: 2.2rem;margin-top:0.35rem; ">
	 			<div class="col-2"><span>服务店铺</span> </div>
	 			<div class="col-2-7"><span>${order.organName }</span> </div>
	 		</li>
	 		<%-- <c:if test="${order.isMember==true}">
		 		<li class="detailTitle" style="height: 2.2rem;margin-top:0.35rem; " onclick="member('${order.userId}')">
		 			<div class="col-2"><span>用户类型</span> </div>
		 			<div class="col-2-7">
			 			<span>会员用户</span> 
			 			<div class="col-1-1-next next" style="float: right;margin: 0" ></div>
		 			</div>
		 		</li>
	 		</c:if> --%>
	 			<li class="detailTitle" style="height: 2.2rem;margin-top:0.35rem;">
	 			<div class="col-2"><span>用户类型</span> </div>
	 			<div class="col-2-7">
	 				<span>${order.isMember==true?'会员用户':'普通用户' }</span>
	 			</div>
	 			</li>	
	 		<li class="detailTitle" style="height: 2.2rem;margin-top:0.35rem; ">
	 			<div class="col-2"><span>付款金额</span> </div>
	 			<div class="col-2-7"><span>¥&nbsp;${order.orderPrice }</span> </div>
	 		</li>
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
			<button class="button-left font-34" onclick="refuse()">拒绝</button>
			<button class="button-right font-34" onclick="confirm()">确认</button>
		</c:if>
	</div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/mine.js"></script>
</body>
</html>