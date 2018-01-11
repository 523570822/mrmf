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
<input type="hidden" id="id" value="${ user._id }"/>
<body class="bg_gray">
     <div class="common_nav">
        <i></i>
        <h4 class="font-34">转赠记录</h4>
    </div>
    <div class="common_stripe" >
    </div>
    <div class="donation_item">
        <div>
            <div>
                <div>
                    <img src="${ctxPath}/module/resources/images/home/item_head.png" />
                </div>
                <div class="person_info_div">
                    <p>
                                                       张杨正元
                    </p>
                    <i>
                        15910273859
                    </i>
                </div>
                <i>
                </i>
            </div>
        </div>
    </div>
    <%-- <div class="donation_item">
        <div>
            <div>
                <div>
                    <img src="${ctxPath}/module/resources/images/home/item_head.png" />
                </div>
                <div class="person_info_div">
                    <p>
                        张杨正元
                    </p>
                    <i>
                        15910273859
                    </i>
                </div>
            </div>
        </div>
    </div>
    <div class="donation_item">
        <div>
            <div>
                <div>
                    <img src="${ctxPath}/module/resources/images/home/item_head.png" />
                </div>
                <div class="person_info_div">
                    <p>
                        张杨正元
                    </p>
                    <i>
                        15910273859
                    </i>
                </div>
            </div>
        </div>
    </div> --%>
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
  </body>
</html>