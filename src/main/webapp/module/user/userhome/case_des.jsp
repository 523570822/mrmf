<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
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
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/my_style.css"/>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/swiper.min.css"/>
    <style>
        .swiper-container {
            width: 100%;
            height: 26.79rem;
        }
        .swiper-slide {
            text-align: center;
            font-size: 3rem;
            background: #fff;
            /* Center slide text vertically */
            display: -webkit-box;
            display: -ms-flexbox;
            display: -webkit-flex;
            display: flex;
            -webkit-box-pack: center;
            -ms-flex-pack: center;
            -webkit-justify-content: center;
            justify-content: center;
            -webkit-box-align: center;
            -ms-flex-align: center;
            -webkit-align-items: center;
            align-items: center;
        }
        .swiper-slide>img {
            height:auto;
            width:100%;
        }
        .swiper-pagination-bullet {
            width: 0.43rem;
            height:  0.43rem;
            text-align: center;
            line-height: 20px;
            /*font-size: 12px;*/
            color:#000;
            opacity: 1;
            background: rgba(0,0,0,0);
            border: 1px solid #22242a;
        }
        .swiper-pagination-bullet-active {
            background: #22242a;
        }
    </style>
</head>
<body class="bg_gray">
    <div class="common_nav">
        <i></i>
        <h4 class="font-34">${weStaffCase.title }</h4>
    </div>
    <input type="hidden" value="${homeType}" id="homeType" /> 
    <div class="swiper-container">
        <div class="swiper-wrapper">
        	<c:choose>
        		<c:when test="${weStaffCase.logo.size() == 1}">
        			<c:forEach items="${weStaffCase.logo}" var="logo" >
        				<div style="margin:0 auto;">
        					<img style="width:auto;height:26.79rem;" src="${ossImageHost}${logo}@!style1000">
        				</div>            			
                     </c:forEach>
        		</c:when>
        		<c:otherwise>
        			<c:forEach items="${weStaffCase.logo}" var="logo">
            			<div class="swiper-slide"><img src="${ossImageHost}${logo}@!style1000"></div>
                     </c:forEach>
        		</c:otherwise>
        	</c:choose>
        </div>
        <!-- Add Pagination -->
        <div class="swiper-pagination"></div>
    </div>
    <form id="caseDetailForm" action="">
    <input type="hidden" id="caseId" name="caseId" value="${weStaffCase._id}"  />
    
    	<input id="staff_id" name="staffId" type="hidden" value="${ staff._id }" />
    	<input id="type" name="type" type="hidden" value="${ type }" />
    	<input id="organ_id" name="organId" type="hidden" value="${ organId }" />
    	<input id="city_id" name="cityId" type="hidden" value="${ cityId }" />
    	<input id="city" name="city" type="hidden" value="${ city }" />
    	<input id="come" name="come" type="hidden" value="${come }"/>
    	<input id="distance" name="distance" type="hidden" value="${distance }" />
    	<input id="userId" name="userId" type="hidden" value="${user._id }"/>
    </form>
    <div class="haircut_info">
        <div>
            <div class="col-5">
                <h4>${weStaffCase.title }</h4>
                <div>
                    <h4>耗时</h4><span>${weStaffCase.consumeTime}分钟</span>
                </div>
            </div>
            <div class="col-5">
                <span class="hair_money">
                    &yen;&nbsp;&nbsp;<i>${weStaffCase.price}</i>
                </span>
            </div>
        </div>
    </div>
    <div class="hair_des">
        <div>
            <h4>发型说明</h4>
            <p>
              ${weStaffCase.desc}
            </p>
        </div>
    </div>
    <ul id="showStaff" class="list"  style="border-bottom: 1px solid #dddddd;">
        <li>
            <div class="col-2 tx">
                <img src="${ossImageHost}${staff.logo}@!avatar" />
            </div>
            <div class="col-6 txt">
                <h4>${staff.name}</h4>
                <ul class="flowers">
                     <c:forEach begin="1" end="${count}"> 
                       <li></li> 
                     </c:forEach> 
                </ul>
                <p><span>${staff.followCount}</span> 人关注</p>
            </div>
            <div class="col-2 location">
                <div><i></i></div>
                <div class="price">&yen; <span>${staff.startPrice}</span> 起</div>
            </div>
        </li>
    </ul>
    <div class="hair_praise" style="border-bottom: 1px solid #dddddd">
        <div class="col-5 left_div">
            <img src="${ctxPath}/module/resources/images/enquiryprice/icon_flower_90_pre.png">
            <span>
                	赞
            </span>
            <i>
                (${staff.zanCount})
            </i>
        </div>
        <div class="middle_tripe"></div>
        <div class="col-5 right_div">
            <img src="${ctxPath}/module/resources/images/home/icon_egg_90_pre.png">
            <span>
               	 糗
            </span>
            <i>
                (${staff.qiuCount})
            </i>
        </div>
    </div>
    <div class="hair_comment" style="border-bottom:1px #ddd solid">
         <span style="margin-left:1.2rem;">店铺名称:</span>  <span style="margin-left:1.2rem;">${organ.name }</span> 
    </div>
    <div class="hair_comment" style="border-bottom:1px #ddd solid" >
         <span style="margin-left:1.2rem;">店铺地址:</span>  <span style="margin-left:1.2rem;">${organ.address }</span> 
    </div>
    <div class="hair_comment" onclick="toComment()">
      <%--   <input id="s_id" type="hidden" value="${ staff._id}"/> --%>
        <img src="${ctxPath}/module/resources/images/home/icon_critic.png"/> <span>客户评价</span> <i></i>
    </div>
    <div class="btm_stripe">
    </div>
    <div class="bot_appoint">
        <div class="hair_collect">
            <img src="${ctxPath}/module/resources/images/home/icon_conllection_nor.png">
            <span>
               	 ${collect}
            </span>
            <i>
                (${weStaffCase.followCount})
            </i>
        </div>
        <div class="middle_tripe hair_middle"></div>
        <c:choose>
        	<c:when test="${isService}">
        		<div id="btnAppoint" class="hair_appoint"> 
                  <button>预约</button>
        		</div>
        	</c:when>
        	<c:otherwise>
        		<div id="btnAppoint" class="hair_no_appoint"> 
                  <button>预约</button>
        		</div>
        	</c:otherwise>
        </c:choose> 
    </div>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/resources/js/swiper.min.js"></script>
    <script>
       var mySwiper = new Swiper('.swiper-container', {
            autoplay: 2000,//可选选项，自动滑动
            pagination : '.swiper-pagination',
            paginationClickable: true,
            autoplayDisableOnInteraction: false,
            loop : true,
        });
        function toComment() {
             var staffId = $("#staff_id").val();
             window.location.href= _ctxPath+"/w/home/toComment?staffId="+staffId;
        }
        $("#showStaff").click(function() {
       	     var staffId = $("#staff_id").val();
        	window.location.href= _ctxPath+"/w/home/staffDetail?staffId="+staffId;
        });
        $(".hair_appoint").click(function() {
        	 //var caseId = $("#caseId").val();
        	 //window.location.href= _ctxPath+"/w/home/appointInfo?caseId="+caseId;
        	 $("#caseDetailForm").attr("action", _ctxPath+"/w/home/appointInfo").submit();
        });
        $(".hair_collect").click(function() {
        	var caseId = $("#caseId").val();
        	if($.trim($(".hair_collect span").text())=="收藏") {
	        	$.post(_ctxPath + "/w/home/saveCollect",{"caseId":caseId},
					  function(data){
					  $(".hair_collect span").text('取消收藏');
					  $(".hair_collect i").text('('+data+')');
	        	});
	        } else {
	        		var caseId = $("#caseId").val();
		        	$.post(_ctxPath + "/w/home/cancelCollect",{"caseId":caseId},
						  function(data){
						  $(".hair_collect span").text('收藏');
						  $(".hair_collect i").text('('+data+')');
		       	});
	         }
        });
        
        $("#btnAppoint").click(function(){
        	if($(this).hasClass("hair_no_appoint")) {
        		alert("亲，该技师没有可预约的时间！");
        	}
        });
        
        $(".common_nav i").click(function() {
        	var homeType = $.trim($("#homeType").val());
        	var type = $.trim($("#type").val());
        	
        	var come = $("#come").val();
	     	if("organDetail"==come){
	     	 // window.location.href = _ctxPath + "/w/organ/toOrganDetail.do?organId="+organId+"&userId="+userId+"&distance="+distance+"&cityId="+cityId+"&city="+encodeURIComponent(encodeURIComponent(city));
	     	  $("#caseDetailForm").attr("action",_ctxPath+'/w/home/staffDetail').submit();
	     	  return;
	     	}
        	
        	if (type !='' && type=="collect") {
				window.location.href= _ctxPath + "/w/staffMy/myCollection";
        	}else if (type !='' && type=="staff") {
        		var sId=$.trim($("#staff_id").val());
				window.location.href=_ctxPath + "/w/home/staffDetail?staffId="+sId;
			} else if(homeType != "精美发型") {
        		window.location.href= _ctxPath + "/w/home/toTypeProgram?homeType="+encodeURIComponent(encodeURIComponent(homeType));
        	} else {
        		window.location.href= _ctxPath + "/w/home/toBeautyHair";
        	}
        });
    </script>
</body>
</html>