<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp" %>
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
	<input id="userId" type="hidden" value="${user._id}" />
    <div class="my_info">
        <div class="my_info_img"> <img src="" id="logoimg"/></div>
        <input type="hidden" id="headImg" value="${user.avatar}">
        <div class="my_info_name"><span>${user.nick}</span></div>
        <div class="my_info_phone">
        	<c:if test="${!empty user.phone}"><span>电话:</span>&nbsp;<span>${user.phone}</span></c:if></div>
    </div>
    <div class="function_nav">
        <div class="wallet">
            <div>
                <img src="${ctxPath}/module/resources/images/my/icon_wallet_pay.png" />
            </div>
            <div class="fun_div">
                <span>
                                             我的钱包
                </span>
            </div>
        </div>
        <div class="fun_stripe">
        </div>
        <div class="user" id="my_vip">
            <div>
                <img src="${ctxPath}/module/resources/images/my/icon_vipcard.png" />
            </div>
            <div class="fun_div">
                <span>
                   	 我的会员卡
                </span>
            </div>
        </div>
        <div class="fun_stripe"></div>
        <div class="collect" id="my_collect">
            <div>
                <img src="${ctxPath}/module/resources/images/my/icon_conllection.png" />
            </div>
            <div class="fun_div">
                <span>
                    	我的收藏
                </span>
            </div>
        </div>
    </div>
    <div class="gray_stripe">
    </div>
    <div class="my_detail_items">
        <div id="pInfo">
            <img src="${ctxPath}/module/resources/images/my/icon_bill_mine.png"><span>个人信息</span><i></i>
        </div>
        <div id="my_order">
            <img src="${ctxPath}/module/resources/images/my/icon_info_mine.png"><span>我的订单</span><i></i>
        </div>
        <div id="my_orCode">
            <img src="${ctxPath}/module/resources/images/my/icon_info_mine.png"><span>我的推荐二维码</span><i></i>
        </div>
        <div id="my_coupon">
            <img src="${ctxPath}/module/resources/images/my/icon_info_mine.png"><span>我的优惠券</span><i></i>
        </div>
        <div id="my_message">
            <div><img src="${ctxPath}/module/resources/images/my/icon_message_mine.png">
            <span>我的消息</span></div>
            <div class="message_num_wrap">
            	<c:if test="${messageCount > 0}">
	            	<div class="message_num"></div>
		      			<%-- <span>${messageCount}</span> --%>
	      		</c:if>
	      	</div>
	      	<div><i></i></div>
        </div>

       <%--  <div id="red_pack">
            <img src="${ctxPath}/module/resources/images/my/icon_red_mine.png"><span>抢红包</span><i></i>
        </div>   --%>
        
        <div id="my_compensate">
            <img src="${ctxPath}/module/resources/images/my/icon_apply_mine.png"><span>申请理赔</span><i></i>
        </div>
        <%-- <div>
            <img src="${ctxPath}/module/resources/images/my/icon_invitation_mine.png"><span>邀请好友</span><i></i>
        </div> --%>
        <div id="setSys">
            <img src="${ctxPath}/module/resources/images/my/icon_setting_mine.png"><span>设置</span><i></i>
        </div>
    </div>
    <div class="col-10 common_func_menu">
        <ul>
            <li id="common_func_menu_first" onclick="returnHome()" >
                <div>
                    <img src="${ctxPath}/module/resources/images/icon_home_nor.png" />
                    <p>
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
                <div>
                    <img src="${ctxPath}/module/resources/images/icon_my_pre.png" />
                    <p style="color: #ff0000">我的</p>
                </div>
            </li>
        </ul>
    </div>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/user/usermy/js/myinfo.js"></script>
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
                $("#logoimg").attr("src", "${ossImageHost}${user.avatar}");
            }
        });

    	function returnHome() {
    		window.location.href= _ctxPath + "/w/home/toHomePage";
    	}
    	$("#setSys").click(function() {
    		window.location.href= _ctxPath + "/w/userMy/toSetSys";
    	});
    </script>
</body>
</html>