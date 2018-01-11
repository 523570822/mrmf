var hide=function(){
	var showDiv=document.getElementById("showDiv");
	var content=document.getElementById("content");
	var contentJoin=document.getElementById("content_join");
	showDiv.style.display='none'; 
	content.style.display='none';
	contentJoin.style.display='none';
}
var show=function(){
	var showDiv=document.getElementById("showDiv");
	var content=document.getElementById("content");
	var content=document.getElementById("content");
	showDiv.style.display='block';
	content.style.display='block';
}
var showtime=function(){
	var showDiv=document.getElementById("showDiv");
	var showtime=document.getElementById("showtime");
	showDiv.style.display='block';
	showtime.style.display='block';
}
function onload() {
	var desc=document.getElementById("desc");
	var descInfo=document.getElementById("desc_info");
	desc.style="border-bottom:2px solid #f4370b;  color:#f4370b;";
	descInfo.style.display="block";
}

function chooseArea() {
	var area=document.getElementById("area");
	var content=document.getElementById("content");
	var showDiv=document.getElementById("showDiv");
	var background=document.getElementById("background");
	area.style="color:#f4370b;";
	showDiv.style.display="block";
	content.style.display="block";
	background.style="background: url('../mrmf/module/staff/images/img/icon_aorrw_down_pre.png')center no-repeat;";
	}
$(function() {
	
	$("#choose li").click(function() {
		$("#choose li span").removeClass('click');
		$(this).children('span:eq(0)').addClass('click');
		if ($(this).index()==0) {
			$("#desc_info").css({'display':'block'});
			$("#discount_info").css({'display':'none'});
			$("#staff_info").css({'display':'none'});
		}else if ($(this).index()==1) {
			$("#desc_info").css({'display':'none'});
			$("#discount_info").css({'display':'block'});
			$("#staff_info").css({'display':'none'});
		}else if ($(this).index()==2) {
			$("#desc_info").css({'display':'none'});
			$("#discount_info").css({'display':'none'});
			$("#staff_info").css({'display':'block'});
		}
	});
	$("#organ_follow").click(function() {
		var flag=$("#organ_follow").hasClass("follow");
		if (flag) {
			$.ajax({
				url:_ctxPath + "/w/staff/follow.do",
				type:"post",
				data:{'staffId':$("#staff_id").val(),'organId':$("#organ_id").val(),'followType':'cancel'},
				success : function(msg) { 
						$("#organ_follow").removeClass("follow");
						$("#count").html(msg);
						$("#collect").html("收藏");
				}
			});
		}else {
			$.ajax({
				url:_ctxPath + "/w/staff/follow.do",
				type:"post",
				data:{'staffId':$("#staff_id").val(),'organId':$("#organ_id").val(),'followType':'follow'},
				success : function(msg) { 
						$("#organ_follow").addClass("follow");
						$("#count").html(msg);
						$("#collect").html("取消收藏");
				}
			});
		}
	});
});

function toChangeType() {//跳转类型选择
	$("#addExample_form").attr("action",_ctxPath+"/w/staff/selectType").submit();
}