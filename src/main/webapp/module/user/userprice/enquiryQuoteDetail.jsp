<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<title>报价详情</title>
<link href="${ctxPath}/module/user/css/style.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8">
<div class="register_nav">
    <i class="back" id="back"></i>
    <h4 class="font-34">预约</h4>
</div>
<input type="hidden" value="${staffState }" id="staff_state">
<form action="" id="quote_form" method="post">
<input type="hidden" value="${userId }" id="user_id" name="userId">
<input type="hidden" value="${staff._id }" id="staff_id" name="staffId">
<input type="hidden" value="${weUserInquiryQuote.inquiryId}" id="quote_id" name="quoteId">
<input type="hidden" value="${weUserInquiryQuote._id}" id="reply_id" name="replyId">
<input type="hidden" value="${organId }" id="organ_id" name="organId">
<input type="hidden" value="${date }" id="orderTime" name="orderTime">
<input type="hidden" value="${weUserInquiryQuote.price }" id="price" name="price">
<input type="hidden" value="${timeNum }" id="timeNum" name="timeNum">
<input type="hidden" value="${day }" id="day" name="day">
<input type="hidden" value="quoteDetail" id="status">
</form>
<div class="appointment_div" >
    <div class="appointment_price">
            <span class="price_font_small">￥</span>
            <span class="price_font_big">${weUserInquiryQuote.price }</span>
    </div>
    <div class="person_div" >
        <div class="person">
            <div class="person_img_div">
                <img  class="person_img" src="${ossImageHost }${weUserInquiryQuote.logo}@!style400" />
                <span class="person_font">${staff.name }</span>
            </div>
            <div>
                <ul class="listroles">
                    <c:if test="${staff.zanLevel==1 }">
                    <i></i>
                    </c:if>
                    <c:if test="${staff.zanLevel==2 }">
                    <i></i><i></i>
                    </c:if>
                    <c:if test="${staff.zanLevel==3 }">
                    <i></i><i></i><i></i>
                    </c:if>
                    <c:if test="${staff.zanLevel==4 }">
                    <i></i><i></i><i></i><i></i>
                    </c:if>
                    <c:if test="${staff.zanLevel==5 }">
                    <i></i><i></i><i></i><i></i><i></i>
                    </c:if>
                </ul>
            </div>
        </div>

        <div class="position_div">
            <div class="distance">
            	<i></i>
                <span class="position_div_span">${weUserInquiryQuote.distance }km</span>
            </div>
            <div style="clear: both">
               <span class="attention" ><span class="attention_num">${staff.followCount }</span> 人关注</span>
            </div>
        </div>
    </div>
</div>
<div class="price_state">
    <div class="price_state_title">
        <div class="price_state_content">报价说明</div>
    </div>
    <div class="price_detail">
        ${weUserInquiryQuote.desc }
    </div>
</div>
<div class="appointment_time_div" id="appoint_time" >
    <div class="col-2 appointment_time_name"  >
        预约时间
    </div>
    <div class="col-7 appointment_time_value">
        <label class="appointment_placeholder" >${empty date?'请选择预约时间':date }</label>
    </div>
    <div class="col-1 next">
    </div>
</div>

<div class="appointment_time_div">
    <div class="col-2 appointment_time_name"  >
        预约店铺
    </div>
    <div class="col-7 appointment_time_value">
    	<div class="appointment_placeholder">
    		${empty organName?'服务店铺':organName }
    	</div>
    </div>
    <!-- <div class="col-1 next"  > -->
    </div>
</div>

<div class="col-10 btn_bottom">
    <button id="appoint_save">预约</button>
</div>
<script src="${ctxPath}/module/user/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/js/enquiry.js"></script>
</body>
</html>