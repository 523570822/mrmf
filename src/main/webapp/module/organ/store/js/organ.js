$(function(){
	$("#organ_index_status").click(function(){
		var organ_id=$("#organ_id").val();
		var organ_state=$("#organ_state").val();
		window.location.href = _ctxPath + "/w/organ/toChangeState.do?organId="+organ_id+"&organState="+organ_state;
	});
	$(".time_list li").click(function(){
		var flag=!$(this).hasClass('active');
		var index=$(this).index();
		 $.post(_ctxPath + "/w/organ/saveSchedule",{'organId':$("#organId").val(),'day':$("#day").val(),'index':index,'selected':flag},
				  function(data){
			 	  	if(data=='true'){
			 	  		$(".time_list li:nth-child("+(index+1)+")").toggleClass('active');
			 	  	}
				  },
				  "text");//这里返回的类型有：json,html,xml,text
	});
	$(".weeklist li").click(function(){
		var day=$(this).children("input").val();
		var organId=$("#organId").val();
		window.location.href = _ctxPath + "/w/organ/schedule.do?organId="+organId+"&day="+day;
	});
	$("#back").click(function(){
		var organId=$("#organId").val();
		window.location.href = _ctxPath + "/w/organ/toIndex.do?organId="+organId;
	});
	$("#busytime_set").click(function(){
		var organId=$("#organId").val();
		window.location.href = _ctxPath + "/w/organ/toBusytime.do?organId="+organId;
	});
	$("#busytime_back").click(function(){
		var organId=$("#organId").val();
		window.location.href = _ctxPath + "/w/organ/schedule?organId="+organId;
	});
	$("#save_busytime").click(function(){
		var organId=$("#organId").val();
		var busyTimeStart=$("#busyTimeStart").html();
		var busyTimeEnd=$("#busyTimeEnd").html();
		if(""==busyTimeStart){
			alert("请选择开始时间");
			return;
		}
		
		if(""==busyTimeEnd){
			alert("请选择开始时间");
			return;
		}
		//window.location.href = _ctxPath + "/w/organ/saveBusytime.do?organId="+organId+"&busyTimeStart="+busyTimeStart+"&busyTimeEnd="+busyTimeEnd;
		$.post(_ctxPath + "/w/organ/saveBusytime",{'organId':organId,'busyTimeStart':busyTimeStart,'busyTimeEnd':busyTimeEnd},
				  function(data){
			 	  	if(data=='true'){
			 	  		window.location.href = _ctxPath + "/w/organ/schedule?organId="+organId;
			 	  	}
				  },
				  "text");//这里返回的类型有：json,html,xml,text
	});
	$(".btn_box ul li").mousedown(function(){
		$(this).addClass("active");
	});
});

function wallet(t){
//	$(t).siblings().removeClass("active");
//	$(t).addClass("active");
	$("#organ_index_form").attr("action",_ctxPath+"/w/organ/wallet").submit();
	//alert($("#organ_index_form").attr("action"));
}
function paypsw(t){
//	$(t).siblings().removeClass("active");
//	$(t).addClass("active");
	$("#organ_index_form").attr("action",_ctxPath+"/w/organ/toMyPayPassword").submit();
	//alert($("#organ_index_form").attr("action"));
}
function schedule(t){
//	$(t).siblings().removeClass("active");
//	$(t).addClass("active");
	$("#organ_index_form").attr("action",_ctxPath+"/w/organ/schedule").submit();
	//alert($("#organ_index_form").attr("action"));
}
function order(t){
//	$(t).siblings().removeClass("active");
//	$(t).addClass("active");
	$("#organ_index_form").attr("action",_ctxPath+"/w/organ/toOrderList").submit();
}
function staff(t){
//	$(t).siblings().removeClass("active");
//	$(t).addClass("active");
	$("#organ_index_form").attr("action",_ctxPath+"/w/organ/toStaffList").submit();
}
function message(t){
//	$(t).siblings().removeClass("active");
//	$(t).addClass("active");
	$("#organ_index_form").attr("action",_ctxPath+"/w/organ/toMessageList").submit();
}
function rated(t){
//	$(t).siblings().removeClass("active");
//	$(t).addClass("active");
	$("#organ_index_form").attr("action",_ctxPath+"/w/organ/toRatedList").submit();
}
function custom(t){
//	$(t).siblings().removeClass("active");
//	$(t).addClass("active");
	$("#organ_index_form").attr("action",_ctxPath+"/w/organ/toCustomList").submit();
}
function organ_set(t){
//	$(t).siblings().removeClass("active");
//	$(t).addClass("active");
	$("#organ_index_form").attr("action",_ctxPath+"/w/organ/toOrganSet").submit();
}
function earn(t){
//	$(t).siblings().removeClass("active");
//	$(t).addClass("active");
	$("#organ_index_form").attr("action",_ctxPath+"/w/organ/toEarnList").submit();
}
function son(t){
//	$(t).siblings().removeClass("active");
//	$(t).addClass("active");
	//查到子公司,弹层，选中店铺 更改from中的organId
    $("#organ_index_form").attr("action",_ctxPath+"/w/organ/toSonList").submit();
}