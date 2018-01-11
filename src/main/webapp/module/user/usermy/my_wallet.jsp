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
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/my_style.css"/>
</head>
<body class="bg_gray">
    <div class="common_nav">
        <i onclick="window.location.href='${ctxPath}/w/userMy/toUserMyHome'"></i>
        <h4 class="font-34">我的钱包</h4>
    </div>
    <input id="id" type="hidden" value="${user._id }"> 
    <input type="hidden" value="" id="pages"/>
    <input type="hidden" value="" id="page"/>
    <div class="wallet_rule">
        <div>
            <div class="col-5">
                <div class="wallet_balance">
                    <img src="${ctxPath}/module/resources/images/my/icon_wallet.png">
                    <span>
                        	钱包余额
                    </span>
                </div>
            </div>
            <div class="col-5">
                <div class="wallet_rule_div" onclick="window.location.href='${ctxPath}/w/userMy/toWalletRule'">
                    <img src="${ctxPath}/module/resources/images/my/icon_wenhao.png">
                    <span>
                      	 钱包使用规则
                    </span>
                </div>
            </div>
        </div>
        <div class="balance_money">
            <div>
                <span>&yen;&nbsp;<i><fmt:formatNumber value ="${user.walletAmount}" pattern="#0.00"/></i></span>
            </div>
        </div>
        </div>
        <div class="wallet_fun">
            <div class="cash_div">
                <div>
                    <img src="${ctxPath}/module/resources/images/my/icon_cash.png" />
                </div>
                <span>
              	   	  提现
                </span>
            </div>
            <div class="donate_div" >
                <div>
                    <img src="${ctxPath}/module/resources/images/my/icon_carryover.png" />
                </div>
                <span>
                	    转赠
                </span>
            </div>
        </div>
        <div class="my_expense" id="my_expense">
            <img src="${ctxPath}/module/resources/images/my/icon_consume.png"/>
            <span>我的消费记录</span>
            <i></i>
        </div>
        <%-- <div class="my_expense" id="donationRecord">
            <img src="${ctxPath}/module/resources/images/my/icon_consume.png"/>
            <span>我的转赠记录</span>
            <i></i>
        </div> --%>
        <div class="expense_list">
            <span>
                                        钱包明细记录
            </span>
        </div>
        <div id="expense_list">
        </div>
     <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
     <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
     <script src="${ctxPath}/module/user/usermy/js/wallet.js"></script>
</body>
</html>