<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
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
    <title>繁忙时间设置</title>
    <link href="${ctxPath}/module/organ/store/css/style_pos.css" rel="stylesheet">
    <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
</head>
<body style="background: #f7f7f7">
<div class="register_nav">
    <i class="back" id="busytime_back"></i>
    <h4 class="font-34">繁忙时间设置</h4>
    <i class="time_setting" id="save_busytime">保存</i>
</div>
<input type="hidden" value="${organ._id }" id="organId"/>
<input type="hidden" value="" id="time_type"/>

<h4 class="setting_time_list" style="">设置繁忙时间段</h4>
<ul class="busytime_setting">
    <li>
        <div class="col-2">起始时间</div>
        <div class="col-6" id="busyTimeStart">${organ.busyTimeStart }</div>
        <div class="col-2"><i></i></div>
    </li>
    <li>
        <div class="col-2">结束时间</div>
        <div class="col-6" id="busyTimeEnd">${organ.busyTimeEnd }</div>
        <div class="col-2"><i></i></div>
    </li>
</ul>
</div>
 <!--modal select list  -->
    <div class="com_back_bg"></div>
    <div class="select_nav" style="position:absolute;top:10rem;">
        <div class="area_m">
            <div id="wrapper_area" style="width:50%;">
                <div id="scroller1">
                    <ul class="area_state">
                        <li>00</li>
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
                        <li>13</li>
                        <li>14</li>
                        <li>15</li>
                        <li>16</li>
                        <li>17</li>
                        <li>18</li>
                        <li>19</li>
                        <li>20</li>
                        <li>21</li>
                        <li>22</li>
                        <li>23</li>
                    </ul>
                </div>
            </div>
            <div id="wrapper2_area" style="width:50%;">
                <div id="scroller2" class="time">
                    <ul>
                           <li>00<i></i><input type="hidden" value="00" /></li>
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
	                       <li>32<i></i><input type="hidden" value="32" /></li>
	                       <li>33<i></i><input type="hidden" value="33" /></li>
	                       <li>34<i></i><input type="hidden" value="34" /></li>
	                       <li>35<i></i><input type="hidden" value="35" /></li>
	                       <li>36<i></i><input type="hidden" value="36" /></li>
	                       <li>27<i></i><input type="hidden" value="37" /></li>
	                       <li>38<i></i><input type="hidden" value="38" /></li>
	                       <li>39<i></i><input type="hidden" value="39" /></li>
	                       <li>40<i></i><input type="hidden" value="40" /></li>
	                       <li>41<i></i><input type="hidden" value="41" /></li>
	                       <li>42<i></i><input type="hidden" value="42" /></li>
	                       <li>43<i></i><input type="hidden" value="43" /></li>
	                       <li>44<i></i><input type="hidden" value="44" /></li>
	                       <li>45<i></i><input type="hidden" value="45" /></li>
	                       <li>46<i></i><input type="hidden" value="46" /></li>
	                       <li>47<i></i><input type="hidden" value="47" /></li>
	                       <li>48<i></i><input type="hidden" value="48" /></li>
	                       <li>49<i></i><input type="hidden" value="49" /></li>
	                       <li>50<i></i><input type="hidden" value="50" /></li>
	                       <li>51<i></i><input type="hidden" value="51" /></li>
	                       <li>52<i></i><input type="hidden" value="52" /></li>
	                       <li>53<i></i><input type="hidden" value="53" /></li>
	                       <li>54<i></i><input type="hidden" value="54" /></li>
	                       <li>55<i></i><input type="hidden" value="55" /></li>
	                       <li>56<i></i><input type="hidden" value="56" /></li>
	                       <li>57<i></i><input type="hidden" value="57" /></li>
	                       <li>58<i></i><input type="hidden" value="58" /></li>
	                       <li>59<i></i><input type="hidden" value="59" /></li>
                    </ul>
                </div>
            </div>
        </div>
   




<script src="${ctxPath}/module/resources/js/iscroll.js"></script>
<script src="${ctxPath}/module/organ/store/js/organ.js"></script>
<script type="text/javascript">
$(function(){
			var myScroll;
        	var myScroll2;
        	myScroll = new IScroll('#wrapper_area', { click:true, checkDOMChanges: true,vScrollbar:false,hScrollbar:false });
            myScroll2 = new IScroll('#wrapper2_area', { click:true,checkDOMChanges: true,vScrollbar:false,hScrollbar:false  });
         	myScroll.refresh();
		    myScroll.enable();
			myScroll2.refresh();
		    myScroll2.enable();
			$(".busytime_setting li").click(function(){
						   var index=$(this).index();
						   $(".area_state li").removeClass('active');
						   $(".time li").removeClass('se_active');
						   if(index==0){
						    $("#time_type").val("start");
						    
						   }else if(index==1){
						   $("#time_type").val("end");
						   }
						   $('.com_back_bg ').fadeIn('fast');
					       $('.select_nav').fadeIn('fast');
					       $('.area_m').fadeIn('fast');
					       myScroll.refresh();
					       myScroll.enable();
					       myScroll2.refresh();
					       myScroll2.enable();
			});
			$(".com_back_bg").click(function(){
			 			   $('.com_back_bg ').fadeOut('fast');
			 			   $('.select_nav').fadeOut('fast');
					       $('.area_m').fadeOut('fast');
					       myScroll.refresh();
					       myScroll.enable();
					       myScroll2.refresh();
					       myScroll2.enable();
			});
			$(".area_state li").click(function(){
			 $(this).addClass('active').siblings().removeClass('active');
			});
			$(".time li").click(function(){
					$(this).addClass('se_active').siblings().removeClass('se_active');
					var hour=$(".area_state li[class='active']").html();
					if(hour==""||typeof(hour)=="undefined"){
							alert("请选择小时");
							return;
					}
					var min=$(".time li[class='se_active']").children("input").val();
							//alert(hour+":"+min);
					var time_type=$("#time_type").val();
					if("start"==time_type){
						$("#busyTimeStart").html(hour+":"+min);
					}else if("end"==time_type){
						$("#busyTimeEnd").html(hour+":"+min);
					}
			 		$('.com_back_bg ').fadeOut('fast');
			 		$('.select_nav').fadeOut('fast');
					$('.area_m').fadeOut('fast');
					myScroll.refresh();
					myScroll.enable();
					myScroll2.refresh();
					myScroll2.enable();
			});
});
</script>
</body>
</html>