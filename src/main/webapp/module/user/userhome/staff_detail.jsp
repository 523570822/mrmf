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
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/my_style.css"/>
</head>

<body class="bg_gray">
    <div class="common_nav">
        <i id="back"></i>
        <h4 class="font-34">${staff.name}</h4>
    </div>
    <div class="common_stripe">
    </div>
    <ul class="list"  style="border-bottom: 1px solid #dddddd;">
        <li>
            <div class="col-2 tx">
       			<img src= "${ossImageHost}${staff.logo}@!avatar" />
            </div>
            <div class="col-5 txt">
                <h4>${staff.name}</h4>
                <ul class="flowers">
              		<c:forEach begin="1" end="${ flowers}">
	                    <li></li>
	                </c:forEach>
                </ul>
                 <c:choose>
	            	<c:when test="${staff.workYears == 6 }">
	            	    <p><span>10年及以上</span>经验</p>
	            	</c:when>
	            	<c:otherwise>
	            		<p><span>${ staff.workYears }</span> 年经验</p>
	            	</c:otherwise>
                </c:choose>
            </div>
            <div class="col-3 location">
                <div><i></i>${ staff.distance } ${staff.unit }</div>
                <div class="price">&yen; <span>${staff.startPrice }</span> 起</div>
            </div>
        </li>
    </ul>
    <div class="hair_praise">
        <div class="col-5 left_div">
            <img src="${ctxPath}/module/resources/images/enquiryprice/icon_flower_90_pre.png">
            <span>
                                            赞
            </span>
            <i>
                (${ staff.zanCount })
            </i>
        </div>
        <div class="middle_tripe"></div>
        <div class="col-5 right_div">
            <img src="${ctxPath}/module/resources/images/home/icon_egg_90_pre.png">
            <span>
               	 糗
            </span>
            <i>
                (${ staff.qiuCount })
            </i>
        </div>
    </div>
    <div class="hair_comment">
        <img src="${ctxPath}/module/resources/images/home/icon_critic.png"/> <span>客户评价</span> <i></i>
    </div>
    <div class="tech_fun">
        <ul>
            <li><span>个人简介</span></li>
            <li id="staffCase">典型案例</li>
        </ul>
    </div>
    <div class="profile">
    <form id="staffDetailForm" action="" >
    	<input id="staffId" name="staffId" type="hidden" value="${ staff._id }" />
    	<input id="type" name="type" type="hidden" value="${ type }" />
    	<input id="organ_id" name="organId" type="hidden" value="${ organId }" />
    	<input id="city_id" name="cityId" type="hidden" value="${ cityId }" />
    	<input id="city" name="city" type="hidden" value="${ city }" />
    	<input id="come" name="come" type="hidden" value="${come }"/>
    	<input id="distance" name="distance" type="hidden" value="${distance }" />
    	<input id="userId" name="userId" type="hidden" value="${user._id }"/>
    </form>
     	<c:choose>
    		<c:when test="${ !empty staff.desc }">
    			<div class="profile_des">
		            <p>
		                ${ staff.desc }
		            </p>
        		</div>
   			 <div class="profile_img">
	        	<c:forEach items="${staff.descImages}" var="img"><!-- 修改描述的图片 -->
	            	<img src="${ossImageHost }${img}@!style400">
	            </c:forEach>
      			 </div> 
    		</c:when>
    		<c:when test="${ !empty staff.desc }">
    		</c:when>
    		<c:otherwise>
    			<div class="noData">
		        	<div></div>
		           	<i>暂无相关数据</i>	
       			 </div>
    		</c:otherwise>
    	</c:choose>
    </div>
    <div class="detail_height"></div>
    <div class="bot_appoint">
        <div class="hair_collect">
            <img src="${ctxPath}/module/resources/images/home/icon_conllection_nor.png">
            <span>
                ${collect}
            </span>
            <i>
                (${staff.followCount})
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
     <script src="${ctxPath}/module/user/userhome/js/des.js"></script>
     <script type="text/javascript">
     	$(".hair_appoint").click(function() {
     		var staffId = $.trim($("#staffId").val());
     		window.location.href= _ctxPath + "/w/home/toAppointDetail?staffId="+staffId;
     	});
     	
     	$("#btnAppoint").click(function(){
        	if($(this).hasClass("hair_no_appoint")) {
        		alert("喵，该技师没有可预约的时间！或者没有做过案例！不能预约哦！");
        	}
        });
        
     	$("#back").click(function() {
     		var come = $("#come").val();
     		var distance=$("#distance").val();
     		var userId =$("#userId").val();
	     	var type = $.trim($("#type").val());
	     	var organId = $.trim($("#organ_id").val());
	     	var cityId = $.trim($("#city_id").val());
	     	var city=$("#city").val();
	     	if("organDetail"==come){
	     	  window.location.href = _ctxPath + "/w/organ/toOrganDetail.do?organId="+organId+"&userId="+userId+"&distance="+distance+"&cityId="+cityId+"&city="+encodeURIComponent(encodeURIComponent(city));
	     	  return;
	     	}
	     	
	     	if (type !='' && type=="collect") {
	     		window.location.href=_ctxPath+"/w/staffMy/myCollection";
			}else if (type !="" && (type=="store" || type=="collectstore")) {
				window.location.href = _ctxPath+"/w/organ/toOrganDetail?organId="+organId+"&city="+encodeURIComponent(encodeURIComponent(city))+"&type="+type+"&cityId="+cityId;
			} else {
	     		window.location.href=_ctxPath+"/w/home/toBeautyStar?sort=1";
				}
	     	}
	     );
     	$(".hair_comment").click(function(){
     		var staffId = $.trim($("#staffId").val());
     		window.location.href= _ctxPath + "/w/home/toComment?staffId="+staffId;
     	});
     	 $(".hair_collect").click(function() {
        	var staffId = $("#staffId").val();
        	if($.trim($(".hair_collect span").text())=="收藏") {
	        	$.post(_ctxPath + "/w/home/collectStaff",{"staffId":staffId},
					  function(data){
					  $(".hair_collect span").text('取消收藏');
					  $(".hair_collect i").text('('+data+')');
	        	});
	        } else {
		        	$.post(_ctxPath + "/w/home/canColStaff",{"staffId":staffId},
						  function(data){
						 $(".hair_collect span").text('收藏');
						 $(".hair_collect i").text('('+data+')');
		       	});
	         }
        });
     </script>
</body>
</html>