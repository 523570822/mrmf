$(function(){
	var selday=$("#day").val();
	if(selday!=""&&typeof(selday)!="undefined"){
		var myDate = new Date();
		var nowhour=myDate.getHours();
		var nowday=myDate.getDate();
		var day=selday.substring(6,8);
		var nowday=myDate.getDate();
		if(day==nowday){
			//var seltime=selectTime.substring(9,11);
			//alert(selday+"::"+seltime);
			//var myDate = new Date();
			var nowhour=myDate.getHours();
			$(".appoint_time_list li").each(function(index){
				//alert(index);
				if(index<=nowhour){
					$(this).removeClass("ok");
				}
			});
		}
	}
	
	$("#appoint_time_back").click(function(){
		$("#appoint_time_form").attr("action",_ctxPath + "/w/organ/toAppointInfo").submit();
	});
	$("#appoint_into_back").click(function(){
		$("#appoint_info_form").attr("action",_ctxPath + "/w/organ/toOrganDetail").submit();
	});
	$("#appoint_store_map_back").click(function(){
		history.go(-1);
	});
	$("#appoint_time").click(function(){
			$("#appoint_info_form").attr("action",_ctxPath + "/w/organ/toAppointTime").submit();
	});
	$("#appoint_store").click(function(){
			$("#appoint_info_form").attr("action",_ctxPath + "/w/organ/toOrganDetailMap").submit();
	});
	$(".appoint_time_list li").click(function(){
		var flag =$(this).hasClass('ok');
		if(flag){
			$(".appoint_time_list li").removeClass("appoint_active");
			$(this).toggleClass("appoint_active");
			var time=$(this).children("span").html();
			var day=$("#day").val();
			$("#select_time").val(day+" "+time);
		}

	});
	
	$(".appoint_week_list li").click(function(){
		var day=$(this).children("input").val();
		var organId=$("#organId").val();
		var userId=$("#userId").val();
		$("#day").val(day);
		//window.location.href = _ctxPath + "/w/organ/toAppointTime.do?organId="+organId+"&day="+day+"&userId="+userId;
		$("#appoint_time_form").attr("action",_ctxPath + "/w/organ/toAppointTime.do").submit();
	});
	$("#appont_type_sub").click(function(){
		var time=$("#select_time").val();
		if(time==""){
			alert("请选择预约时间");
			return;
		}
		$("#appoint_time_form").attr("action", _ctxPath + "/w/organ/saveAppointTime.do").submit();
	});
	$("#appoint_submit").click(function(){
		var organId=$("#organId").val();
		var userId=$("#userId").val();
		var orderServiceId=$("#orderServiceId").val();
		var selectTime=$("#select_time").val();
		if(orderServiceId==""){
			alert("请选择预约项目");
			return;
		}
		if(selectTime==""){
			alert("请选择预约时间");
			return;
		}
		/*alert(selectTime);
		
		
		 
		alert(nowday+"::"+nowhour);
		return;*/
		$("#appoint_submit").attr('disabled',"true");
		 $.post(_ctxPath + "/w/organ/saveAppointInfo",{'organId':organId,'userId':userId,'orderServiceId':orderServiceId,'selectTime':selectTime},
				  function(data){
			 	  	if(data=='true'){
			 	  		alert('恭喜您,您已经预约成功!');
			 	  		//$("#appoint_submit").removeAttr("disabled");
			 	  		$("#appoint_info_form").attr("action",_ctxPath + "/w/organ/toOrganDetail").submit();
			 	  	} else {
			 	  		alert('对不起,您预约失败!');
			 	  	}
				  },
				  "text");//这里返回的类型有：json,html,xml,text
	});
	$("#organ_type").click(function(){
		$("#appoint_info_form").attr("action",_ctxPath + "/w/organ/toOrganType").submit();
	});
	$('.type_list li').click(function() {
		$('.type_list li').children().remove("#choiced");
		$(this).append('<i id="choiced"><img src="'+_ctxPath+'/module/resources/images/icon_choiced.png"/></i>');
		var typeName=$(this).children('input:eq(0)').val();
		var typeId=$(this).children('input:eq(1)').val();
		$("#orderServiceId").val(typeId);
		$("#orderService").val(typeName);
	});
	$(".organ_type_sub").click(function(){
		$("#type_form").attr("action",_ctxPath + "/w/organ/saveOrganType").submit();
	});
	
});