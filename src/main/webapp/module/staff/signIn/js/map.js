$(function() {
	var longitude=$.trim($("#longitude").val());
	var latitude=$.trim($("#latitude").val());
	if(longitude == "0" ||longitude== "" ||  latitude == "0" || latitude =="") {
		alert("当前位置定位不准确 ，可能影响你的签到！请退出从新登录");
		return;
	}
	organList(1);
});
function organList(page) {
	/*if(page==1){
		$("#store_list").html("");
	}else{
		 var nextPage=parseInt($("#page").val())+1;
		 page=nextPage;
		 var pages=parseInt($("#pages").val());
		 if(nextPage>pages){
			 return;
		 }
	}*/
	var longitude=$("#longitude").val();
	var latitude=$("#latitude").val();
	$.post(_ctxPath + "/w/staff/organList",{'page':page},
			  function(data){
		var obj=data;
	//	$("#page").val(data.page);
		var map = new BMap.Map("store_container");
		window.map = map;
		 var point1 = new BMap.Point(longitude, latitude);  //当前人所在的位置
		    var personIcon = new BMap.Icon(_ctxPath+"/module/resources/images/icon_location_map.png", new BMap.Size(40,40));
		    var opts = {
		            width : 0,     // 信息窗口宽度
		            height: 0,     // 信息窗口高度
		        };
		    var marker1 = new BMap.Marker(point1,{icon:personIcon});
	     map.centerAndZoom(point1, 18);
	     map.addOverlay(marker1);
	     map.enableScrollWheelZoom(true);   
	 	 map.enableContinuousZoom();
	 	 map.enableDragging();
	 	 map.setCurrentCity("北京");
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
	    	$('#organ_id').val(obj.data[i]._id);
    		var sContent ="";
    		sContent = '<ul class="map_info"><li onclick="choiceOrgan(this)"><input type="hidden" value="'+obj.data[i]._id+'"><input type="hidden" value="'+obj.data[i].name+'"><div class="map_store_img"><img src="'+
    		(obj.data[i].logo==null ?_ctxPath+'/module/staff/images/img/nopicture.jpg':_ossImageHost+obj.data[i].logo+'@!style400')+'">'
            +'</div><div class="map_name ">'+obj.data[i].name+'</div><span class="map_address_name ">地址</span> <div class="map_address ">'+obj.data[i].address+'</div></li></ul>';
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
	        }
		},"json");
	}
function choiceOrgan(str) {
	var organId=$(str).children('input:eq(0)').val();
	var organName=$(str).children('input:eq(1)').val();
	$("#organ_id").val(organId);
}
