<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/style_user_index.css"/>
</head>
<body class="bg_gray">
    <div class="common_nav">
        <i onclick="window.location.href='${ctxPath}/w/home/staffDetail?staffId=${staff._id}'"></i>
        <h4 class="font-34">预约信息</h4>
    </div>

    <div class="technician_info">
        <div>
            <div class="col-2">
                <span>预约技师</span>
            </div>
            <div class="col-8">
                <img src="${ossImageHost}${staff.logo}@!style400" />
                <i>${staff.name}</i>
            </div>
        </div>
    </div>
    <div class="time_store" id="apTime">
        <div>
            <div class="col-2">
                <span>预约时间</span>
            </div>
            <div class="col-7">
                <label id="ap_time">
                    <c:choose>
                        <c:when test="${empty date}">请选择预约时间</c:when>
                        <c:otherwise>${date}</c:otherwise>
                    </c:choose>
                </label>
            </div>
            <div class="col-1 time_store_next"  >
            </div>
        </div>
    </div>
    <div class="time_store" id="selectType">
        <div>
            <div class="col-2">
                <span>服务类型</span>
            </div>
            <div class="col-7">
                <label id="selectedType">
                	 <c:choose>
		           		 <c:when test="${empty type}">请选择服务类型</c:when>
		        		 <c:otherwise>${type}</c:otherwise>
	        		</c:choose>
                </label>
            </div>
            <div class="col-1 time_store_next">
            </div>
        </div>
    </div>

    <div class="time_store" id="goOrganMap">
        <div>
            <div class="col-2">
                <span>预约店铺</span>
            </div>
            <div class="col-7">
                <label id="ap_store">
                      <c:choose>
		           		  <c:when test="${empty organName}">店铺地址</c:when>
		        		  <c:otherwise>${organName}</c:otherwise>
	        		   </c:choose>
                </label>
            </div>
            <div class="col-1 time_store_location"  >
            </div>
        </div>
    </div>
    <!-- 地址和店铺 -->
	<form id="addressAndTime" action="${ctxPath}/w/home/toSelectType" method="post" >
		<input id ="appointTime" type="hidden" name="appointTime"  />
		<input id ="organName" type="hidden" name="organName" />
		<input type="hidden" id="staffId" name="staffId" value="${staff._id}"/>
		<input id="organId" name="organId" type="hidden" value="${organId}" /> 
	</form>
	<input id ="orderPrice" type="hidden" name="orderPrice" value="${staff.startPrice}" />
	<form id="typeForm"  action="${ctxPath}/w/home/toAppointTimeByStaff" method="post" >
		<input id ="type" type="hidden" name="type"  />
		<input type="hidden" id="staffId" name="staffId" value="${staff._id}"/>
	</form>	
	<div class="model_bg_appoint" >
    </div>
	 <!-- 弹出框样式 -->
    <div  class="modal_appoint">
        <div class="modal_appoint_title">
            <h4>提交成功</h4>
            <p>预约信息提交成功，请你耐心等待。</p>
        </div>
        <div class="modal_appoint_fun">
            <div class="ret_home">
                <span>返回首页</span>
            </div>
            <div class="order_info">
                <span>订单详情</span>
                <input type="hidden" id="orderId" />
            </div>
        </div>
    </div>
    <div class="common_btn">
        <button>
                              提交
        </button>
    </div>
     <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
     <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
     <script src="${ctxPath}/module/user/userhome/js/appointDetail.js"></script>
</body>
</html>