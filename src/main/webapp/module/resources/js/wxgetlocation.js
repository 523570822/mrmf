function getUserLocaltion(){
	wx.getLocation({
		    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
		    success: function (res) {
	        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
	        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
	        var speed = res.speed; // 速度，以米/每秒计
	        var accuracy = res.accuracy; // 位置精度
	        
	        var point = new BMap.Point(longitude,latitude);
			var gc = new BMap.Geocoder();
			var city='';
				gc.getLocation(point, function(rs) {
				    var addComp = rs.addressComponents;
				    var seleltCity = $("#seleltCity").val();
				    if(seleltCity==""){
				    	city=addComp.city;
						$('.position span').text(city);
				    }else{
				    	city = $('.position span').text();
				    }
					$.post(_ctxPath + "/w/organ/getUserLocation",{'latitude':latitude,'longitude':longitude,'userId':$("#userId").val(),'city':city,'type':"user"},
							  function(data){
								if(data=="true"){
									//alert("定位成功");
									//将遮罩层去掉
									$("#zzc").hide();
								}else{
                                    getUserLocaltion();
                                    $("#zzc").hide();
								}
					     },
					"json");
				 });
	            },
	            fail:function(res){
                    $("#zzc").hide();
                    // location.reload();
	            }
		     }
          );
	}
function getStaffLocaltion(){
	wx.getLocation({
	    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
	    success: function (res) {
        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
        var speed = res.speed; // 速度，以米/每秒计
        var accuracy = res.accuracy; // 位置精度
        var point = new BMap.Point(longitude,latitude);
		var gc = new BMap.Geocoder();
		var city='';
		gc.getLocation(point, function(rs) {
			var addComp = rs.addressComponents;
			var seleltCity = $("#seleltCity").val();
			if(seleltCity==""){
				city=addComp.city;
				$('.position span').text(city);
			}else{
				city = $('.position span').text();
			}

			$.post(_ctxPath + "/w/organ/getUserLocation",{'latitude':latitude,'longitude':longitude,'userId':$("#staffId").val(),'city':city,'type':"staff"},
		        		function(data){
					if(data=="true"){
						//alert("定位成功");
						//将遮罩层去掉
						$("#zzc").hide();
					}else{
						getStaffLocaltion();
                        $("#zzc").hide();
					}
		        	},"json");//这里返回的类型有：json,html,xml,text
		});

		},
		fail:function(res){
            $("#zzc").hide();
            // location.reload();
        }
    });
	}
function stafftimer(){
	var i = setInterval(function() {
		var vis=$("#zzc").is(":visible");
		if(vis){
		clearInterval(i);
            getStaffLocaltion();
            $("#zzc").hide();
		}else{
			clearInterval(i);
		}
		
}, 10000);
	
}
function timer(){
	var i = setInterval(function() {
		var vis=$("#zzc").is(":visible");
		if(vis){
		clearInterval(i);
			getUserLocaltion();
            $("#zzc").hide();
		}else{
			clearInterval(i);
		}
		
}, 10000);
	
}