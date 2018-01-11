<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<head>
<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <meta name="description" content="This is  a .."/>
<title>订单支付</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/module/user/css/my_style.css"/>
    <style>
        /* 清浮动 */
        .clear{zoom: 1;}
        .clear:after{content: "";overflow: hidden; clear: both; display: block;}

        .mark-bg{width: 100%; height: 100%; position: fixed; top: 0; left: 0; z-index: 99; background: rgba(0,0,0,.4);}
        .mark-bg .coupon-model{width: 90%; height: 20rem; background: #d7d7d7; position: absolute; left: 0; right: 0; top: 50%; margin:-10rem auto 0; overflow-y: auto;}
        .mark-bg .coupon-model ul li{list-style-type: none; padding: 0 1rem; height: 8rem; margin-bottom: 1rem; background: #fff;}
        .mark-bg .coupon-model ul li .coupon-top{border-bottom: 1px solid #ddd; height: 6rem;}
        .mark-bg .coupon-model ul li .coupon-top .coupon-name h4{font-size: 1.1rem; padding-top: 1.6rem;}
        .mark-bg .coupon-model ul li .coupon-top .coupon-name p{font-size: 0.85rem; padding-top: 0.8rem;}
        .mark-bg .coupon-model ul li .coupon-top .coupon-price{text-align: center;}
        .mark-bg .coupon-model ul li .coupon-top .coupon-price p{height: 6rem; line-height: 6rem;}
        .mark-bg .coupon-model ul li .coupon-top .coupon-price span{font-size: 40px;}
        .mark-bg .coupon-model ul li .coupon-bottom{height: 2rem; line-height: 2rem; font-size: 0.85rem; color: #666;}
    </style>
</head>
<body>
<input type="hidden" id="user_id" value="${user._id }">
<input type="hidden" id="order_id" value="${order._id }">
<input type="hidden" id="organId" value="${order.organId }">
<input type="hidden" id="staffId" value="${order.staffId }">
<input type="hidden" id="pay_type" value="">
<div class="bg_gray">
    <div class="common_nav orderpay_top ">
        <i onclick="window.history.go(-1)"></i>
        <h4 class="font-34">订单支付</h4>
    </div>
    <div class="orderpayheader_div">
        <div class="orderpayheader_left">
            <img src="${ossImageHost }${order.organLogo}@!style400" />
        </div>
        <div class="orderpayheader_right">
            <span>${order.organName }</span>
        </div>
    </div>
    <div class="orderpayheader_div amount_div">
        <div class="amount_left">
                              支付金额
        </div>
        <div class="amount_right">
           <input type="text"  name="amount" onkeyup="clearTxt()" id="price" placeholder="请输入支付金额" value="${price}" />
           <input type="hidden" value="${sysQuery }" id="sysQuery"/> 
        </div>
    </div>
    <div class="orderpayheader_div amount_div">
        <div class="amount_left">
            优惠券
        </div>
        <div class="amount_right" id="couponList" style="font-size: 9px">
            <span style="font-size:17px;" id="coupon">
                <c:if test="${mycoupon._id==null}">选择优惠券</c:if>
                <c:if test="${mycoupon.moneyType==0}">任性猫${mycoupon.moneyOrRatio}元优惠券</c:if>
                <c:if test="${mycoupon.moneyType==1}">任性猫${mycoupon.moneyOrRatio}折优惠券</c:if>
            </span>
            <input type="hidden" value="${mycoupon._id}" id="couponId"/>
            <input type="hidden" value="${mycoupon.moneyOrRatio}" id="moneyOrRatio"/>
            <input type="hidden" value="${mycoupon.minConsume}" id="minConsume"/>
        </div>
    </div>
	<div class="orderpayheader_div amount_div">
        <div class="amount_left">
                            钱包余额
        </div>
        <div class="amount_right">
        	<span><fmt:formatNumber value="${user.walletAmount}" pattern="##0.00#"/></span>
        	<input type="hidden" value="<fmt:formatNumber value="${user.walletAmount}" pattern="#0.00"/>" id="walletAmount"/> 
        </div>
    </div>

    <div class="orderpayheader_div amount_div amout_wrap">
        <div class="amount_left">
                          喵喵：
        </div>
        <div class="amount_right">
        	<em>订单支付会先扣除钱包余额，余额不足差价会使用微信支付</em>
        </div>
    </div>
    <div class="c"></div>
    <div class="commentheader_div ordersubmit_footer_div">
        <form class="footer_button">

            <input type="button" class="button_footer_2 buttonfooter_submit" value="支付" id="pay">
        </form>
    </div>
</div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/myOrder/js/my_order.js"></script>
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
				'chooseWXPay'
			]    
		});
	</script>
	<script type="text/javascript">
        
    	$(function(){
			$("#pay").click(function() {
			var salePrice = $("#price").val();
			var regex = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
			    if (!regex.test(salePrice)) {
			       layer.msg("金额无效，请输入有效金额");
			       $("#price").val("");
			       $("#price").focus();
			       return;
				}
			var walletAmount=$("#walletAmount").val();
            var moneyOrRatio = $("#moneyOrRatio").val();
            var minConsume=$("#minConsume").val();
            salePrice=parseFloat(salePrice);//返回浮点类型
            if(salePrice<=0){
                layer.msg("支付金额不能为零");
                return;
            }
            moneyOrRatio=parseFloat(moneyOrRatio)
            if(${mycoupon.moneyType==1}){//判断优惠券是打折券还是代金券
                salePrice = salePrice*moneyOrRatio;
                alert(salePrice);
            }else if(${mycoupon.moneyType==0}){
                salePrice = salePrice-moneyOrRatio;
                alert(salePrice);
            }
			walletAmount=parseFloat(walletAmount);
			var tit="";
			var payType="";
			if(salePrice<walletAmount){
				tit="喵喵：你的余额为"+walletAmount+",本次支付将使用余额?";
				payType="1";//余额支付moneyOrRatio
			}else{
				tit="喵喵：你的余额为"+walletAmount+",还需支付"+accSub(salePrice,walletAmount)+"?";
				payType="2";//差价微信支付
			}
			  if(confirm(tit)){//判断用户对弹出窗口的选择(true为确定)
				var userId = $("#user_id").val();
				var organId = $("#organId").val();
				var orderId=$("#order_id").val();
				var staffId = $("#staffId").val();
				var sysQuery=$("#sysQuery").val();
				var couponId = $("#couponId").val();
				var moneyOrRatio = $("#moneyOrRatio").val();
				var price = $("#price").val();
				if(sysQuery=="false"){
					 layer.msg("平台会员卡不存在");
					 return;
				}
				$("#pay").unbind("click");//清除支付按钮的事件
				if("2"==payType){//差额微信支付的方法
				$.post(_ctxPath + "/w/pay/balancePayOrderToStaff",{'userId':userId ,'organId':organId,'staffId':staffId,'price':price,'orderId':orderId,'couponId':couponId},
				  function(data){
					if(data.status == "1"){
						wx.ready(function(){
							wx.chooseWXPay({
							    timestamp: data.params.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
							    nonceStr: data.params.nonceStr, // 支付签名随机串，不长于 32 位
							    package: data.params.packageValue, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
							    signType: data.params.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
							    paySign: data.params.paySign, // 支付签名
							    success: function(res) {
				                    window.location.href=_ctxPath + "/w/userMy/myOrder.do?orderId="+orderId+"&price="+ price;
							    },
							    cancel: function (res) {
							    	window.location.href = _ctxPath + "/w/pay/cancelMyOrder?payorderId=" + data.params.userPayOrder._id+"&orderId="+orderId;
                  				}
							});
						});
					} else {
						layer.msg(data.message);
					}
				  },
				  "json");//这里返回的类型有：json,html,xml,text
				  }else{//不需要差额，走正常付款
					  $.post(_ctxPath + "/w/pay/balancePayOrderToStaff",{'userId':userId ,'organId':organId,'staffId':staffId,'price':price,'orderId':orderId,'couponId':couponId},
					  function(data){
						if(data.status == "1"){
							window.location.href=_ctxPath + "/w/userMy/myOrder.do?orderId="+orderId+"&price="+ price;
						}else{
							layer.msg(data.message);
						}
						},"json");
					}
				}
   			});
		});
		function accSub(arg1, arg2) {//支付金额和钱包余额的算法
		     var r1, r2, m, n;
		     try {//把钱通过。分隔，获得点前的长度
		         r1 = arg1.toString().split(".")[1].length;
		     }
		     catch (e) {
		         r1 = 0;
		     }
		     try {
		         r2 = arg2.toString().split(".")[1].length;
		     }
		     catch (e) {
		         r2 = 0;
		     }
		     m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
		     n = (r1 >= r2) ? r1 : r2;
		     return ((arg1 * m - arg2 * m) / m).toFixed(n);//四舍五入为指定小数位数的数字。
 		}

 		$("#couponList").click(function () {
            var price = $("#price").val();
            var regex = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
            if (!regex.test(price)) {
                layer.msg("金额无效，请输入有效金额");
                $("#price").val("");
                $("#price").focus();
                return;
            }else if (price!=0){
                var userId = $("#user_id").val();
                var orderId=$("#order_id").val();
                window.location.href=_ctxPath + "/w/module/coupon/queryMyCoupon.do?orderId="+orderId+"&price="+ price+"&userId="+userId;
            }else{
                layer.msg("金额不可为0");
            }
        });
    function clearTxt() {//改变支付金额，清空优惠券
        $("#coupon").html("选择优惠券")
        $("#couponId").val("");
        $("#moneyOrRatio").val("");
        $("#minConsume").val("");
    }
    </script>
</body>
</html>