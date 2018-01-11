$(function(){
//	map.addEventListener("zoomend", function () {
//		alert(map.getZoom());
//		});
	organList(1);
	$(".hot_sort").click(function(){
		$('.select_nav').fadeOut('fast');
	    $('.com_back_bg ').fadeOut('fast');
	    $('.area_m').fadeOut('fast');
		$(this).addClass("color_red");
		$(".distance_sort").removeClass("color_red");
		$("#followCount_type").addClass("color_red");
		var flag=$(this).hasClass('color_red');
		if(flag){
			$("#followCount").val("followCount");
			$("#distance_type").removeClass("color_red");
			$("#distance").val("");
			organList(1);
		}else{
			$("#followCount").val("");
		}
	});
	$(".distance_sort").click(function(){
		$('.select_nav').fadeOut('fast');
	    $('.com_back_bg ').fadeOut('fast');
	    $('.area_m').fadeOut('fast');
	    $(this).addClass("color_red");
	    $(".hot_sort").removeClass("color_red");
	    $("#distance_type").addClass("color_red");
		var flag=$(this).hasClass('color_red');
		if(flag){
			$("#distance").val("distance");
			$("#followCount_type").removeClass("color_red");
			$("#followCount").val("");
			organList(1);
		}else{
			$("#distance").val("");
		}
	});
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	organList();
	    }
	});
	$(".map_right").click(function(){
		$("#stroe_list_form").attr("action",_ctxPath+"/w/organ/toOrganMapList").submit();
	});
	$(".list_left").click(function(){
		$("#stroe_list_form").attr("action",_ctxPath+"/w/organ/toOrganList").submit();
	});
	$("#user_enquiry").click(function(){
 		window.location.href = _ctxPath + "/w/user/enquiryPriceList.do?userId="+$("#userId").val();
	});
});
function organList(page){
	if(page==1){
		$("#store_list").html("");
	}else{
		 var nextPage=parseInt($("#page").val())+1;
		 page=nextPage;
		 var pages=parseInt($("#pages").val());
		 if(nextPage>pages){
			 return;
		 }
	}
	var type=$("#organ_type").val();
	var city=$("#city").val();
//	type 店铺类型
//	city 城市
//	district 区域
//	region 商圈
//	longitude 经度
//	latitude 纬度
//	maxDistance 搜索半径
//	followCount 关注（排序条件如果不为空就按关注的倒叙排序）
	var district=$("#district").val();
	var region=$("#region").val();
	var longitude=$("#longitude").val();
	var latitude=$("#latitude").val();
	var maxDistance="10";
	var followCount=$("#followCount").val();
	var distance=$("#distance").val();
	var pagesize=100;
	$.post(_ctxPath + "/w/organ/organList",{'type':type,'city':city,'district':district,'region':region,'longitude':longitude,'latitude':latitude,'distance':distance,'maxDistance':maxDistance,'followCount':followCount,'page':page,'pagesize':pagesize},
			  function(data){
		var obj=eval(data);
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		var map = new BMap.Map("store_container");
		//map.addControl(new BMap.NavigationControl());
		//map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);
		var city=$("#city").val();
		if(city!="北京市"){
			map.centerAndZoom(city);
		}
		var longitude=$("#longitude").val();
		var latitude=$("#latitude").val();
		//alert(longitude+"::"+latitude);
	   /* var lon=(Number(longitude)+0.01)+"";
	    var lat=(Number(latitude)+0.01)+"";
	    var pointRegion=new BMap.Point(lon,lat);//如果选择了商圈那么就以此为中心点（前提是商圈有具体坐标）
	    var  pointRegionIcon = new BMap.Icon(_ctxPath+"/module/resources/images/icon_didian_shouye.png", new BMap.Size(36,36));//商圈的图标和大小
	    var pointRegionOpts = {
	            width : 0,     // 信息窗口宽度
	            height: 0,     // 信息窗口高度
	        }; 
	    var pointRegionMarker = new BMap.Marker(pointRegion,{icon:pointRegionIcon});
	    map.centerAndZoom(pointRegion, 15);
        map.addOverlay(pointRegionMarker);*/
        var point1 = new BMap.Point(longitude, latitude);  //当前人所在的位置
        
        
	    var personIcon = new BMap.Icon(_ctxPath+"/module/resources/images/icon_location_map.png", new BMap.Size(36,36));
	    var opts = {
	            width : 0,     // 信息窗口宽度
	            height: 0,     // 信息窗口高度
	        };
	        var marker1 = new BMap.Marker(point1,{icon:personIcon});
	        
	        map.centerAndZoom(point1, 15);
	        map.addOverlay(marker1);
	        if(region!=""){
				var rep=$("#regiongpsPoint").val();
				var strs= new Array(); //定义一数组 
				strs=rep.split("|"); //字符分割
				//alert(strs[0]);
				//alert(strs[1]);
				map.panTo(new BMap.Point(strs[0], strs[1]));
			} 
	     // 添加定位控件
	        var geolocationControl = new BMap.GeolocationControl({
	            // 靠左上角位置
	            anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
	            // 启用显示定位
	            enableGeolocation: true,
	            showAddressBar:false
	        });
	        map.addControl(geolocationControl);
	for(var i=0;i<obj.data.length;i++){
		var sContent ="";
		  sContent = '<ul class="map_info"><li><div><div class="map_store_img">';
		  if(obj.data[i].logo==""||obj.data[i].logo=="null"){
			  sContent+= '<img src="'+_ctxPath+'/module/resources/images/nopicture.jpg">';
		  }else{
			  sContent+='<img src="'+_ossImageHost+obj.data[i].logo+'@!style400'+'">';
		  }
		  sContent +='</div><div class="map_rose_attent"><h4>'+obj.data[i].name+'</h4><ul class="map_flowers">';
		 for(j=0;j<obj.data[i].zanCount;j++){
			 sContent+='<li></li>';
		 }
		 sContent+='</ul><p><span>'+obj.data[i].followCount+'</span> 人关注</p></div><div class="map_dis_state"> <div>';
		 if(obj.data[i].state==0){
			 sContent+='<span class="state_busy">空闲</span>';
		    }else if(obj.data[i].state==1){
		    	sContent+='<span class="state_general">一般</span>';
		    }else if(obj.data[i].state==2){
		    	sContent+='<span class="state_idle">繁忙</span>';
		    }

		 sContent+='</div><div><div class="distance"><img src="'+_ctxPath+'/module/resources/images/home/icon_location_list.png" />'+
         '<span>'+obj.data[i].distance+obj.data[i].unit+'</span></div></div></div></div></li></ul>';
	  
		 
		 var opts = {
		            width : 0,     // 信息窗口宽度
		            height: 0,     // 信息窗口高度
		        };
		 var storeIcon = new BMap.Icon(_ctxPath+"/module/resources/images/icon_shop_map.png", new BMap.Size(50,50));
		 createMark = function(lng, lat, info_html) {  
	            var _marker = new BMap.Marker(new BMap.Point(lng, lat),{icon:storeIcon});  
	            _marker.addEventListener("click", function(e) {  
	                this.openInfoWindow(new BMap.InfoWindow(info_html));  
	            });  
	            return _marker;  
	        };  
	        var marker = createMark(obj.data[i].gpsPoint.longitude, obj.data[i].gpsPoint.latitude, sContent);
	        map.addOverlay(marker); 
		// 添加定位控件
	        var geolocationControl = new BMap.GeolocationControl({
	            // 靠左上角位置
	            anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
	            // 启用显示定位
	            enableGeolocation: false,
	            showAddressBar:false
	        });
	        /*geolocationControl.addEventListener("locationSuccess", function(e){
	        });
	        geolocationControl.addEventListener("locationError",function(e){
	            // 定位失败事件
	            //alert(e.message);
	        });*/
	        map.addControl(geolocationControl);
	}
	},"json");//这里返回的类型有：json,html,xml,text	
}
function toStoreDetail(thisli){
	var organId=$(thisli).children('input:eq(0)').val();
	var distance=$(thisli).children('input:eq(1)').val();
	var userId=$("#userId").val();
	var cityId=$("#cityId").val();
	var cityId=$("#city").val();
	//alert(distance);
	window.location.href = _ctxPath + "/w/organ/toOrganDetail.do?organId="+organId+"&userId="+userId+"&distance="+distance+"&cityId="+cityId+"&city="+city;
}
