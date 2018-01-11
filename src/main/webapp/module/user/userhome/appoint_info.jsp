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
        <i onclick="appointInfo()"></i>
        <h4 class="font-34">预约信息</h4>
    </div>
    	<form id="appointForm" action="">
	    <input type="hidden" id="staffId" name="staffId" value ="${ staff._id }" />
	    <input type="hidden" id="caseId" name="caseId" value ="${ weStaffCase._id }" />
	    <input type="hidden" id="startPrice" name="startPrice" value ="${ weStaffCase._id }" />
	    <input id="type" name="type" type="hidden" value="${ type }" />
    
    	<input id="organ_id" name="organId" type="hidden" value="${ organId }" />
    	<input id="city_id" name="cityId" type="hidden" value="${ cityId }" />
    	<input id="city" name="city" type="hidden" value="${ city }" />
    	<input id="come" name="come" type="hidden" value="${come }"/>
    	<input id="distance" name="distance" type="hidden" value="${distance }" />
    	<input id="userId" name="userId" type="hidden" value="${user._id }"/>
    	</form>
    	<input id="appointCase" name="appointCase" type="hidden" value="${appointCase }"/>
    <div class="appoint_type_art">
        <div class="type_div">
            <div class="type_img">
                <img src="${ossImageHost}${weStaffCase.logo[0]}@!style400" />
            </div>
            <div>
                <div class="type_name">
                  <span>${ weStaffCase.title}</span>
                </div>
                <div class="type_price">
                    <span>&yen;&nbsp;&nbsp;<i id="orderPrice">${weStaffCase.price}</i></span>
                </div>
             </div>
        </div>
        <div class="technician">
            <div class="col-2">
                <span>
                                                       预约技师
                </span>
            </div>
            <div class="col-8">
                <img src="${ossImageHost}${staff.logo}@!avatar" />
                <span id="technician_name">${staff.name}</span>
            </div>
        </div>
    </div>

    <div class="time_store">
       <div onClick="toAppointTime()">
           <div class="col-2">
               <span>预约时间</span>
           </div>
           <div class="col-7">
               <label id="appointTime">
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

    <div class="time_store">
    	<!-- 这里是选择好店铺id   这里可以改  -->
        <input id="organId" type="hidden" value="${organId}" /> 
        <div onClick="goOrganMap()">
            <div class="col-2">
                <span>预约店铺</span>
            </div>
            <div class="col-7">
                <label id="storeAddress">
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

    <div class="common_btn">
        <button>
                                  提交
        </button>
    </div>
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
           		 <input type="hidden" id="orderId"/>
                <span>订单详情</span>
            </div>
        </div>
    </div>

    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/resources/js/common.js"></script>
    <script type="text/javascript">
    	function goOrganMap() {
    	    var organId =$.trim($("#organId").val());
    	    if(organId=="") {
    	    	return;
    	    }
    		window.location.href=_ctxPath+"/w/home/goOrganMap?organId="+organId;
    	};
    	function appointInfo(){
    	 $("#appointForm").attr("action",_ctxPath+'/w/home/toCaseDes').submit();
    	}
    	
    	
    	function toAppointTime(){
    	var appointCase=$("#appointCase").val();
    	if("ok"!=appointCase){
    		alert("技师正与店面签约中...");
    		return;
    	}
    	 $("#appointForm").attr("action",_ctxPath+'/w/home/toAppointTime').submit();
    	}
    	
    </script>
</body>
</html>