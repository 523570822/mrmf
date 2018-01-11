<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
    <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
    <style type="text/css">
        .days{
            border: 1px solid slategray ;
            width: 40px; height: 40px; float: left;
            line-height: 40px;text-align: center;
        }
        .div_right{
            width: 85%; float: right;
            padding-left: 20px;font-size: 20px;
            margin-bottom: 3.3rem;
        }
        .data-day{display: none;}

        .data-month>div>.active{color: #fff; background: #ff6600;}

        .data-month-left{width: 15%;float: left; border: 1px solid sienna;height: 35rem;overflow-y: auto}
        .div_one>div>.active{color: #fff; background: #ff6600;}
        .day-color{background-color: #00b83f}
    </style>
</head>
<body class="bg_gray">
<div class="list_nav">
    <div>
        <h4 class="font-34">请选择您的租赁时间?</h4>
    </div>
</div>
<ul class="store_list" id="store_list"></ul>


<!--modal select list  -->
<div class="com_back_bg"></div>
<div class="data-month">
    <div class="div_right">
        <c:forEach items="${map}" var="MonthDays" varStatus="dayStatus">
            <div class="div_one">
               <c:forEach items="${MonthDays.value}" var="day">
                   <c:if test="${day.state==0}"><div class="days" style="visibility: hidden">${day.title}</div> </c:if>
                   <c:if test="${day.state==1}"><div class="days" style="background-color: #dddddd">${day.title}</div> </c:if>
                   <c:if test="${day.state!=1&&day.state!=0}">
                       <c:if test="${day.flag<organPositionSetting.num}"><div class="days" onclick="add('${day.time}',this)"  month="true" day="${day.time}">${day.title}</div> </c:if>
                       <c:if test="${day.flag>=organPositionSetting.num}"><div class="days" style="background-color: #dddddd">${day.title}</div></c:if>
                   </c:if>
               </c:forEach>
           </div>
        </c:forEach>
    </div>
</div>
<div class="col-10 common_func_menu"style="height: 4rem;padding-top: 0.4rem">
    <div>
        <button class="button_footer_2 buttonfooter_submit font-34" id="sub">
            <c:if test="${organPositionSetting.leaseType==0}">在线支付</c:if>
            <c:if test="${organPositionSetting.leaseType==1}">在线预约</c:if>
        </button>
    </div>
</div>
<form action="" method="post" id="fom">
<input type="hidden" id="organId" name="organId" value="${organPositionSetting.organId}"/>
<input type="hidden" id="staffId" name="staffId" value="${staffId}">
</form>
<script>
    $(document).ready(function () {
        $("#sub").click(function () {
            //alert($("#fom").serialize())asd
            $("#fom").attr("action",_ctxPath+"/w/staffMy/toRentPay").submit();
        })
    });
    var i =0;
    //onclick="add('{day.time}',this)"
    function add(obj,index) {
        if ($(index).hasClass("day-color")){
            $(index).removeClass("day-color");
            $("#"+obj).remove();
        }else {
            $(index).addClass("day-color");
            var times="<input name='timeList["+i+"]' type='hidden' id='"+obj+"' value='"+obj+"'/>";
            $("#fom").append(times);
            i=i+1;
        }
    }
   /* var newDay = null;
    var oldDay = null;
    $(".days[month = true]").click(function () {
        if($(".div_one .days").hasClass('day-color')){
            newDay=$(this).attr('day');
            if(newDay == oldDay){//得到当前时间，计算赋值
                //alert("清楚全部样式");
                $("div").removeClass('day-color');
            }else {
                //alert("截取收尾赋值");
                $(this).addClass('day-color');
            }
        }else {
            oldDay = $(this).attr('day');
            $(this).addClass('day-color');
            //alert("赋样式");
        }
    });*/
    var beforeDate = null;
    var afterDate = null;
    var oldIndex = null;
    var newIndex = null;
    var differIndex = null;
   /* $(".days[month = true]").click(function () {
        if($(".div_one .days").hasClass('day-color')){
            var afterIndex=$(this).index();
            newIndex = afterIndex;
            if(oldIndex != newIndex){//得到当前时间，计算赋值
                differIndex = oldIndex - newIndex;

            }else {
                $(this).removeClass('day-color');
            }
        }else {
            var beforeIndex = $(this).index();
            oldIndex = beforeIndex;
            $(this).addClass('day-color');
            beforeDate = $(this).attr('day');
            alert("赋样式");
        }
    });*/

  /*  var arr = [];

    $(".days").hasClass('day-color').each(
        function () {
        arr.push($(this).attr('day'))
    });*/



</script>
</body>
</html>