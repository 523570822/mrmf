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
</head>
<body >
<input type="hidden" id="user_id" value="${userId }">
<input type="hidden" id="cardId" value="${cardId }">
<input type="hidden" id="organId" value="${organ._id }"> 
<input type="hidden" id="pay_type" value="">
<div class="bg_gray">
    <!-- common_nav start -->
	<div class="common_nav">
        <i onclick="goback()"></i>
        <h4 class="font-34">会员卡充值</h4>
    </div>
    <!-- common_nav end -->

    <!-- vipcard start -->
    <div class="vipcard">
		<img src="${ctxPath}/module/user/images/kazhengmian_2.jpg" alt="" />
		<span class="font-30">${organ.name}${usersort.name1 }</span>
    </div>
    <!-- vipcard end -->

    <!-- top-up_amout start -->
    <c:choose>
    	<c:when test="${cardType==1002 }">
    		<div class="top-up_amout font-30">
				充值金额 <input class="font-28" id="price" type="text" placeholder="最低充值1元" value="" />
				<p>最低金额 <span>1元</span></p>
		    </div>
    	</c:when>
    	<c:when test="${cardType==1003 }">
	    	<div class="top-up_amout font-30">
<!-- 					充值次数 <input class="font-28" id="price" type="text" placeholder="最低充值100元" value="" /> -->
					<p>充值金额 <span>${usersort.money }</span></p>
<!-- 					<p>单次金额 <span>${card.danci_money }</span></p> -->
					<input type="hidden" value="${usersort.money }" id="price"/>
			 </div>
    	</c:when>
    </c:choose>
    <input type="hidden" value="${cardType }" id="cardType"/>
    <!-- top-up_amout end -->

    <!-- <div class="payment_title">
       <span>选择支付方式</span>
    </div>

    <div class="my_pay_type">
    <ul id="pay_ul">
    	<li>
	        <div>
	            <img src="${ctxPath }/module/staff/images/img/icon_wechat_pay.png"><span>微信支付</span>
	            <div class="my_choiced">
	                <i ></i>
	            </div>
	        </div>
    	</li>
    	<li>
	        <div>
	            <img src="${ctxPath }/module/resources/images/my/icon_wallet_pay.png"><span>钱包支付</span>
	            <div class="my_choiced">
	                <i ></i>
	            </div>
	        </div>
    	</li>
    	<li>
	        <div>
	            <img src="${ctxPath }/module/user/images/icon_card_pay.png"><span>会员卡支付</span>
	            <div class="my_choiced">
	                <i ></i>
	            </div>
	        </div>
    	</li>
    </ul>
    </div>
     -->
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
			$("#pay").click(function() {//发送询价
			    //var minprice=100;
			    /*var cardType=$("#cardType").val();
			    var userId = $("#user_id").val();//用户ID
			    var cardId=$("#cardId").val();//会员卡ID
			    var price = $("#price").val();//充值金额
			    var sumPrice="";
			    if("1002"==cardType){
				var regex = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
			    if (!regex.test(price)) {
			       layer.msg("金额无效，请输入有效金额");
			       $("#price").val("");
			       $("#price").focus();
			       return;
				}else{
					sumPrice=price;
				}
			    }else if("1003"==cardType){
			    	var regex=/^[1-9]*[1-9][0-9]*/;
			    	/*if(!regex.test(price)){
				    	layer.msg("次数无效，请输入有效次数");
				    	$("#price").val("");
				        $("#price").focus();
				        return;
			    	}else{
			    	var danci_money=$("#danci_money").val();
			    	sumPrice=danci_money*price;
			    	}
			    }
				if(sumPrice<minprice){
					layer.msg("充值金额不能小于100元");
					return;
				}*/
				var cardType=$("#cardType").val();
			    var userId = $("#user_id").val();//用户ID
			    var cardId=$("#cardId").val();//会员卡ID
			    var price = $("#price").val();//充值金额
			    if("1002"==cardType){
			    var regex=/^[1-9]*[1-9][0-9]*/;
				if(!regex.test(price)){
				    	layer.msg("金额无效，请输入有效金额");
				    	$("#price").val("");
				        $("#price").focus();
				        return;
			    	}
			    }
				$.post(_ctxPath + "/w/pay/payOnline",{'userId':userId ,'cardId':cardId,'price':price,},
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
				                    window.location.href=_ctxPath + "/w/user/myCardDetail.do?userId="+userId+"&cardId="+cardId;
				                     //window.location.href=_ctxPath + "/w/pay/payOrderMessage.do?userId="+userId+"&orderId="+data.params.userPayOrder._id; 
							    },
							    cancel: function (res) {
							    	window.location.href = _ctxPath + "/w/pay/cancelPayOnline?payorderId=" + data.params.userPayOrder._id+"&userId="+userId+"&cardId="+cardId+"&cardType="+cardType;
                  				}
							    
							});
						});
					} else {
						layer.msg(data.message);
					}
				  },
				  "json");//这里返回的类型有：json,html,xml,text
   			});
		});
		function goback(){
		var userId=$("#user_id").val();
		var cardId=$("#cardId").val();
		window.location.href = _ctxPath + "/w/user/myCardDetail.do?userId="+userId+"&cardId="+cardId;
		}
    </script>
</body>
</html>