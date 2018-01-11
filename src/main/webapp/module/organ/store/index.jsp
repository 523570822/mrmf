<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
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
    <title>任性猫</title>
     <link href="${ctxPath}/module/resources/css/style_pos.css" rel="stylesheet">
  
</head>
<body style="background: #f7f7f7">
	<div class="top_nav">
    <div class="col-2"></div>
    <div class="col-6 font-34">${organ.name}</div>
    <!-- <div class="col-2" id="organ_index_status"> -->
        <div class="col-2" >
        <c:choose> 
        	<c:when test="${organ.state==0}"><span class="tag1">空闲</span></c:when>
        	<c:when test="${organ.state==1}"><span class="tag2">一般</span></c:when>
        	<c:when test="${organ.state==2}"><span class="tag3">繁忙</span></c:when>
         </c:choose>
        
    </div>
</div>
	<form id="organ_index_form" action=""  method="post">
    	<input type="hidden" value="${organ._id}" id="organ_id" name="organId" />
    	<input type="hidden" value="${organ.state }" id="organ_state" name="organState"/>
   </form>
<div class="btn_box">
    <ul>
        <li  onclick="wallet(this)">
            <a href="javascript:void(0);" >
                <i class="profit"></i>
                <p>我的钱包</p>
            </a>
        </li>
        <li onclick="paypsw(this)">
            <a href="javascript:void(0);" >
                <i class="setting"></i>
                <p>设置支付密码</p>
            </a>
        </li>
    </ul>
    <ul>
        <li  onclick="schedule(this)">
            <a href="javascript:void(0);" >
                <i class="date"></i>
                <p>日程管理</p>
            </a>
        </li>
        <li onclick="order(this)">
            <a href="javascript:void(0);" >
                <i class="order"></i>
                <p>订单管理</p>
            </a>
        </li>
    </ul>
    <ul>
        <li onclick="custom(this)">
            <a href="javascript:void(0);" >
                <i class="cutorm"></i>
                <p>店铺客户</p>
            </a>
        </li>
        <li onclick="staff(this)">
            <a href="javascript:void(0);" >
                <i class="skiller"></i>
                <p>店铺技师</p>
            </a>
        </li>
    </ul>
    <ul>
        <li onclick="earn(this)">
            <a href="javascript:void(0);" >
                <i class="profit"></i>
                <p>日店铺收益</p>
            </a>
        </li>
        <li onclick="message(this)">
            <a href="javascript:void(0);" >
              <c:choose>
              	<c:when test="${message==true }">
              		<i class="havenews"></i>
              	</c:when>
              	<c:otherwise>
              		<i class="news"></i>
              	</c:otherwise>
              </c:choose>
                <p>消息通知</p>
            </a>
        </li>
    </ul>
    <ul>
        <li onclick="rated(this)">
            <a href="javascript:void(0);" >
                <i class="judge"></i>
                <p>顾客评价</p>
            </a>
        </li>
        <li onclick="organ_set(this)">
            <a href="javascript:void(0);" >
                <i class="setting"></i>
                <p>设置</p>
            </a>
        </li>
    </ul>
    <ul>
        <c:if test="${organ.parentId==0}">
            <li onclick="son(this)">
                <a href="javascript:void(0);" >
                    <i class="profit"></i>
                    <p>子店铺收益</p>
                </a>
            </li>
        </c:if>
    </ul>

</div>
   <script src="${ctxPath}/module/organ/store/js/organ.js"></script>
  <%--  <script src="${ctxPath}/module/organ/store/js/point.js"></script> --%>
  <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
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
            var inviaterId='<%=session.getAttribute("organId")%>';
            $("#organId").val(organId);
            getUserLocaltion();

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
</body>
</html>
