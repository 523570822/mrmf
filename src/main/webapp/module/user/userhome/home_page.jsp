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
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/swiper.min.css"/>
    <style>
        .swiper-container {
            width: 100%;
            height:10.72rem;
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
        .btn {
        	width:2rem;
        	height:2rem;
        	background:url("${ctxPath}/module/user/images/icon_sao.png") no-repeat center;
        	background-size: 2rem;
        	position:absolute;
        	top: 0.6rem;
    		right: 1.2rem;
        }
    </style>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=UbLgidkKinZvi3ksTu1Q0hDAhWHM8Zi6"></script>
</head>
<body style="background: #f9f9f9">
<%--	<div id="zzc"  style="<c:if test="${userLocation==true }">display:none;</c:if> width: 100%;height: 120%; z-index:9999;position:absolute;top:0;background: url(${ctxPath}/module/resources/images/zzc.png);">
        <img src="${ctxPath}/module/resources/images/loading.gif;" style="display:block;position: relative;top: 24%;left: 30%;">
        <span style="display:block;position: relative;top: 24%;left: 40%;">获取地理位置...</span>
    </div>--%>

    <div class="index_nav">
        <div class="position">
            <img src="${ctxPath}/module/resources/images/enquiryprice/icon_didian_shouye.png" />
	        	<span>${city}</span>
	        	<input type="hidden" value="${seleltCity }" id="seleltCity">
        </div>
        <div class="index_font">
            <h4 class="font-34">
            </h4>
        </div>
        <div class="btn" id="scanQRCode1"></div>
    </div>
    <div class="swiper-container">
        <div class="swiper-wrapper">
        	<c:forEach var="weCarousel" items="${weCarousels}">
        		 <div class="swiper-slide"><img src="${ossImageHost}${weCarousel.img}@!banner"></div>
        	</c:forEach>
        </div>
        <!-- Add Pagination -->
        <div class="swiper-pagination"></div>
    </div>
    <div id="beautyHair" class="index_logo">
        <div class="logo_img col-4" >
            <img src="${ctxPath}/module/resources/images/home/icon_hair.png"/>
        </div>

        <div class="logo_font_div col-6">
            <p class="logo_font_big">精美发型</p>
            <p class="logo_font_small">海量精美发型设计</p>
        </div>
    </div>
    <div id="beautyStar" class="index_logo">
        <div class="logo_img col-4" >
            <img src="${ctxPath}/module/resources/images/home/iconfont-xingxing.png"/>
        </div>

        <div class="logo_font_div col-6">
            <p class="logo_font_big">美丽之星</p>
            <p class="logo_font_small">专业发型设计师</p>
        </div>
    </div>
    <div id="beautyStaff" class="index_logo">
        <div class="logo_img col-4" >
            <img src="${ctxPath}/module/resources/images/home/icon_star.png"/>
        </div>
        <div class="logo_font_div col-6">
            <p class="logo_font_big">明星技师</p>
            <p class="logo_font_small">美发设计明星气质</p>
        </div>
    </div>

    <div class="btm_item">
        <div>
            <img src="${ctxPath}/module/resources/images/home/icon_cosmetology_shouye.png">
            <h4>美容</h4>
        </div>
        <div>
            <img src="${ctxPath}/module/resources/images/home/icon_nail_shouye.png">
            <h4>美甲</h4>
        </div>
        <div>
            <img src="${ctxPath}/module/resources/images/home/icon_foot_shouye.png">
            <h4>养生</h4>
        </div>
    </div>
    <div class="btm_down">
    </div>
    <div class="col-10 common_func_menu">
        <ul>
            <li id="common_func_menu_first">
                <div>
                    <img src="${ctxPath}/module/resources/images/icon_home_pre.png" />
                    <p class="font-red">
                        	首页
                    </p>
                </div>
            </li>
            <li>
                <div id="user_organ">
                    <img src="${ctxPath}/module/resources/images/icon_store_nor.png" />
                    <p>店铺</p>
                </div>
            </li>
            <li>
                <div id="user_enquiry">
                    <img src="${ctxPath}/module/resources/images/icon_ask_nor.png" />
                    <p>询价</p>
                </div>
            </li>
            <li>
                <div id="user_my">
                    <img src="${ctxPath}/module/resources/images/icon_my_nor.png" />
                    <p>我的</p>
                </div>
            </li>
        </ul>
    </div>
    <form method="post" action="" id="home_page">
	    <input type="hidden" value="${user._id }" name="userId" id="userId"/>
	    <input type="hidden" value="${cityId }" id="cityId" name="cityId" />
    </form>

    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/resources/js/swiper.min.js"></script>
    <script src="${ctxPath}/module/user/userhome/js/user_my.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="${ctxPath}/module/resources/js/wxgetlocation.js"></script>

    <script type="text/javascript">
	wx.config({    
		debug: false,
		appId: '${sign.appid}', // 必填，公众号的唯一标识
		timestamp: '${sign.timestamp}', // 必填，生成签名的时间戳
		nonceStr: '${sign.nonceStr}', // 必填，生成签名的随机串
		signature: '${sign.signature}',// 必填，签名，见附录1    
		jsApiList: [    
		'checkJsApi',    
		'scanQRCode' ,
		'getLocation',
        'onMenuShareTimeline',// 朋友圈
        'onMenuShareAppMessage', //朋友
        'chooseImage',    //调用手机相册
        'onMenuShareQZone', //qq空间
        'onMenuShareQQ',//qq
        'onMenuShareWeibo',//腾讯微博
		]    
	});
	wx.error(function(res){
	    //alert(JSON.stringify(res));
       // alert(location.href.split('#')[0]);
//	 location.reload();
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
    });

</script>
<script type="text/javascript">
    // link: "http://test.wangtiansoft.cn/w/home/oauth2wx?inviaterId="+inviaterId,  这两个地址要做修改
    //imgUrl: "http://test.wangtiansoft.cn/module/resources/images/article.jpg", // 分享图标
    wx.ready(function () {
        var inviaterId='<%=session.getAttribute("userId")%>';
        $("#userId").val(userId);
        getUserLocaltion();
        document.querySelector('#scanQRCode1').onclick = function () {
            wx.scanQRCode({
              needResult: 0,
              desc: 'scanQRCode desc',
              success: function (res) {
              }
            });
        };

        wx.onMenuShareTimeline({
            title: '首页', // 分享标题
            link: "http://test.wangtiansoft.cn/mrmf/w/home/oauth2wx?inviaterId="+inviaterId,
            imgUrl: "http://test.wangtiansoft.cn/mrmf/module/resources/images/article.jpg", // 分享图标
            success: function () {
                grantCouponByShare(inviaterId);
            },
            cancel: function () {
                alert("_ctxPath"+_ctxPath);
                // 用户取消分享后执行的回调函数
            }
        });

        wx.onMenuShareAppMessage({
            title: '首页', // 分享标题
            link: "http://test.wangtiansoft.cn/mrmf/w/home/oauth2wx?inviaterId="+inviaterId, // 分享链接
            imgUrl: "http://test.wangtiansoft.cn/mrmf/module/resources/images/article.jpg", // 分享图标
            success: function () {
                grantCouponByShare(inviaterId);
            },
            cancel: function () {
                alert("_ctxPath"+_ctxPath);
                // 用户取消分享后执行的回调函数
            }
        });

        wx.onMenuShareQQ({
            title: '首页', // 分享标题
            link: "http://test.wangtiansoft.cn/mrmf/w/home/oauth2wx?inviaterId="+inviaterId, // 分享链接
            imgUrl: "http://test.wangtiansoft.cn/mrmf/module/resources/images/article.jpg", // 分享图标
            success: function () {
                grantCouponByShare(inviaterId);// 用户确认分享后执行的回调函数
            },
            cancel: function () {
                alert("_ctxPath"+_ctxPath); // 用户取消分享后执行的回调函数
            }
        });

        wx.onMenuShareWeibo({
            title: '首页', // 分享标题
            link: "http://test.wangtiansoft.cn/mrmf/w/home/oauth2wx?inviaterId="+inviaterId, // 分享链接
            imgUrl: "http://test.wangtiansoft.cn/mrmf/module/resources/images/article.jpg", // 分享图标
            success: function () {
                grantCouponByShare(inviaterId);// 用户确认分享后执行的回调函数
            },
            cancel: function () {
                alert("_ctxPath"+_ctxPath); // 用户取消分享后执行的回调函数
            }
        });

        wx.onMenuShareQZone({
            title: '首页', // 分享标题
            link: "http://test.wangtiansoft.cn/mrmf/w/home/oauth2wx?inviaterId="+inviaterId, // 分享链接
            imgUrl: "http://test.wangtiansoft.cn/mrmf/module/resources/images/article.jpg", // 分享图标
            success: function () {
                grantCouponByShare(inviaterId);// 用户确认分享后执行的回调函数
            },
            cancel: function () {
                alert("_ctxPath"+_ctxPath); // 用户取消分享后执行的回调函数
            }
        });

        function grantCouponByShare(inviaterId) {
            $.post("http://test.wangtiansoft.cn/w/home/grantCouponByShare",{"inviaterId":inviaterId},
               function(data){
                  if(data=="true"){
                       alert("您获得一张优惠券,请到个人中心查看");
                  }else{
                       alert("获得优惠券失败");
                  }
               }, "json");
        }


      /*  wx.getLocation({
		    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
		    success: function (res) {
		    alert(11111);
	        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
	        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
	        var speed = res.speed; // 速度，以米/每秒计
	        var accuracy = res.accuracy; // 位置精度

	        var point = new BMap.Point(longitude,latitude);
			var gc = new BMap.Geocoder();
			var city='';
				gc.getLocation(point, function(rs) {
				    var addComp = rs.addressComponents;
					city=addComp.city;
					$('.position span').text(city);
					$.post(_ctxPath + "/w/organ/getUserLocation",{'latitude':latitude,'longitude':longitude,'userId':$("#userId").val(),'city':city,'type':"user"},
							  function(data){
								if(data=="true"){

								}else{

								}
					     },
					"json");
				 });
	            },
	            error:function(res){
	            	alert(2222);
	            }
		     }
          );
          getUserLocaltion();*/
    });
        
</script>
    <script>
        var mySwiper = new Swiper('.swiper-container', {
            autoplay: 4000,//可选选项，自动滑动
            pagination : '.swiper-pagination',
            paginationClickable: true,
            autoplayDisableOnInteraction: false,
            loop : true,
        });
        /*  美丽发型  */
         $("#beautyHair").click(function() {
				window.location.href = _ctxPath + "/w/home/toBeautyHair";
		 });
		 $("#beautyStar").click(function() {
				window.location.href = _ctxPath + "/w/home/toBeautyStar?sort=1&userId="+$("#userId").val();
		 }); 
		 $("#beautyStaff").click(function() {
				window.location.href = _ctxPath + "/w/home/toBeautyStar?sort=2&userId="+$("#userId").val();
		 });
		 
		 $("#user_organ").click(function(){
		 		$("#home_page").attr("action",_ctxPath + "/w/organ/toOrganList.do").submit();
		 		//window.location.href = _ctxPath + "/w/organ/toOrganList.do?cityId="+$("#cityId").val()+"&userId="+$("#userId").val();
		 });
		 $("#user_enquiry").click(function(){
		 		window.location.href = _ctxPath + "/w/user/enquiryPriceList.do?userId="+$("#userId").val();
		 });
		 $(".position").click(function(){
		        var currentCity = $.trim($(".position span").text());
		 		window.location.href = _ctxPath + "/w/home/toSelectCity?city="+encodeURIComponent(encodeURIComponent(currentCity));
		 });
		 $(".btm_item").find("div").eq(0).click(function() {	
		 	  var homeType =  $(this).find("h4").text();
		 	  window.location.href = _ctxPath + "/w/home/toTypeProgram?homeType="+encodeURIComponent(encodeURIComponent(homeType));
		 });
		 
		 $(".btm_item").find("div").eq(1).click(function() {	
		 	  var homeType =  $(this).find("h4").text();
		 	  window.location.href = _ctxPath + "/w/home/toTypeProgram?homeType="+encodeURIComponent(encodeURIComponent(homeType));
		 });
		 
		 $(".btm_item").find("div").eq(2).click(function() {	
		 	  var homeType =  $(this).find("h4").text();
		 	  window.location.href = _ctxPath + "/w/home/toTypeProgram?homeType="+encodeURIComponent(encodeURIComponent(homeType));
		 });
    </script>
</body>
</html>