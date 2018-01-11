<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> --%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <meta name="description" content="This is  a .."/>
<title>签到统计</title>
<link href="${ctxPath}/module/staff/css/style_mainPage.css" rel="stylesheet">
</head>
<body class="checkinList">
	<input type="hidden" value="statistics" id="status">
<form action="" method="post" id="order_form">
	<input type="hidden" value="${staffId }" id="staff_id" name="staffId">
	<input type="hidden" value="Desc" name="timeSort" id="time_sort">
	<input type="hidden" value="" id="pages"/>
	<input type="hidden" value="" id="page"/>
</form>
	<div class="nav">
	    <i class="back" id="back"></i>
	    <h4 class="font-34">店铺列表</h4>
	 </div>
	 <div class="ckl-title">
	 	<div class="ckl-title-left" id="order_by_time">时间排序</div>
	 	<div style="font-size: 1rem;" id="choose_time">选择日期</div>
	 </div>
	 <ul id="sign_list" class="result">
	
	 </ul>
<!--modal select list  -->
    <div class="com_back_bg"></div>
    <div class="select_nav" style="position:absolute;top:10rem;">
        <div class="area_m">
            <div id="wrapper_area" style="width:33%;">
                <div id="scroller1">
                    <ul class="year">
                        <li>2000</li>
                        <li>2001</li>
                        <li>2002</li>
                        <li>2003</li>
                        <li>2004</li>
                        <li>2005</li>
                        <li>2006</li>
                        <li>2007</li>
                        <li>2008</li>
                        <li>2009</li>
                        <li>2010</li>
                        <li>2011</li>
                        <li>2012</li>
                        <li>2013</li>
                        <li>2014</li>
                        <li>2015</li>
                        <li>2016</li>
                        <li>2017</li>
                        <li>2018</li>
                        <li>2019</li>
                        <li>2020</li>
                        <li>2021</li>
                        <li>2022</li>
                        <li>2023</li>
                    </ul>
                </div>
            </div>
            <div id="wrapper3_area" style="width:33%;">
                <div id="scroller3">
                    <ul class="month">
                        <li>01</li>
                        <li>02</li>
                        <li>03</li>
                        <li>04</li>
                        <li>05</li>
                        <li>06</li>
                        <li>07</li>
                        <li>08</li>
                        <li>09</li>
                        <li>10</li>
                        <li>11</li>
                        <li>12</li>
                    </ul>
                </div>
            </div>
            <div id="wrapper2_area" style="width:33%;">
                <div id="scroller2" class="day">
                    <ul>
                           <li>01<i></i><input type="hidden" value="01" /></li>
	                       <li>02<i></i><input type="hidden" value="02" /></li>
	                       <li>03<i></i><input type="hidden" value="03" /></li>
	                       <li>04<i></i><input type="hidden" value="04" /></li>
	                       <li>05<i></i><input type="hidden" value="05" /></li>
	                       <li>06<i></i><input type="hidden" value="06" /></li>
	                       <li>07<i></i><input type="hidden" value="07" /></li>
	                       <li>08<i></i><input type="hidden" value="08" /></li>
	                       <li>09<i></i><input type="hidden" value="09" /></li>
	                       <li>10<i></i><input type="hidden" value="10" /></li>
	                       <li>11<i></i><input type="hidden" value="11" /></li>
	                       <li>12<i></i><input type="hidden" value="12" /></li>
	                       <li>13<i></i><input type="hidden" value="13" /></li>
	                       <li>14<i></i><input type="hidden" value="14" /></li>
	                       <li>15<i></i><input type="hidden" value="15" /></li>
	                       <li>16<i></i><input type="hidden" value="16" /></li>
	                       <li>17<i></i><input type="hidden" value="17" /></li>
	                       <li>18<i></i><input type="hidden" value="18" /></li>
	                       <li>19<i></i><input type="hidden" value="19" /></li>
	                       <li>20<i></i><input type="hidden" value="20" /></li>
	                       <li>21<i></i><input type="hidden" value="21" /></li>
	                       <li>22<i></i><input type="hidden" value="22" /></li>
	                       <li>23<i></i><input type="hidden" value="23" /></li>
	                       <li>24<i></i><input type="hidden" value="24" /></li>
	                       <li>25<i></i><input type="hidden" value="25" /></li>
	                       <li>26<i></i><input type="hidden" value="26" /></li>
	                       <li>27<i></i><input type="hidden" value="27" /></li>
	                       <li>28<i></i><input type="hidden" value="28" /></li>
	                       <li>29<i></i><input type="hidden" value="29" /></li>
	                       <li>30<i></i><input type="hidden" value="30" /></li>
	                       <li>31<i></i><input type="hidden" value="31" /></li>
                    </ul>
                </div>
            </div>
        </div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script src="${ctxPath}/module/staff/signIn/js/page.js"></script>
<script src="${ctxPath}/module/resources/js/iscroll.js"></script>
<script>
$(function(){
			var myScroll;
        	var myScroll2;
        	var myScroll3;
        	myScroll = new IScroll('#wrapper_area', { click:true, checkDOMChanges: true,vScrollbar:false,hScrollbar:false });
        	myScroll3 = new IScroll('#wrapper3_area', { click:true, checkDOMChanges: true,vScrollbar:false,hScrollbar:false });
            myScroll2 = new IScroll('#wrapper2_area', { click:true,checkDOMChanges: true,vScrollbar:false,hScrollbar:false  });
         	myScroll.refresh();
		    myScroll.enable();
			myScroll2.refresh();
		    myScroll2.enable();
			myScroll3.refresh();
		    myScroll3.enable();
			$("#choose_time").click(function(){
						   $(".year li").removeClass('active');
						   $(".month li").removeClass('active');
						   $(".day li").removeClass('se_active');
						   
						   $('.com_back_bg ').fadeIn('fast');
					       $('.select_nav').fadeIn('fast');
					       $('.area_m').fadeIn('fast');
					       myScroll.refresh();
					       myScroll.enable();
					       myScroll2.refresh();
					       myScroll2.enable();
					       myScroll3.refresh();
					       myScroll3.enable();
			});
			$(".com_back_bg").click(function(){
			 			   $('.com_back_bg ').fadeOut('fast');
			 			   $('.select_nav').fadeOut('fast');
					       $('.area_m').fadeOut('fast');
					       myScroll.refresh();
					       myScroll.enable();
					       myScroll2.refresh();
					       myScroll2.enable();
					       myScroll3.refresh();
					       myScroll3.enable();
			});
			$(".year li").click(function(){
			 $(this).addClass('active').siblings().removeClass('active');
			});
			$(".month li").click(function(){
			 $(this).addClass('active').siblings().removeClass('active');
			});
			$(".day li").click(function(){
					var year=$(".year li[class='active']").html();
					var month=$(".month li[class='active']").html();
					if(year==""||typeof(year)=="undefined"){
							alert("请选择年份");
							return;
					}
					if(month==""||typeof(month)=="undefined"){
							alert("请选择月份");
							return;
					}
					$(this).addClass('se_active').siblings().removeClass('se_active');
					var day=$(".day li[class='se_active']").children("input").val();
							//alert(hour+":"+min);
					//var time_type=$("#time_type").val();
						$("#choose_time").html(year+"-"+month+"-"+day);
			 		$('.com_back_bg ').fadeOut('fast');
			 		$('.select_nav').fadeOut('fast');
					$('.area_m').fadeOut('fast');
					myScroll.refresh();
					myScroll.enable();
					myScroll2.refresh();
					myScroll2.enable();
					myScroll3.refresh();
					myScroll3.enable();
					$(".ckl-title div").removeClass("colorRed");
					$("#choose_time").addClass("colorRed");
					organList(1);
			});
			
});
</script>
</body>
</html>