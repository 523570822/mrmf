<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML >
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
    <link rel="stylesheet" type="text/css" href="${ctxPath}/module/staff/css/style_mainPage.css"/>

    <script src="${ctxPath}/module/staff/myEarning/mobiscroll/js/jquery.1.7.2.min.js"></script>
    <script src="${ctxPath}/module/staff/myEarning/mobiscroll/js/mobiscroll_002.js" type="text/javascript"></script>
    <script src="${ctxPath}/module/staff/myEarning/mobiscroll/js/mobiscroll_004.js" type="text/javascript"></script>
    <link href="${ctxPath}/module/staff/myEarning/mobiscroll/css/mobiscroll_002.css" rel="stylesheet" type="text/css">
    <link href="${ctxPath}/module/staff/myEarning/mobiscroll/css/mobiscroll.css" rel="stylesheet" type="text/css">
    <script src="${ctxPath}/module/staff/myEarning/mobiscroll/js/mobiscroll.js" type="text/javascript"></script>
    <script src="${ctxPath}/module/staff/myEarning/mobiscroll/js/mobiscroll_003.js" type="text/javascript"></script>
    <script src="${ctxPath}/module/staff/myEarning/mobiscroll/js/mobiscroll_005.js" type="text/javascript"></script>
    <link href="${ctxPath}/module/staff/myEarning/mobiscroll/css/mobiscroll_003.css" rel="stylesheet" type="text/css">

</head>
<body class="myEarning" >
<input type="hidden" value="${staff._id}" name="staffId" id="staff_id">
<input type="hidden" value="${staff.name}" name="name" id="name">
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="1" id="type"/>
<input type="hidden" value="myEarn" id="status">

<div class="nav" style="position: fixed; z-index: 999; top: 0px">
    <input value="" style="opacity: 0;position: fixed; z-index: 999;" readonly="readonly" name="appDate" id="appDate" type="text">
    <i class="back" id="back"></i>
    <div class="title " style="border: none">
        我的租赁受益
        <span style="float: right;padding-bottom: 0"> <label for="appDate" ><img src="${ctxPath}/module/staff/images/img/icon_search_date.png" width="24px" ></label></span>
    </div>
</div>
<div class="money-div" style="margin-top: 3rem">
    <div>
        <p>总收益</p>
        <p><span style="font-size: 1.2rem">￥</span><fmt:formatNumber value ="${staff.totalIncome}" pattern="#0.00"/></p>
    </div>
    <div>
        <p id="goMyEarning">去钱包提现></p>
    </div>
</div>
<div id="earn_list">
</div>




<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/myEarning/js/myProfit.js"></script>
<script src="${ctxPath}/module/staff/js/mine.js"></script>
</body>
<script type="text/javascript">
    $(function () {
        var currYear = (new Date()).getFullYear();
        var opt={};
        opt.date = {preset : 'date'};
        opt.datetime = {preset : 'date'};
        opt.time = {preset : 'time'};
        opt.default = {
            theme: 'android-ics light', //皮肤样式
            display: 'bottom', //显示方式
            mode: 'scroller', //日期选择模式
            dateFormat: 'yyyy-mm-dd',
            lang: 'zh',
            dateOrder:'yymmdd',
            dayText: '日',
            monthText: '月',
            yearText: '年',
            startYear: currYear-1, //开始年份
            endYear: currYear + 10, //结束年份
            onSelect: function (valueText, inst) {//选择时事件（点击确定后），valueText 为选择的时间，
                //var selectedDate = valueText;
                $("#page").val("");
                $("#pages").val("");
                $("#earn_list").html("")
                earnList();
            }
        };

        $("#appDate").mobiscroll($.extend(opt['date'], opt['default']));

    });
</script>
</html>