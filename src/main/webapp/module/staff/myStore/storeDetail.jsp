<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
<title>店铺列表</title>
<link href="${ctxPath}/module/staff/css/my_style.css" rel="stylesheet">
<link href="${ctxPath}/module/staff/css/swiper.min.css" rel="stylesheet">
<style>
        .swiper-container {
            width: 100%;
            height: 19.93rem;
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
            height:100%;
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
<body class="bg_gray" >
    <div class="common_nav">
        <i id="back"></i>
<input type="hidden" value="store" id="status">
<input type="hidden" value="1667920738524089172" id="city_id" name="cityId">
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="" id="page"/>
        <h4 class="font-34">${organ.name }</h4>
    </div>
   <%--  <div class="swiper-container">
        <div class="swiper-wrapper">
            <div class="swiper-slide"><img src="${ossImageHost}${organ.logo}"></div>
            
            <!-- <div class="swiper-slide"><img src="${ctxPath }/module/staff/images/example/2.jpg"></div>
            <div class="swiper-slide"><img src="${ctxPath }/module/staff/images/example/3.jpg"></div> -->
        </div>
        <!-- Add Pagination -->
        <div class="swiper-pagination"></div>
    </div> --%>
    <div class="store-img">
    <c:choose>
    	<c:when test="${empty organ.logo||organ.logo=='' }">
    		<img src="${ctxPath }/module/staff/images/img/nopicture.jpg">
    	</c:when>
    <c:otherwise>
    	<img src="${ossImageHost}${organ.logo}@!style1000">
    </c:otherwise>
    </c:choose>
   </div>
    <div class="store_des">
        <div>
            <div class="store_des_up">
               <h4>${organ.name }</h4>
               <c:if test="${organ.state==0 }"><span class="free">空闲</span></c:if> 
               <c:if test="${organ.state==1 }"><span class="normal">一般</span></c:if> 
               <c:if test="${organ.state==2 }"><span class="busy">繁忙</span></c:if> 
            </div>
            <div class="store_des_down">
                <div class="store_des_block">
                    <div class="block_l">店铺保证金</div>
                    <div class="block_r">&yen;${organ.deposit }</div>
                </div>
                <div class="store_right">
                    <i></i><span>${distance }m</span>
                </div>
            </div>
        </div>
    </div>
    <div class="hair_praise" style="border-bottom: 1px solid #dddddd">
        <div class="col-5 left_div">
            <img src="${ctxPath }/module/staff/images/img/icon_flower_90_pre.png">
            <span>
                赞
            </span>
            <i>
                (${organ.zanCount })
            </i>
        </div>
        <div class="middle_tripe"></div>
        <div class="col-5 right_div">
            <img src="${ctxPath }/module/staff/images/img/icon_egg_90_pre.png">
            <span>
                糗
            </span>
            <i>
                (${organ.qiuCount })
            </i>
        </div>
    </div>
    <div class="hair_comment" onclick="comment()">
        <img src="${ctxPath }/module/staff/images/img/icon_critic.png"/> <span>顾客评价</span> <i></i>
    </div>
    <div class="common_stripe"></div>
    <div class="store_phone">
        <div>
            <c:if test="${not empty organ.tel}">
                <a href="tel:${organ.tel }" class="tel_img"><img src="${ctxPath }/module/staff/images/img/icon_telephone.png">
                    <span class="phone_band"> 美丽热线</span>
                    <span class="phone_num">${organ.tel }</span>
                </a>
            </c:if>
            <c:if test="${not empty organ.phone}">
                <c:if test="${empty organ.tel}">
                    <a href="tel:${organ.phone }" class="tel_img"><img src="${ctxPath }/module/staff/images/img/icon_telephone.png">
                        <span class="phone_band"> 美丽热线</span>
                        <span class="phone_num">${organ.phone }</span>
                    </a>
                </c:if>
            </c:if>
            <!-- <i></i> -->
        </div>
    </div>
    <div class="store_phone">
        <div id="goOrganMap">
            <img src="${ctxPath }/module/staff/images/img/icon_store_address.png">
            <span class="phone_band">店铺地址</span>
            <span class="phone_num">${organ.address }</span>
            <i></i>
        </div>
    </div>
    <div class="store_phone">
        <div style="border-bottom:0;">
            <img src="${ctxPath }/module/staff/images/img/icon_commercialzone.png">
            <span class="phone_band">所在商圈</span>
            <span class="phone_num">${organ.region }</span>
        </div>
    </div>
    <div class="common_stripe">
    </div>
    <div class="store_prof">
        <ul class="store_fun" id="choose">
            <li ><span id="desc" class="click">店铺简介</span></li>
            <li ><span id="discound" >优惠信息</span> </li>
            <li ><span id="staff">店铺技师</span> </li>
        </ul>
        <div class="desc" id="desc_info">
        <c:if test="${empty organ.desc }">
        	<div></div>
           	<i>暂无相关数据</i>
        </c:if>
        <c:if test="${!empty organ.desc }">
           <p> ${organ.desc }</p>
        </c:if>
           	
        </div>
        <div class="discount" id="discount_info">
        <c:if test="${empty organ.discountInfo }">
           <div></div>
           <i>暂无相关数据</i>
        </c:if>
        <c:if test="${!empty organ.discountInfo }">
           <p> ${organ.discountInfo }</p>
        </c:if>
        </div>
        <div class="staff" id="staff_info">
        	<ul id="staff_list"></ul>
        </div>
    </div>
    <div class="btm_stripe">
    </div>
        <div class="bot_appoint">
        <div class="hair_collect <c:if test='${isCollect=="yes" }'>follow</c:if>" id="organ_follow">
            <img src="${ctxPath }/module/staff/images/img/icon_conllection_nor.png">
            <c:choose>
            	<c:when test="${isCollect=='no' }"><span id="collect">收藏</span></c:when>
            	<c:when test="${isCollect=='yes' }"><span id="collect">取消收藏</span></c:when>
            </c:choose>
            <i>(</i><i id="count">${empty organ.followCount?0:organ.followCount }</i><i>)</i>
        </div>
        <div class="middle_tripe hair_middle"></div>
        <div class="hair_appoint">
        <input type="hidden" value="${staffId }" id="staff_id">
        <input type="hidden" value="${organ._id }" id="organ_id">
        	<c:if test="${isJoin==0 }">
            	<button onclick="isjoin('${isJoin}')">申请加入</button>
        	</c:if>
        	<c:if test="${isJoin==1 }">
            	<button id="joinOrgan">已经加入</button>
        	</c:if>
        </div>
    </div>
    <div id="showDiv" class="hidebackground" onclick="hide()"></div>
    <form action="" id="terminate_form" method="post">
		<div id="content" class="showinput" >
		<input type="hidden" value="${staffId }" id="staff_id" name="staffId">
        <input type="hidden" value="${organ._id }" id="organ_id" name="organId">
        <input type="hidden" value="terminate" id="status" name="status">
			<div class="title">
				确定与<span style="color: #f4370b;">${organ.name }</span>解约
			</div>
			<div class="botton-left" onclick="hide()">取消</div>
			<div class="botton-right" id="terminate" onclick="hide()">
			<span style="color:#f4370b;">确定</span>
			</div>
		</div>
    </form> 
    <form action="" id="join_form" method="post">
		<div id="content_join" class="showinput" >
		<input type="hidden" value="${staffId }" name="staffId">
        <input type="hidden" value="${organ._id }" name="organId">
        <input type="hidden" value="join" id="status" name="status">
			<div class="title">
				确定加入<span style="color: #f4370b;">${organ.name }</span>
			</div>
			<div class="botton-left" onclick="hide()">取消</div>
			<div class="botton-right" id="join" onclick="hide()">
			<span style="color:#f4370b;">确定</span>
			</div>
		</div>
    </form>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/swiper.min.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script src="${ctxPath}/module/staff/js/changejs.js"></script>
<script src="${ctxPath}/module/staff/myStore/js/storeStaff_page.js"></script>
<script type="text/javascript">
	$('#joinOrgan').click(function() {
		alert("你已经加入了店铺，不能重复加入了！");
	})
</script>
   <!--  <script type="text/javascript">
        var mySwiper = new Swiper('.swiper-container', {
            autoplay: 2000,//可选选项，自动滑动
            pagination : '.swiper-pagination',
            paginationClickable: true,
            autoplayDisableOnInteraction: false,
            loop : true,
        })
    </script> -->
</body>
</html>