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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
<title>我的</title>
<link href="${ctxPath}/module/staff/css/style_mainPage.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8;">
<form action="" method="post" id="mine_form">
	<input type="hidden" value="${staff._id }" name="staffId" id="staff_id">
	<input type="hidden" value="1" name="page" id="page">
	<input type="hidden" value="10" name="size" id="size">
</form>
<form action="" method="post" id="staff_mainPage">
	<input type="hidden" value="${staff._id }" name="staffId" id="staff_id">
</form>
		<div class="top">我的</div>
		<div class="body">
			<ul>
				<li id="my_customer">
					<div class="col-1"><img src="${ctxPath }/module/staff/images/img/icon_my_client.png"></div>
					<div class="col-6 font-30">我的客户</div>
					<div class="col-2 next" ></div>
				</li>
				<li id="my_order">
					<div class="col-1"><img src="${ctxPath }/module/staff/images/img/icon_my_list.png"></div>
					<div class="col-6 font-30">我的订单</div>
					<div class="col-2 next" ></div>
				</li>
				<li id="my_code">
					<div class="col-1"><img src="${ctxPath}/module/resources/images/store/icon_code.png"></div>
					<div class="col-6 font-30">分账二维码</div>
					<div class="col-2 next" ></div>
				</li>
				<li id="my_twoCode">
					<div class="col-1"><img src="${ctxPath}/module/resources/images/store/icon_code.png"></div>
					<div class="col-6 font-30">我的二维码</div>
					<div class="col-2 next" ></div>
				</li>
				<li id="my_profit">
					<div class="col-1"><img src="${ctxPath }/module/staff/images/img/icon_belt.png"></div>
					<div class="col-6 font-30">我的收入</div>
					<div class="col-2 next" ></div>
				</li>
				<li id="my_station">
					<div class="col-1"><img src="${ctxPath }/module/staff/images/img/icon_belt.png"></div>
					<div class="col-6 font-30">我的工位租赁</div>
					<div class="col-2 next" ></div>
				</li>
				<li id="my_earn">
					<div class="col-1"><img src="${ctxPath }/module/staff/images/img/icon_belt.png"></div>
					<div class="col-6 font-30">我的收益</div>
					<div class="col-2 next" ></div>
				</li>
				<li id="my_estimation">
					<div class="col-1"><img src="${ctxPath }/module/staff/images/img/icon_my_critic.png"></div>
					<div class="col-6 font-30">我的评价</div>
					<div class="col-2 next" ></div>
				</li>
				<li id="send_redPacket">
					<div class="col-1"><img src="${ctxPath }/module/staff/images/img/icon_red_packet.png"></div>
					<div class="col-6 font-30">发红包</div>
					<div class="col-2 next" ></div>
				</li>
				<li id="my_message">
					<div class="col-1"><img src="${ctxPath }/module/staff/images/img/icon_message.png"></div>
					<div class="col-6 font-30"><p style="float: left;">我的消息</p>
					<c:if test="${messageCount > 0}">
						<div style="margin-left: 1rem;
							    float: left;
							    border: 1px red solid;
							    width: 0.7rem;
							    border-radius: 0.6rem;
							    height: 0.7rem;
							    line-height:normal;
							    margin-top:1.3rem;
							    text-align:center;
							    background:red;
							"><%-- <span style="line-height:2rem;color:white;">${messageCount}</span> --%>
						</div>
					</c:if>
				</div>
				<div class="col-2 next" ></div>
				</li>
				<li id="my_setup">
					<div class="col-1"><img src="${ctxPath }/module/staff/images/img/icon_my_set.png"></div>
					<div class="col-6 font-30">设置</div>
					<div class="col-2 next" ></div>
				</li>
			</ul>
		</div>
		<div class="bottom">
			<div id="main_page" class="col-2" >
				<i class="homenor"></i>
				<h4 >信息管理</h4>
			</div>
			<div id="ask_price" class="col-2" >
				<i class="asknor"></i>
				<h4>询价报价</h4>
			</div>
			<div id="sign_in" class="col-2" >
				<i class="signinnor"></i>
				<h4>签到</h4>
			</div>
			<div class="col-2" >
				<i class="mypre"></i>
				<h4 style="color: #f4370b;">我的</h4>
			</div>
		</div>

<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script src="${ctxPath}/module/staff/js/mine.js"></script>
</body>
</html>