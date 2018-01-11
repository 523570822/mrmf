$(function(){
	$.ajaxSetup({
		  async: false
	});
	var search=$("#searchContent").val().trim();
	if(search!=""){
		$("#store_list").html("");
		organList(1);
	}
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	organList();
	    }
	});
	$("#search").click(function(){
		var search=$("#searchContent").val().trim();
		if(search==""){
			alert("店铺名称不能为空");
			return;
		}
		$("#store_list").html("");
		organList(1);
	});
	$(".map_right").click(function(){
		$("#stroe_list_form").attr("action",_ctxPath+"/w/organ/toOrganMapList").submit();
	});
	$("#organ_search_back").click(function(){
		$("#stroe_list_form").attr("action",_ctxPath+"/w/organ/toOrganList").submit();
	});
	$("#search_organ").click(function(){
		$("#stroe_list_form").attr("action",_ctxPath+"/w/organ/toSearchOrgan").submit();
	});
});
function organList(page){
	//alert("翻頁");
	var search=$("#searchContent").val();
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
	var pagesize=8;
	var longitude=$("#longitude").val();
	var latitude=$("#latitude").val();
	$.post(_ctxPath + "/w/organ/searchOrganList",{'search':search,'longitude':longitude,'latitude':latitude,'page':page,'pagesize':pagesize},
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
	var search=$("#searchContent").val().trim();
//	alert(city+"::"+cityId);
	window.location.href = _ctxPath + "/w/organ/toOrganDetail.do?organId="+organId+"&userId="+userId+"&distance="+distance+"&search="+search+"&cityId="+cityId+"&city="+encodeURIComponent(encodeURIComponent(city));
}
