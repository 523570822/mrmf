<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>店铺列表</title>
<link href="${ctxPath}/module/staff/css/style_myStore.css" rel="stylesheet">
</head>
<body style="background: #f8f8f8;">
<form action="" method="post" id="stores_form">
	<input type="hidden" value="${staffId }" name="staffId" id="staff_id">
	<input type="hidden" value="${cityId }" name="cityId" id="cityId"/>
	<input type="hidden" value="${district}" name="district" id="district"/>
	<input type="hidden" value="${regionId}" name="regionId" id="region_id"/>
	<input type="hidden" value="${organName}" name="organName" id="organName">
	<input type="hidden" value="stores" name="status" id="status">
</form>
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="" id="page"/>

<input type="hidden" value="" id="region"/>

<div>
    <div class="nav">
	    <i class="back" id="back"></i>
	    <h4 class="font-34">店铺列表</h4>
	</div>
	 
	<div class="search font-32">
		<ul class="sort_fun">
			<li>
                <c:if test="${region==null}">所有区域</c:if>
                <c:if test="${region!=null}">${region.name}</c:if>&nbsp;&nbsp;<img id="img1" style="width:0.8rem;height:0.8rem;" src="${ctxPath}/module/resources/images/home/icon_aorrw_down.png">
			</li>
			<li>
				搜索
			</li>
		</ul>
	</div>
	<div class="store_content">
		<ul id="store_list">
		</ul>
	</div>
	</div>
	<div class="com_back_bg"></div>
	<div class="select_nav">
	<div>
    <div class="nav">
	    <i class="back" id="back2"></i>
	    <h4 class="font-34">店铺列表</h4>
	</div>
	<div class="search font-32" style="top:6.5rem;">
	<ul class="sort_fun_m">
			<li>
				所有区域&nbsp;&nbsp;<img id="img1" style="width:0.8rem;height:0.8rem;" src="${ctxPath}/module/resources/images/home/icon_aorrw_down.png">
			</li>
			<li>
				搜索
			</li>
		</ul>
		</div>
	</div>
	<div class="area_m">
         <div id="wrapper_area">
             <div id="scroller1">
                 <ul class="area_state">
                     <li class="active">所有区域
                     	<input type="hidden" value="all" />
                     	<input type="hidden" value="所有区域" />
                     </li>
                     <c:forEach items="${map}" var="item">
                     	<c:if test="${item.key=='district' }">
						<c:forEach items="${item.value}" var="district">
		 							<li>${district.name }
		 							<input type="hidden" value="${district._id }" />
		 							<input type="hidden" value="${district.name }"/>
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
					<c:forEach items="${item.value}" var="region">
  							<li>${region.name }
  							<input type="hidden" value="${region._id }" />
  							<input type="hidden" value="${region.name }" />
  							<i></i>
  							</li>
					</c:forEach>
					</c:if>
				</c:forEach>
                  </ul>
              </div>
          </div>
        </div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script src="${ctxPath}/module/staff/js/changejs.js"></script>
<script src="${ctxPath}/module/staff/js/page.js"></script>
<script src="${ctxPath}/module/resources/js/iscroll.js"></script>
<script type="text/javascript">
        $(document).ready(function(){
    		var myScroll;
        	var myScroll2;
        	myScroll = new IScroll('#wrapper_area', { click:true, checkDOMChanges: true,vScrollbar:false,hScrollbar:false });
            myScroll2 = new IScroll('#wrapper2_area', { click:true,checkDOMChanges: true,vScrollbar:false,hScrollbar:false  });
            
            $(".sort_fun li").eq(0).click(function () {
               
		       $('#img1').attr("src",_ctxPath+'/module/resources/images/home/icon_aorrw_down_pre.png');
		       $('.sort_fun_m li').eq(0).css('color','#f4370b');
		       
		       $('.com_back_bg ').fadeIn('fast');
		       $('.select_nav').fadeIn('fast');
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
		       var organName = $("#organName").val();
		       if(id!="all"){
		    	   $("#district").val(name);
		    	   var cityId=$("#cityId").val();
		    	   $.post(_ctxPath + "/w/staff/region",{'cityId':cityId,'districtId':id,"organName":organName},
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
									       $(".sort_fun li").eq(0).html(name + '&nbsp;&nbsp;<img src='+_ctxPath+'/module/resources/images/home/icon_aorrw_down.png>');
									       $("#region_id").val(id);
									       $('.select_nav').fadeOut('fast');
										   $('.com_back_bg ').fadeOut('fast');
                                    		organList(1);
		   						 });
				       				
		    		},"json");	
		       }else{
		    	   $("#district").val("");
		    	   $("#region").val("");
		    	   $("#region_id").val("");
		    	   $(".sort_fun li").eq(0).html(name + '&nbsp;&nbsp;<img src='+_ctxPath+'/module/resources/images/home/icon_aorrw_down.png>');
		    	   $('.select_nav').fadeOut('fast');
		    	   $('.com_back_bg ').fadeOut('fast');
		    	   organList(1);
		       }
		       
		   });
		   $(".sort_fun li").eq(1).click(function(){
		       $("#stores_form").attr("action",_ctxPath+"/w/staff/rexOrganName");
               $("#stores_form").submit();
//		       var staffId = $("#staff_id").val();
//               var cityId = $("#cityId").val();
//               var organName = $("#organName").val();
//               var districtId=$("#district_id").val();
//               var regionId=$("#region_id").val();
		       //window.location.href = _ctxPath+"/w/staff/rexOrganName?cityId="+cityId+"&staffId="+staffId+"&organName="+organName+"&regionId="+regionId+"&districtId="+districtId;
		   });
		   $(".com_back_bg ").click(function(){
		   		$('.select_nav').fadeOut('fast');
				$('.com_back_bg ').fadeOut('fast');
		   });
   		});
</script>
</body>
</html>