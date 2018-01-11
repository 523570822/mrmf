$(function(){
	$.ajaxSetup({
		  async: false
	});
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
		//alert($(window).scrollTop()+"::"+($(document).height() - $(window).height()));
		//alert($(window).scrollTop());
		//alert($(document).height());
		//alert($(window).height());
		//alert($(document).height() - $(window).height());
		//alert($(window).scrollTop() == $(document).height() - $(window).height());
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	//alert(1111);
	    	organList();
	    }
	});
	$(".map_right").click(function(){
		$("#stroe_list_form").attr("action",_ctxPath+"/w/organ/toOrganMapList").submit();
	});
	$("#user_enquiry").click(function(){
 		window.location.href = _ctxPath + "/w/user/enquiryPriceList.do?userId="+$("#userId").val();
	});
	$("#search_organ").click(function(){
		$("#stroe_list_form").attr("action",_ctxPath+"/w/organ/toSearchOrgan").submit();
	});
});
function organList(page){
	//alert("翻頁");
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
	//var longitude="116.391786289962";
	//var latitude="39.9077741797592";
	var longitude=$("#longitude").val();
	var latitude=$("#latitude").val();
	//alert(longitude);
	var maxDistance="-1";
	var followCount=$("#followCount").val();
	var distance=$("#distance").val();
	//alert("到这里了");
	var pagesize=20;
	$.post(_ctxPath + "/w/organ/organList",{'type':type,'city':city,'district':district,'region':region,'longitude':longitude,'latitude':latitude,'distance':distance,'maxDistance':maxDistance,'followCount':followCount,'page':page,'pagesize':pagesize},
			  function(data){
		var obj=eval(data);
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		if (obj.data.length==0) {
			$("#store_list").addClass("nodateul");
			var html='<li style="background:#eee;border:0;" class="nodatali"><div class="notDate"></div><div class="notDateTitle">暂无相关数据</div></li>';
			$("#store_list").html($("#store_list").html()+html);
		}else {
			$("#store_list").removeClass("nodateul");
				for(var i=0;i<obj.data.length;i++){
					var html='<li onclick="toStoreDetail(this)"><div><div class="store_img">';
					if(obj.data[i].logo==""||obj.data[i].logo=="null"){
						html+='<img src="'+_ctxPath+'/module/resources/images/nopicture.jpg">';
					}else{
						html+='<img src="'+_ossImageHost+obj.data[i].logo+'@!style400'+'">';
					}
					html+='</div><div class="name_rose_attent"><h4 style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">'+obj.data[i].name+'</h4><ul class="flowers">';
					          for(var j=0;j<obj.data[i].zanCount;j++){
					        	  html+='<li></li>';  
					          }
					    html+='</ul><p><span>'+obj.data[i].followCount+'</span> 人关注</p></div><div class="dis_state"><div>';
					    if(obj.data[i].state==0){
					    	html+='<span class="state_busy">空闲</span>';
					    }else if(obj.data[i].state==1){
					    	html+='<span class="state_general">一般</span>';
					    }else if(obj.data[i].state==2){
					    	html+='<span class="state_idle">繁忙</span>';
					    }
		//			    if (!isNaN(obj.data[i].distance)) {
		//			    	
		//			    	if((obj.data[i].distance + '').indexOf('.') != -1){
		//			    		html+='</div><div><div class="distance"><img src="'+_ctxPath+'/module/resources/images/home/icon_location_list.png" />'+
		//				          '<span>'+obj.data[i].distance+'km</span></div></div></div></div><input type="hidden" name="organid" value="'+obj.data[i]._id+'" /><input name="distance" type="hidden" value="'+obj.data[i].distance+'km" /></li>';
		//				  
		//			    	}else{
		//			    		 html+='</div><div><div class="distance"><img src="'+_ctxPath+'/module/resources/images/home/icon_location_list.png" />'+
		//				          '<span>'+obj.data[i].distance+'m</span></div></div></div></div><input type="hidden" name="organid" value="'+obj.data[i]._id+'" /><input name="distance" type="hidden" value="'+obj.data[i].distance+'m" /></li>';
		//				  
		//			    	}
		//			        
		//			    }
					    html+='</div><div><div class="distance">'
					    	+'<span>'+obj.data[i].distance+obj.data[i].unit+'</span>'+
				          '<img src="'+_ctxPath+'/module/resources/images/home/icon_location_list.png" /></div></div></div></div><input type="hidden" name="organid" value="'+obj.data[i]._id+'" /><input name="distance" type="hidden" value="'+obj.data[i].distance+obj.data[i].unit+'" /></li>';
					    $("#store_list").html($("#store_list").html()+html);
				}
		}
	},
			  "json");//这里返回的类型有：json,html,xml,text	
}
function toStoreDetail(thisli){
	var organId=$(thisli).children('input:eq(0)').val();
	var distance=$(thisli).children('input:eq(1)').val();
	var userId=$("#userId").val();
	var cityId=$("#cityId").val();
	var city=$("#city").val();
//	alert(city+"::"+cityId);
	window.location.href = _ctxPath + "/w/organ/toOrganDetail.do?organId="+organId+"&userId="+userId+"&distance="+distance+"&cityId="+cityId+"&city="+encodeURIComponent(encodeURIComponent(city));
}
