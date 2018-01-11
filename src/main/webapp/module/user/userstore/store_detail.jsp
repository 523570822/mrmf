<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=yes" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>

    <link rel="stylesheet" href="${ctxPath}/module/resources/css/swiper.min.css"/>
    <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
    <link href="${ctxPath}/module/organ/store/css/nodata.css" rel="stylesheet">

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
            width:auto;
            max-width:100%;
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
    <i id="organ_detail_back"></i>
    <h4 class="font-34">${organ.name }</h4>
</div>
<input type="hidden" value="${type }" id="type" />
<div class="swiper-container">
    <div class="swiper-wrapper">

        <%-- <c:choose> 
        	<c:when test="${ empty organ.logo}">
        		<img src="${ctxPath}/module/resources/images/nopicture.jpg">
        	</c:when>
        	<c:otherwise><img src="${ossImageHost}${organ.logo }@!style400"></c:otherwise>
         </c:choose> --%>
        <c:forEach items="${ organ.images }" var="img">
            <div class="swiper-slide">
                <img src="${ossImageHost}${img }@!style400">
            </div>
        </c:forEach>

    </div>
    <div class="swiper-pagination"></div>
</div>
<div class="store_des" style="  width:100%;  height:5.36rem;  background: #ffffff;  border-bottom:1px solid #dddddd;">
    <div>
        <div class="store_des_up">
            <h4>${organ.name }</h4>
            <c:choose>
                <c:when test="${organ.state==0}"><span class="tag1">空闲</span></c:when>
                <c:when test="${organ.state==1}"><span class="tag2">一般</span></c:when>
                <c:when test="${organ.state==2}"><span class="tag3">繁忙</span></c:when>
            </c:choose>

        </div>
        <div class="store_des_down">
            <div class="store_des_block">
                <div class="block_l">店铺保证金</div>
                <div class="block_r">&yen;${organ.deposit }</div>
            </div>
            <div class="store_right">
                <i></i><span>${distance}</span>
                <input type="hidden" id="distance" value="${distance}"/>
            </div>
        </div>
    </div>
</div>
<div class="hair_praise" style="border-bottom: 1px solid #dddddd">
    <div class="col-5 left_div">
        <img src="${ctxPath}/module/resources/images/enquiryprice/icon_flower_90_pre.png">
        <span>
             	   赞
            </span>
        <i>
            (${organ.zanCount })
        </i>
    </div>
    <div class="middle_tripe"></div>
    <div class="col-5 right_div">
        <img src="${ctxPath}/module/resources/images/home/icon_egg_90_pre.png">
        <span>
                                                  糗
            </span>
        <i>
            (${organ.qiuCount })
        </i>
    </div>
</div>
<div class="hair_comment" onclick="organRated()">
    <img src="${ctxPath}/module/resources/images/home/icon_critic.png"/> <span>客户评价</span> <i></i>
</div>
<div class="common_stripe"></div>
<div class="store_phone">
    <div>
        <c:if test="${not empty organ.tel}">
            <a href="tel:${organ.tel}" class="tel_img"><img src="${ctxPath}/module/resources/images/store/icon_telephone.png">
                <span class="phone_band"> 美丽热线</span>
                <span class="phone_num">${organ.tel}</span>
            </a>
        </c:if>
        <c:if test="${not empty organ.phone}">
            <c:if test="${empty organ.tel}">
                <a href="tel:${organ.phone }" class="tel_img"><img src="${ctxPath}/module/resources/images/store/icon_telephone.png">
                    <span class="phone_band"> 美丽热线</span>
                    <span class="phone_num">${organ.phone }</span>
                </a>
            </c:if>
        </c:if>
    </div>
</div>
<div class="store_phone" id="organ_addr">
    <div>
        <img src="${ctxPath}/module/resources/images/store/icon_store_address.png">
        <span class="phone_band">店铺地址</span>
        <span class="phone_num">${organ.address }</span>
        <!--         <i></i> -->
        <div class="col-1 time_store_location"></div>
    </div>
</div>
<%--<div class="store_phone" id="store_QRCode">--%>
<%--<div>--%>
<%--<img src="${ctxPath}/module/resources/images/store/icon_code.png">--%>
<%--<span class="phone_band">店铺支付二维码</span>--%>
<%--<i></i>--%>
<%--</div>--%>
<%--</div>--%>
<div class="store_phone">
    <c:choose>
    <c:when test="${smallsort==true }">
    <div>
        </c:when>
        <c:otherwise>
        <div style="border-bottom:0;">
            </c:otherwise>
            </c:choose>

            <img src="${ctxPath}/module/resources/images/store/icon_commercialzone.png">
            <span class="phone_band">所在商圈</span>
            <span class="phone_num">${organ.region }商圈</span>
        </div>
    </div>
    <c:if test="${smallsort==true }">
    <div class="store_phone"  id="smallsort">
        <div style="border-bottom:0;">
            <img src="${ctxPath}/module/resources/images/store/icon_price.png">
            <span class="phone_band">店铺价目表</span>
            <i></i>
        </div>
    </div>
    </c:if>
    <div class="common_stripe">
    </div>
    <div class="store_prof">
        <ul class="store_fun">
            <li ><label class="organ_info_active">店铺简介</label></li>
            <li><label>优惠信息</label></li>
            <li><label>店铺技师</label></li>
        </ul>
        <c:choose>
            <c:when test="${empty organ.desc }">
                <div id="context" class="organ_detail_context ">
                    <ul class='list nodateulsrote'>
                        <li style="background:#eee;border:0;" class="nodatali"><div class="notDate"></div><div class="notDateTitle">暂无相关数据</div></li>
                    </ul>
                </div>
            </c:when>
            <c:otherwise>
                <div id="context" class="organ_detail_context ">
                    <ul class='list'>
                        <p>${organ.desc }</p>
                    </ul>
                </div>
            </c:otherwise>
        </c:choose>
        <input type="hidden" value="${organ.desc }" id="desc" name="desc"/>
        <input type="hidden" value="${organ.discountInfo }" id="discountInfo" />
        <input type="hidden" value="" id="organ_datail_type"/>
        <input type="hidden" value="" id="page"/>
        <input type="hidden" value="" id="pages" />
        <form action="" method="post" id="organ_detail_form">
            <input type="hidden" value="${organId }" id="organId" name="organId"/>
            <input type="hidden" value="${user._id }" id="userId" name="userId"/>
            <input type="hidden" value="${cityId }" id="cityId" name="cityId"/>
            <input type="hidden" value="${city }" id="city" name="city"/>
            <input type="hidden" value="${longitude}" name="longitude" />
            <input type="hidden" value="${latitude }" name="latitude" />
            <input type="hidden" value="${search }"  id="search" name="search"/>
            <input type="hidden" value="${userAppID }" id="userAppID" name="userAppID" />
            <input type="hidden" value="${encode }"  id="encode" name="encode"/>
        </form>
    </div>
    <div class="btm_stripe">
    </div>
    <div class="bot_appoint">
        <div class="hair_collect1" style="width: 30%">
            <img src="${ctxPath}/module/resources/images/home/icon_conllection_nor.png">
            <span>
                        <c:choose>
                            <c:when test="${user1.favorTheOrganId==false}">
                                收藏
                            </c:when>
                            <c:otherwise>
                                取消收藏
                            </c:otherwise>
                        </c:choose>
		    </span>
            <i>
                (${empty organ.followCount?0:organ.followCount })
            </i>
            <input type="hidden" value="${empty organ.followCount?0:organ.followCount }" id="followCount" />
        </div>
        <div class="middle_tripe hair_middle"></div>
        <div class="hair_pay1" id="pay" >
            <button style="width: 8rem">支付</button>
        </div>
        <div class="hair_appoint1" >
            <button style="width: 8rem;margin-right: -0.5rem">预约</button>
        </div>
    </div>

    <script src="${ctxPath}/module/resources/js/swiper.min.js"></script>
    <script src="${ctxPath}/module/user/userstore/js/store_detail.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script>
        wx.config({
            debug: false,
            appId: '${sign.appid}', // 必填，公众号的唯一标识
            timestamp: '${sign.timestamp}', // 必填，生成签名的时间戳
            nonceStr: '${sign.nonceStr}', // 必填，生成签名的随机串
            signature: '${sign.signature}',// 必填，签名，见附录1
            jsApiList: [
                'checkJsApi',
                'openLocation'
            ]
        });

        wx.ready(function () {
            document.querySelector('#organ_addr').onclick = function () {
                var latitude1 = parseFloat('${organ.gpsPoint.latitude}');
                var longitude1 = parseFloat('${organ.gpsPoint.longitude}');
                wx.openLocation({
                    latitude:latitude1,  // 纬度，浮点数，范围为90 ~ -90
                    longitude:longitude1, // 经度，浮点数，范围为180 ~ -180。
                    name: '${organ.name }', // 位置名
                    address: '${organ.address}', // 地址详情说明
                    scale:14, // 地图缩放级别,整形值,范围从1~28。默认为最大
                    infoUrl:'' // 在查看位置界面底部显示的超链接,可点击跳转
                });
            }
        });
        wx.error(function () {
            alert('加载微信地图错误');
        });
        $(function(){
            var img=$(".swiper-slide").children("img");
            if(img.length!=1){
                var mySwiper = new Swiper('.swiper-container', {
                    autoplay: 2000,//可选选项，自动滑动
                    pagination : '.swiper-pagination',
                    paginationClickable: true,
                    autoplayDisableOnInteraction: false,
                    loop : true,
                });
            }
        });

    </script>
</body>
</html>
