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
     <%-- <style type="text/css">
          html,body{margin:0;padding:0;}
          .iw_poi_title {color:#CC5522;font-size:14px;font-weight:bold;overflow:hidden;padding-right:13px;white-space:nowrap}
          .iw_poi_content {font:12px arial,sans-serif;overflow:visible;padding-top:4px;white-space:-moz-pre-wrap;word-wrap:break-word}
      </style>--%>
      <%--<script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.1&services=true"></script>--%>
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
</head>
<body class="bg_gray">
<nav class="nav">
    <div class="col-3">
        <i class="back" onclick="window.history.go(-1)"></i>
    </div>
    <div class="col-4">
        <h4 class="font-34">店铺详情</h4>
    </div>
    <div class="col-3"></div>
</nav>

<section class="store-banner">
    <div class="swiper-container swiper-banner">
        <div class="swiper-wrapper">
            <c:forEach items="${organ.images}" var="image">
                <div class="swiper-slide" style="background: url('${ossImageHost}${image}') no-repeat center/cover"></div>
            </c:forEach>
        </div>
        <div class="swiper-pagination"></div>
    </div>
</section>

<section class="store-title">
    <h3 style="font-size: 16px;color: #31262d">${organ.name}</h3>
    <p>${setting.num} 个工位可供租赁</p>
    <div class="store-price">
        <i>
            <c:if test="${setting.leaseType==0}">
                <img src="${ctxPath}/module/staff/images/img/label_zujin.png">
            </c:if>
            <c:if test="${setting.leaseType==1}">
                <img src="${ctxPath}/module/staff/images/img/label_fenzhang.png">
            </c:if>
        </i>
        <p class="clear_both">
        <c:if test="${setting.leaseType==0}">
            ￥<span>${setting.leaseMoney}</span>/天
        </c:if>
        </p>
    </div>
</section>

<section class="store-map clearfix">
    <div>
        <h3>${organ.address}</h3>
        <p>${organ.district} ${organ.region}</p>
        <p>距离 ${distance}</p>
    </div>
    <div id="dituContent" style="width: 17px;height: 17px;margin-top: 30px;"><img src="${ctxPath}/module/staff/images/img/icon_store_address.png" alt="" style="width: 16px"></div>
</section>

<section class="store-station">
    <h3>工位展示</h3>
    <div class="store-station-list">
        <div class="swiper-container swiper-scroll">
            <div class="swiper-wrapper">
                <div class="swiper-slide">
                    <c:forEach items="${setting.images}" var="image">
                        <img src="${ossImageHost}${image}" alt="">
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="store-fac">
    <h3>提供设施</h3>
    <div>${setting.describe}</div>
</section>

<section class="store-fixed" id="toDetail">
    <c:if test="${setting.leaseType==0}">
        <div class="col-5 store-fixed-s">租金每天 ￥ <span style="font-size: 16px">${setting.leaseMoney}</span></div>
        <div class="col-5"><button>预定租赁</button></div>
    </c:if>
    <c:if test="${setting.leaseType==1}">
        <div class="col-5" style="float: right"><button>预定租赁</button></div>
    </c:if>
</section>
<input type="hidden" value="${organId}" id="organId">
<input type="hidden" value="${staffId}" id="staffId">
<script src="${ctxPath}/module/resources/js/swiper.min.js"></script>
<%--<script src="${ctxPath}/module/staff/rent/js/baiduMap.js"></script>--%>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
   $(document).ready(function () {
       $("#toDetail button").click(function () {
           var organId = $("#organId").val();
           var staffId = $("#staffId").val();
           window.location.href = _ctxPath+'/w/staff/toDate?staffId='+staffId+'&organId='+organId;
       })
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
           document.querySelector('#dituContent').onclick = function () {
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
   })
    $(document).ready(function () {
        var mySwiper = new Swiper ('.swiper-banner', {
            direction: 'horizontal',
            loop: true,
            // 如果需要分页器
            pagination: '.swiper-pagination',
            autoplay: 5000
        });



        var swiper = new Swiper('.swiper-scroll', {
            direction: 'horizontal',
            slidesPerView: 'auto',
            mousewheelControl: true,
            freeMode: true
        });

    });


    $("#aaa").click(function () {
        var organId = $("#organId").val();
        var staffId = $("#staffId").val();
        window.location.href = _ctxPath + "/w/staff/toDate.do?organId="+organId+"&staffId="+staffId;
    })
</script>
</body>
</html>
