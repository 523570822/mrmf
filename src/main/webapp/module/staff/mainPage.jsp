<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <meta name="description" content="This is  a .."/>
<title>首页</title>
<link href="${ctxPath}/module/staff/css/style_mainPage.css" rel="stylesheet">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=UbLgidkKinZvi3ksTu1Q0hDAhWHM8Zi6"></script>
</head>
<body link="#F0F0F0" onload="mess()">
<div id="zzc"  style="<c:if test="${staffLocation==true }">display:none;</c:if>width: 100%;height: 120%; z-index:9999;position:absolute;top:0;background: url(${ctxPath}/module/resources/images/zzc.png);">
		<img src="${ctxPath}/module/resources/images/loading.gif;" style="display:block;position: relative;top: 24%;left: 30%;">
		<span style="display:block;position: relative;top: 24%;left: 2%;">获取地理位置...</span>
</div>
<input type="hidden" value="${status.status}" id="status"/>
<input type="hidden" value="${status.message}" id="message"/>
<input type="hidden" value="${staff._id}" id="staffId">
		<div class="nav_pic1" align="center">
			<input type="hidden" id="headImg" value="${staff.logo}">
			<%--<img src="" name="file" id="logoimg"/>--%>
			<img src="" id="logoimg" class="image1"/>
		<c:if test="${empty staff.logo}">
			<img src="module/staff/images/img/icon_headportrait .png" class="image1">
		</c:if>
		</div>
		<ul  >
			<li>
				<div class="nav3">
					<p class="font-32">${staff.nick}</p>
				</div>
			</li>
		</ul>
		<div class="nav" style="height: 2.2rem;">
			<div class="center">
				<c:forEach begin="1" end="${staff.zanLevel}">
					<input class="image2" type="image" src="${ctxPath}/module/staff/images/img/flower.png" />
				</c:forEach>
			</div>
		</div>
		<form action="" id="staff_mainPage" method="post">
			<input type="hidden" value="${staff._id }" id="staff_id" name="staffId" />
			<input type="hidden" value="${cityId }" id="cityId" name="cityId" />
			<input type="hidden" value="${city}" id="city" name="city" />
		</form>
		<div class="mine_content clear">
			<div class="nav5">
					<div class="col-3" onclick="myDetail(this)">
						<div class="inner">
							<i class="personal"></i>
						</div>
						<h4 class="font-30" >个人信息</h4>
					</div>
					<div class="col-4" onclick="myRecommendation(this)">
						<div class="inner">
							<i class="recommend"></i>
						</div>
						<h4 class="font-30">自我推荐</h4>
					</div>
			</div>
			<div class="nav5">
					<div class="col-3" onclick="mystore(this)">
						<div class="inner">
							<i class="shop"></i>
						</div>
						<h4 class="font-30">店铺管理</h4>
					</div>
					<div class="col-4" id="example">
						<div class="inner">
							<i class="case"></i>
						</div>
						<h4 class="font-30">典型案例</h4>
					</div>
			</div>
				<div class="nav5">
					<div class="col-3" onclick="tariff(this)">
						<div class="inner">
							<i class="mainprice"></i>
						</div>
						<h4 class="font-30">价格管理</h4>
					</div>
					<div class="col-4" onclick="schedule(this)">
						<div class="inner">
							<i class="mainschedule"></i>
						</div>
						<h4 class="font-30">日程管理</h4>
					</div>
				</div>

				<div class="nav5" style="margin-bottom: 3.6rem;">
					<div class="col-3" onclick="rent(this)">
						<div class="inner">
							<i class="mainschedule"></i>
						</div>
						<h4 class="font-30">工位租赁</h4>
					</div>
					<div></div>
				</div>
			</div>

		<div class="bottom">
			<div class="col-2" >
				<i class="homepre"></i>
				<h4 style="color: #f4370b;">信息管理</h4>
			</div>
			<div class="col-2" id="ask_price">
				<i class="asknor"></i>
				<h4>询价报价</h4>
			</div>
			<div class="col-2" id="sign_in">
				<i class="signinnor"></i>
				<h4>签到</h4>
			</div>
			<div class="col-2" id="mine">
				<i class="mynor"></i>
				<h4>我的</h4>
			</div>
		</div>

<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${ctxPath}/module/resources/js/wxgetlocation.js"></script>
<script type="text/javascript">
	$(function () {
		var headImg = $("#headImg").val();
		if(headImg.indexOf("http")==0) {
			$("#logoimg").attr("src", headImg);
		}
//        if (headImg != '' && headImg != undefined && headImg != 'undefined' && headImg.indexOf("http")>0){
//
//        }
		if(headImg.indexOf("http") == -1){
			$("#logoimg").attr("src", "${ossImageHost}${staff.logo}@!avatar");
		}
	});
    //alert(location.href.split('#')[0]);
	wx.config({    
		debug: false,
		appId: '${sign.appid}', // 必填，公众号的唯一标识
		timestamp: '${sign.timestamp}', // 必填，生成签名的时间戳
		nonceStr: '${sign.nonceStr}', // 必填，生成签名的随机串
		signature: '${sign.signature}',// 必填，签名，见附录1    
		jsApiList: [    
		'checkJsApi',
		'getLocation',
		'onMenuShareAppMessage',
		'onMenuShareQZone',
		'onMenuShareWeibo',
		'onMenuShareTimeline',
		'onMenuShareQQ'
      ]    
});  
wx.error(function(res){
	//alert("error " + JSON.stringify(res));
//    location.reload();
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

}); 
wx.ready(function () {
		var inviaterId = "staff_"+$("#staffId").val();
	    getStaffLocaltion();
	    wx.onMenuShareAppMessage({
		    title: '${staff.nick}邀请你加入任性猫', // 分享标题
		    desc: '亲使用任性猫优惠多多哦!', // 分享描述
            link: "http://test.wangtiansoft.cn/mrmf/w/home/oauth2wx?inviaterId="+inviaterId, // 分享链接
            imgUrl: "http://test.wangtiansoft.cn/mrmf/module/resources/images/article.jpg", // 分享图标
		    type: '', // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () {
                grantCouponByShare(inviaterId);// 用户确认分享后执行的回调函数
		        console.info("分享成功了！");
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		        console.info("分享取消了！");
		    }
	     });
	     wx.onMenuShareTimeline({
		    title: '${staff.nick}邀请你加入任性猫', // 分享标题
             link: "http://test.wangtiansoft.cn/mrmf/w/home/oauth2wx?inviaterId="+inviaterId, // 分享链接
             imgUrl: "http://test.wangtiansoft.cn/mrmf/module/resources/images/article.jpg", // 分享图标
		    success: function () {
                grantCouponByShare(inviaterId); // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
		wx.onMenuShareQQ({
		    title: '${staff.nick}邀请你加入任性猫', // 分享标题
		    desc: '亲使用任性猫优惠多多哦!', // 分享描述
            link: "http://test.wangtiansoft.cn/mrmf/w/home/oauth2wx?inviaterId="+inviaterId, // 分享链接
            imgUrl: "http://test.wangtiansoft.cn/mrmf/module/resources/images/article.jpg", // 分享图标
		    success: function () {
                grantCouponByShare(inviaterId);// 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		       // 用户取消分享后执行的回调函数
		    }
         });
         wx.onMenuShareWeibo({
		    title: '${staff.nick}邀请你加入任性猫', // 分享标题
		    desc: '亲使用任性猫优惠多多哦!', // 分享描述
             link: "http://test.wangtiansoft.cn/mrmf/w/home/oauth2wx?inviaterId="+inviaterId, // 分享链接
             imgUrl: "http://test.wangtiansoft.cn/mrmf/module/resources/images/article.jpg", // 分享图标
		    success: function () {
                grantCouponByShare(inviaterId);// 用户确认分享后执行的回调函数
		    },
		    cancel: function () {
                grantCouponByShare(inviaterId);// 用户取消分享后执行的回调函数
		    }
       });
       wx.onMenuShareQZone({
		    title: '${staff.nick}邀请你加入任性猫', // 分享标题
		    desc: '亲使用任性猫优惠多多哦!', // 分享描述
           link: "http://test.wangtiansoft.cn/mrmf/w/home/oauth2wx?inviaterId="+inviaterId, // 分享链接
           imgUrl: "http://test.wangtiansoft.cn/mrmf/module/resources/images/article.jpg", // 分享图标
		    success: function () {
                grantCouponByShare(inviaterId);// 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		         // 用户取消分享后执行的回调函数
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
});
$(function(){

});
</script>
</body>
</html>