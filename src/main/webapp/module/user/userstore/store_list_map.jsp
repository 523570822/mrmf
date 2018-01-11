<%@ page language="java" import="java.util.*,java.lang.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
Double  lo=(Double)session.getAttribute("longitude");
Double	 la=(Double)session.getAttribute("latitude");
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
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/my_style.css"/>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=cyuWAntIDuQM6TXAUrPRuGvosDUFZoh5"></script>
</head>
<div class="list_nav">
    <div>
        <div class="list_left" style="background:#fff;">
            <span style="color:#22242a;">列表</span>
        </div>
        <div class="map_right" style="background:#22242a;">
            <span style="color:#fff;">地图</span>
        </div>
    </div>
<!--     <i></i> -->
</div>
<form action="" method="post" id="stroe_list_form">
	<input type="hidden" value="${cityId }" name="cityId" id="cityId"/>
    <input type="hidden" value="${city }" name="city" id="city"/>
    <input type="hidden" value="${userId }" name="userId" id="userId"/>
    <input type="hidden" value="${longitude}" name="longitude" id="longitude"/>
    <input type="hidden" value="${latitude }" name="latitude" id="latitude"/>
    <input type="hidden" value="" id="pages"/>
    <input type="hidden" value="" id="page"/>
    <input type="hidden" value="" id="followCount"/>
	<input type="hidden" value="" id="organ_type"/>
	<input type="hidden" value="distance" id="distance"/>
	<input type="hidden" value="" id="districtId"/>
	<input type="hidden" value="" id="district"/>
	<input type="hidden" value="" id="region"/>
	<input type="hidden" value="" id="regiongpsPoint"/>
	</form>
<ul class="sort_fun">
    <li>店铺类型&nbsp;&nbsp;<img src="${ctxPath}/module/resources/images/home/icon_aorrw_down.png"></li>
    <li>所在区域&nbsp;&nbsp;<img src="${ctxPath}/module/resources/images/home/icon_aorrw_down.png"></li>
    <li id="followCount_type" class="hot_sort">热度排序</li>
    <li id="distance_type" class="color_red distance_sort">距离排序</li>
</ul>
<div id="store_container" >
</div>
 <!--modal select list  -->
    <div class="com_back_bg"></div>
    <div class="select_nav">
        <div class="list_nav">
            <div>
                <div class="list_left" style="background:#fff;">
                    <span style="color:#22242a;">列表</span>
                </div>
                <div class="map_right" style="background:#22242a;">
                    <span style="color:#fff;">地图</span>
                </div>
            </div>
            <i>
            </i>
        </div>
       <ul class="sort_fun_m">
            <li>店铺类型&nbsp;&nbsp;<img id="img1" src="${ctxPath}/module/resources/images/home/icon_aorrw_down.png"></li>
            <li>所在区域&nbsp;&nbsp;<img src="${ctxPath}/module/resources/images/home/icon_aorrw_down.png"></li>
            <li class="hot_sort">热度排序</li>
            <li class="distance_sort">距离排序</li>
        </ul>
        <ul class="store_type">
            <li class="add_selected">所有类型<i></i></li>
            <c:forEach items="${codeList}" var="code" varStatus="status">
            <li>${code.name }<i></i></li>
            </c:forEach>
        </ul>
        <div class="area_m">
            <div id="wrapper_area">
                <div id="scroller1">
                    <ul class="area_state">
                        <li class="active">所有区域<input type="hidden" value="all" /><input type="hidden" value="所有区域" /></li>
                        <c:forEach items="${map}" var="item">
                        	<c:if test="${item.key=='district' }">
							<c:forEach items="${item.value}" var="d">
    							<li>${d.name }
    							<input type="hidden" value="${d._id }" />
    							<input type="hidden" value="${d.name }"/>
    							</li>
							</c:forEach>
							</c:if>
						</c:forEach>
                    </ul>
                </div>
            </div>
            <div id="wrapper2_area">
                <div id="scroller2" class="test">
                    <ul id="region_sel" >
                    	 <c:forEach items="${map}" var="item">
                        	<c:if test="${item.key=='region' }">
							<c:forEach items="${item.value}" var="d">
    							<li>${d.name }
    							<input type="hidden" value="${d._id }" />
    							<input type="hidden" value="${d.name }" />
    							<input type="hidden" value="${d.gpsPoint.longitude } |${d.gpsPoint.latitude }"/>
    							<i></i>
    							</li>
							</c:forEach>
							</c:if>
						</c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        </div>


<div class="col-10 common_func_menu">
    <ul>
        <li id="organ_list_back">
            <div>
                <img src="${ctxPath}/module/resources/images/icon_home_nor.png" />
                <p class="font-red">
                    首页
                </p>
            </div>
        </li>
        <li>
            <div>
                <img src="${ctxPath}/module/resources/images/icon_store_pre.png" />
                <p style="color:#f4370b">店铺</p>
            </div>
        </li>
        <li>
            <div id="user_enquiry">
                <img src="${ctxPath}/module/resources/images/icon_ask_nor.png" />
                <p>询价</p>
            </div>
        </li>
        <li>
            <div id="organ_my_back">
                <img src="${ctxPath}/module/resources/images/icon_my_nor.png" />
                <p>我的</p>
            </div>
        </li>
    </ul>
</div>

<script src="${ctxPath}/module/resources/js/iscroll.js"></script>
	<!--  <script src="${ctxPath}/module/user/userstore/js/store.js"></script>-->
	<script src="${ctxPath}/module/resources/js/userStore.js"></script>
	
    <script>
   
    	$(document).ready(function(){
    		 var lo='<%=lo%>';
    		 var la='<%=la%>';
    		 if(lo==""||la==""||la=="null"||la=="null"){
    		 	alert("定位失败，请重新登陆");
    		 }
    		var myScroll;
        	var myScroll2;
        	myScroll = new IScroll('#wrapper_area', { click:true, checkDOMChanges: true,vScrollbar:false,hScrollbar:false });
            myScroll2 = new IScroll('#wrapper2_area', { click:true,checkDOMChanges: true,vScrollbar:false,hScrollbar:false  });
         
    	
    	   $('#region_sel li').click(function() {
		       $(this).addClass('se_active').siblings().removeClass('se_active');
		       var id=$(this).children("input:eq(0)").val() ;
		       var name=$(this).children("input:eq(1)").val();
		       var gpsPoint=$(this).children("input:eq(2)").val();
		       $('.sort_fun li').eq(1).html(name + '&nbsp;&nbsp;<img src='+_ctxPath+'/module/resources/images/home/icon_aorrw_down.png>');
		       $("#region").val(name);
			   $("#regiongpsPoint").val(gpsPoint);		       
		       $('.select_nav').fadeOut('fast');
			   $('.com_back_bg ').fadeOut('fast');
			   organList(1);
   			});
   
    	 	$('.sort_fun li').eq(1).click(function () {
		       //$('#img1')[0].src=_ctxPath+'/module/resources/images/home/icon_aorrw_down.png';
			   $('#img1').attr("src",_ctxPath+'/module/resources/images/home/icon_aorrw_down.png');
		       $('.sort_fun_m li').eq(0).css('color','#22242a');
		       //$('#img2')[0].src=_ctxPath+'/module/resources/images/home/icon_aorrw_down_pre.png';
		       $('#img2').attr("src",_ctxPath+'/module/resources/images/home/icon_aorrw_down_pre.png');
		       $('.sort_fun_m li').eq(1).css('color','#f4370b');
		       
		       $('.com_back_bg ').fadeIn('fast');
		       $('.select_nav').fadeIn('fast');
		       $('.store_type').fadeOut('fast');
		       $('.area_m').fadeIn('fast');
		       myScroll.refresh();
		       myScroll.enable();
		       myScroll2.refresh();
		       myScroll2.enable();
		   });
	$('.area_state li').click(function(){
       $(this).addClass('active').siblings().removeClass('active');
       var id=$(this).children("input:eq(0)").val() ;
       var name=$(this).children("input:eq(1)").val();
       if(id!="all"){
    	   $("#district").val(name);
    	   var cityId=$("#cityId").val();
    	   $.post(_ctxPath + "/w/organ/districtList",{'cityId':cityId,'districtId':id},
    				  function(data){
    					var obj=eval(data);
    					$("#region_sel").html("");
    					for(var i=0;i<obj.region.length;i++){
    						var html='<li>'+obj.region[i].name+'<input type="hidden" value="'+obj.region[i]._id+'" />'+
							'<input type="hidden" value="'+obj.region[i].name+'" /><i></i></li>';
    						$("#region_sel").html($("#region_sel").html()+html);
    					}
    					
    					   $('#region_sel li').click(function() {
							       $(this).addClass('se_active').siblings().removeClass('se_active');
							       var id=$(this).children("input:eq(0)").val() ;
							       var name=$(this).children("input:eq(1)").val();
							       $('.sort_fun li').eq(1).html(name + '&nbsp;&nbsp;<img src='+_ctxPath+'/module/resources/images/home/icon_aorrw_down.png>');
							       $("#region").val(name);
							       $('.select_nav').fadeOut('fast');
								   $('.com_back_bg ').fadeOut('fast');
								   organList(1);
   						 });
		       				
    		},"json");//这里返回的类型有：json,html,xml,text	
       }else{
    	   $("#district").val("");
    	   $("#region").val("");
    	   $('.sort_fun li').eq(1).html(name + '&nbsp;&nbsp;<img src='+_ctxPath+'/module/resources/images/home/icon_aorrw_down.png>');
    	   $('.select_nav').fadeOut('fast');
    	   $('.com_back_bg ').fadeOut('fast');
    	   $('#region_sel li').removeClass('se_active');
    	   organList(1);
       }
       //select_nav
       
   });
    	});
        
    </script>
    <script src="${ctxPath}/module/user/userstore/js/store_map.js"></script>
</body>
</html>
