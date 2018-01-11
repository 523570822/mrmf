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
    <title>任性猫</title>
    <link rel="stylesheet" type="text/css" href="${ctxPath}/module/user/css/my_style.css"/>
    <style>
        .text-right{
            float: right;
            text-align: right;
            line-height: 4.46rem;
        }
    </style>
</head>
<body style="background-color: #f8f8f8">
<input type="hidden" id="user_id" value="${user._id }">
<input type="hidden" id="order_id" value="${order._id }">
<input type="hidden" id="organId" value="${order.organId }">
<input type="hidden" id="pay_type" value="">
<div class="common_nav orderpay_top ">
    <i onclick="window.history.go(-1)"></i>
    <h4 class="font-34">工位预定信息</h4>
</div>
<div class="gongWei">
    <div class="gongweiOne clear_both">
        <div>
            <span>预定店铺</span>
            <p id="dituContent">${positionOrder.organName}<img src="${ctxPath}/module/staff/images/img/icon_arrow18.png"  ></p>
        </div>
        <div style="height: 20px">
            <span class="clear_both col-3" style="color: #31262d">服务时间</span>
            <span class="col-5 time-style"> ${positionOrder.timeString}</span>
            <span class="time-span">共${positionOrder.totalDay}天</span>
        </div>
    </div>
    <c:if test="${positionOrder.leaseType==0}">
        <div class="gongweiTwo">
            <div>
                <span>单日租金</span>
                <p>￥ <fmt:formatNumber value="${positionOrder.leaseMoney}" pattern="##0.00#"/></p>
            </div>
            <div>
                <span>总金额</span>
                <p>￥ <fmt:formatNumber value="${positionOrder.totalMoney}" pattern="##0.00#"/></p>
                <input type="hidden" id="totalMoney" value="<fmt:formatNumber value="${positionOrder.totalMoney}" pattern="#0.00"/>"/>
            </div>
        </div>
        <div class="gongweiThree">
            <span>钱包支付</span>
            <p>钱包余额<span >￥ <fmt:formatNumber value="${staffMoney}" pattern="##0.00#"/> </span> <%--<img src="${ctxPath}/module/staff/images/img/icon_dorpbox_pre.png" id="img">--%></p>
            <input type="hidden" id="staffMoney" value="<fmt:formatNumber value="${staffMoney}" pattern="#0.00"/>" />
        </div>
       <%-- <ul class="pay-b">
            <li>
                <div>
                    <p class="margin-bottom1">总金额：</p>
                    <p class="margin-bottom2">钱包余额：</p>
                    <p style="font-size: 0.71rem">总计：</p>
                </div >
                <div>
                    <p class="margin-bottom1">￥ <fmt:formatNumber value="${positionOrder.totalMoney}" pattern="##0.00#"/> </p></span>
                    <input type="hidden" id="totalMoaaaney" value="<fmt:formatNumber value="${positionOrder.totalMoney}" pattern="#0.00"/>"/>
                    <p class="margin-bottom2"> ￥
                        <c:if test="${positionOrder.totalMoney>staffMoney}"><fmt:formatNumber value="${staffMoney}" pattern="##0.00#"/></c:if>
                        <c:if test="${positionOrder.totalMoney<=staffMoney}"><fmt:formatNumber value="${positionOrder.totalMoney}" pattern="##0.00#"/></c:if>
                    </p>
                    <p>￥<span style="font-size: 20px;">
                        <c:if test="${positionOrder.totalMoney>staffMoney}"><fmt:formatNumber value="${positionOrder.totalMoney-staffMoney}" pattern="##0.00#"/></c:if>
                        <c:if test="${positionOrder.totalMoney<=staffMoney}">0</c:if>
                        </span>
                    </p>
                </div>
            </li>
        </ul>--%>
        <ul class="pay-b">
            <li>
                <div class="col-10">
                    <span class="margin-bottom1">总金额：</span>
                    <span class="margin-bottom1">￥ <fmt:formatNumber value="${positionOrder.totalMoney}" pattern="##0.00#"/></span>
                    <input type="hidden" id="totalMoaaaney" value="<fmt:formatNumber value="${positionOrder.totalMoney}" pattern="#0.00"/>">
                </div>
                <div class="col-10">
                    <span class="margin-bottom1">钱包余额：</span>
                    <span class="margin-bottom1">￥
                        <c:if test="${positionOrder.totalMoney>staffMoney}"><fmt:formatNumber value="${staffMoney}" pattern="##0.00#"/></c:if>
                        <c:if test="${positionOrder.totalMoney<=staffMoney}"><fmt:formatNumber value="${positionOrder.totalMoney}" pattern="##0.00#"/></c:if>
                    </span>
                </div>
                <div class="col-10" style="font-size: 1.42rem">
                    <span>总计：</span>
                    <span>￥
                        <c:if test="${positionOrder.totalMoney>staffMoney}"><fmt:formatNumber value="${positionOrder.totalMoney-staffMoney}" pattern="##0.00#"/></c:if>
                        <c:if test="${positionOrder.totalMoney<=staffMoney}">0</c:if>
                    </span>
                </div>
            </li>
        </ul>
    </c:if>
    <c:if test="${positionOrder.leaseType==1}">
        <div class="gongweiOne" style="margin-top: initial;padding-top: 0">
            <div>
                <span>租赁模式</span>
                <p style="width: 90px;text-align: right">分账模式</p>
            </div>
        </div>
    </c:if>
    <div class="gongweiFive clear_both">提交确定,表示您已经同意<a href="${ctxPath}/module/staff/myPosition/potisionProtocol.jsp">《工位租赁协议》</a> </div>

</div>
    <div class="c"></div>
    <div class="commentheader_div ordersubmit_footer_div">
            <c:if test="${positionOrder.leaseType==0}">
                <div class="payMoney">
                    <span>实付金额</span>
                    <span id="showPayMoney" style="font-size: 18px;color: red">￥
                            <c:if test="${positionOrder.totalMoney>staffMoney}"><fmt:formatNumber value="${positionOrder.totalMoney-staffMoney}" pattern="##0.00#"/></c:if>
                            <c:if test="${positionOrder.totalMoney<=staffMoney}">0</c:if>
                    </span>
                    <input type="button" class="button_submit1" value="支付" id="pay" style="margin-right: 1rem;">
                </div>
            </c:if>
            <c:if test="${positionOrder.leaseType==1}"><input type="button" class="button_submit2" value="预约" id="pay"></c:if>
    </div>
    <form method="post" id="pay_from">
        <c:forEach items="${bean.timeList}" var="time" varStatus="status">
            <input type="hidden" name="timeList[${status.index}]" value="${time}">
        </c:forEach>
        <input type="hidden" name="organName" value="${positionOrder.organName}">
        <input type="hidden" name="staffId" value="${positionOrder.staffId}">
        <input type="hidden" name="staffName" value="${positionOrder.staffName}">
        <input id="test" type="hidden" name="timeString" value="${positionOrder.timeString}">
        <input type="hidden" name="leaseType" value="${positionOrder.leaseType}">
        <input type="hidden" name="leaseMoney" value="${positionOrder.leaseMoney}">
        <input type="hidden" name="totalDay" value="${positionOrder.totalDay}">
        <input type="hidden" name="totalMoney" value="${positionOrder.totalMoney}">
        <input type="hidden" name="organId" value="${bean.organId }">
    </form>
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
            'chooseWXPay',
            'openLocation'
        ]
    });

</script>
<script type="text/javascript">


    $(function() {
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

            $("#pay").click(function() {
                var lesstype =${positionOrder.leaseType};
                if( 0==  lesstype){
                    var tit = "";
                    var payType = "";
                    var totalMoney = parseFloat($("#totalMoney").val());//需要支付金额
                    var staffmoney = parseFloat($("#staffMoney").val());//钱包余额
                    if (totalMoney <= staffmoney ) {
                        tit = "喵喵：你的余额为" + staffmoney + ",本次支付将使用余额?";
                        payType = "1";//余额支付
                    } else {
                        tit = "喵喵：你的余额为" + staffmoney + ",还需支付" + accSub(totalMoney,staffmoney) + "?";
                        payType = "2";//差价微信支付
                    }
                    if (confirm(tit)) {
                        if ("2" == payType) {
                            $.post(_ctxPath + "/w/staffMy/staffPayPositionOrder", $("#pay_from").serialize(),
                                function (data) {
                                    if (data.status == "1") {
                                       wx.chooseWXPay({
                                           timestamp: data.params.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                                           nonceStr: data.params.nonceStr, // 支付签名随机串，不长于 32 位
                                           package: data.params.packageValue, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
                                           signType: data.params.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
                                           paySign: data.params.paySign, // 支付签名
                                           success: function(res) { //跳转成功界面
                                               window.location.href="${ctxPath}/w/staff/toResult?orderId="+data.message;
                                           },
                                           cancel: function (res) {//取消订单
                                               window.location.href="${ctxPath}/w/staff/cancelPositionOrder?orderId="+data.message;
                                           }

                                       })

                                    } else {
                                        layer.msg(data.message);
                                    }
                                },
                                "json");//这里返回的类型有：json,html,xml,text
                        } else {//不需要差额，走正常付款
                            $.post(_ctxPath + "/w/staffMy/staffPayPositionOrder", $("#pay_from").serialize(), function (data) {
                                if (data.status == "1") {
                                    var orderId = data.params._id;
                                    window.location.href="${ctxPath}/w/staff/toResult?orderId="+orderId;
                                } else {
                                    alert(data.message);
                                }
                            }, "json");
                        }
                    }
                }else {
                    $.post(_ctxPath + "/w/staffMy/staffPayPositionOrder", $("#pay_from").serialize(), function (data) {
                        if (data.status == "1") {
                            var orderId = data.params._id;
                            window.location.href="${ctxPath}/w/staff/toResult?orderId="+orderId;
                        } else {
                            alert(data.message);
                        }
                    }, "json");
                }

            })
        });
        wx.error(function () {
            alert('加载微信地图错误');
        });
    })
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

</script>
</body>
</html>