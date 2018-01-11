<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<% 
    String datetime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()); 
%>
<!DOCTYPE html >
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
<title>发红包</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/module/staff/css/style_mainPage.css"/>
</head>
<body class="sendMoney">
	<div class="nav">
	    <i class="back" id="back"></i>
	    <div class="title">
	    	发红包
	    <i class="font-34" id="have_sent">已发红包</i>
	    </div>
	 </div>
<input type="hidden" id="total_income" value="${map['totalIncome']}">
<form action="" method="post" id="send_form">
<input type="hidden" value="${organId }" name="organId" id="organ_id">
	 <ul>
	 	<li id="to_select_scope">
	 		<div class="choose">
	 		<div class="col-2">选择范围</div>
	 		<div class="col-7">
	 		<input type="hidden" value="${scope }" name="scope" id="scope">
	 		<c:choose>
	 			<c:when test="${empty scope }">请选择客户范围</c:when>
	 			<c:when test="${scope=='1' }">所有服务过的会员</c:when>
	 			<c:when test="${scope=='2' }">所有关注客户</c:when>
	 		</c:choose>
	 		</div>
	 		<div class="col-1 next"></div>
	 		</div>
	 	</li>
	 	<li style="margin-bottom: 1.07rem;">
	 		<div class="choose">
	 		<div class="col-2">发送时间</div>
	 		<input type="hidden" name="sendTime" id="send_time">
	 		<div class="col-7" id="time"><%=datetime %></div>
	 		<div class="col-1 "></div>
	 		</div>
	 	</li>
	 	<li>
	 		<div class="choose">
	 		<div class="col-2">红包个数</div>
	 		<div class="col-7" style="width: 66%;">
	 			<input type="text" value="${count }" id="count" name="count" placeholder="填写个数">
	 		</div>
	 		<div class="col-1">个</div>
	 		</div>
	 	</li>
	 	<li style="margin-bottom: 1.07rem;">
	 		<div class="choose">
	 		<div class="col-2">总金额</div>
	 		<div class="col-7" style="width: 66%;">
	 			<input type="text" value="${money }" id="money" name="money" placeholder="填写金额" >
	 			<!-- onfocus="blackColor(this)" onblur="redColor(this)" -->
	 		</div>
	 		<div class="col-1">元</div>
	 		</div>
	 	</li>
	 	<li style="height: 8rem;">
	 		<textarea placeholder="恭喜发财" name="desc" id="desc">${desc}</textarea>
	 	</li>
	 </ul>
</form>
	 <div class="totalMoney"></div>
	 <div class="bottom" style="height: 2.5rem;">
		<button  class="button font-34" id="send_redPacket" style="height: 2.5rem;">发送红包</button>
	</div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${ctxPath}/module/staff/myhongbao/js/hongbao.js"></script>
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
		$("#send_redPacket").click(function(){//保存红包
			var totalIncome=$("#total_income").val();
			var count=$("#count").val();
			var money=$("#money").val();
			var scope=$("#scope").val();
			var desc=$.trim($("#desc").val());
			if(desc.length == 0) {
				desc ="恭喜发财";
			}
			if (scope=="") {
				alert('请选择发送范围');
				return false;
			}
			var flagc=isUnsigned(count);
			var flagm=isRightMoney(money);
			if (!flagc) {
				alert('请输入正确的个数');
				return false;
			}
			if (!flagm) {
				alert('请输入正确的金额');
				return false;
			}
			count = parseFloat(count);
			money = parseFloat(money);
			if(money/count<0.01) {
				alert('您发送的金额必须保证每个人0.01元');
				return;
			}
			if (Number(money)>Number(totalIncome)) {
				layer.confirm('您的余额不足,确定要用微信支付吗?', function(index){
					layer.close(index);
					pay(2,count,money,scope,desc);
				});
			}else {
				 pay(1,count,money,scope,desc);
			}
	});
});
function pay(str,count,money,scope,desc){
	if("2"==str){//余额不足 微信支付
		$.post(_ctxPath + "/w/pay/saveRedPacket",{'scope':scope ,'count':count,'money':money,'desc':desc},
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
			                    window.location.href=_ctxPath + "/w/staffMy/sendRedPacket";
						    },
						    cancel: function (res) {
								 layer.msg("已取消支付");
	                		},
						    error:function(res){
						    	 layer.msg("支付失败");
						    }
						});
					});
				} else {
					layer.msg(data.message);
				}
			  },"json");//这里返回的类型有：json,html,xml,text
		  }else{//余额支付
			$.post(_ctxPath + "/w/pay/saveRedPacket",{'scope':scope ,'count':count,'money':money,'desc':desc},
					function(data){
						if(data.status == "1"){
							window.location.href=_ctxPath + "/w/staffMy/sendRedPacket";
						}else{
							layer.msg("发送失败");
						}
					},
			    "json");
			}
        }
		function isRightMoney(a){//验证金额
			var reg=/^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
			return reg.test(a);
		}
		function isUnsigned(a){//验证正整数
		    var reg =/^[0-9]*[1-9][0-9]*$/;
		    return reg.test(a);
		}
</script>
</body>
</html>