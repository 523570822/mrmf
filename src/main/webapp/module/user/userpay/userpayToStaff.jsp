<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<!DOCTYPE html >
<html>
<head>
	<meta charset="UTF-8">
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
	<form action="" method="post" id="userpay_form">
		<div class="store_box">
			<div class="store_pic"><img src="${ossImageHost}${organ.logo}@!style400"/> </div>
			<div class="store_name">${organ.name}</div>
		</div>
		<div class="turn_money">
			<p>支付金额</p>
			<p><span>￥</span><input type="text" value="" id="price" name="price"/></p>
			<div class="turn_line"></div>
			<input type="hidden" id="userId" name="userId" value="${userId }" />
			<input type="hidden" id="organId" name="organId" value="${organ._id }" />
            <input type="hidden" id="staffId" name="staffId" value="${staff._id }" />
		</div>
		<div class="orderpayheader_div amount_div">
            <div class="amount_left">
                钱包余额
            </div>
            <div class="amount_right">
                <span>&yen;&nbsp;<i><fmt:formatNumber value ="${user.walletAmount}" pattern="#0.00"/></i></span>
                <input type="hidden" value="<fmt:formatNumber value ="${user.walletAmount}" pattern="#0.00"/>" id="walletAmount"/>
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
		<div class="turn_money_btn" id="turn_money_btn">
			支付
		</div>
    </form>
	<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="${ctxPath}/module/user/js/media_contrl.js"></script>
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
			$("#turn_money_btn").click(function() {//发送询价
				//$("#userpay_form").attr("action",_ctxPath+"/w/pay/saveOrder").submit();
				var m = $("#price").val();
				var regex = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
			    if (!regex.test(m)) {
			       layer.msg("金额无效，请输入有效金额");
			       $("#price").val("");
			       $("#price").focus();
			       return;
				}
				var userId = $("#userId").val();
				var organId = $("#organId").val();
				var staffId = $("#staffId").val();
				var price = $("#price").val();
				var walletAmount=$("#walletAmount").val();
				price=parseFloat(price);
				if(price<=0){
				 layer.msg("支付金额不能为零");
				 return;
				}
				walletAmount=parseFloat(walletAmount);
				var tit="";
				var payType="";
				if(price<=walletAmount){
					tit="喵喵：你的余额为"+walletAmount+",本次支付将使用余额?";
					payType="1";//余额支付
				}else{
					tit="喵喵：你的余额为"+walletAmount+",还需支付"+accSub(price,walletAmount)+"?";
					payType="2";//差价微信支付
				}
				if(confirm(tit)){
				$("#turn_money_btn").unbind("click");
				  if("2"==payType){
                      $.post(_ctxPath + "/w/pay/balancePayOrderToStaff",{'userId':userId ,'organId':organId,'price':price,'staffId':staffId},
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
                                            //window.location.href=_ctxPath + "/w/home/toHomePage.do?msg=支付成功";

                                            window.location.href = _ctxPath + "/w/userMy/ScanToEvaluate.do?payOrderId="+data.message;
                                        },
                                        cancel: function (res) {
                                            window.location.href = _ctxPath + "/w/pay/cancelOrder?orderId=" + data.message;
                                        }

                                    });
                                });
                              } else {
                                layer.msg(data.message);
                              }
                          },
                      "json");//这里返回的类型有：json,html,xml,text
				  }else{
				  	 $.post(_ctxPath + "/w/pay/balancePayOrderToStaff",{'userId':userId ,'organId':organId,'price':price,'staffId':staffId},
					  function(data){
						if(data.status == "1"){

							window.location.href = _ctxPath + "/w/userMy/ScanToEvaluate.do?payOrderId="+data.message;
						}else{
							layer.msg(data.message);
						}
						},"json");
				  }
				 }
   			});
		});
		function accSub(arg1, arg2) {
		     var r1, r2, m, n;
		     try {
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
		     return ((arg1 * m - arg2 * m) / m).toFixed(n);
 		}
    </script>
</body>