$(function(){
	organList(1);
	$("#order_by_time").click(function(){
		$(".ckl-title div").removeClass("colorRed");
		$("#choose_time").html("选择日期");
		$(this).addClass("colorRed");
		var flag=$(this).hasClass('colorRed');
		if(flag){
			if ($("#time_sort").val()=="Desc") {
				$("#time_sort").val("Asc");
			}else {
				$("#time_sort").val("Desc");
			}
			organList(1);
		}else{
			$("#time_sort").val("");
		}
	});
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	organList();
	    }
	});
	
});
function organList(page){
	if(page==1){
		$("#sign_list").html("");
	}else{
		 var nextPage=parseInt($("#page").val())+1;
		 page=nextPage;
		 var pages=parseInt($("#pages").val());
		 if(nextPage>pages){
			 return;
		 }
	}
	var staffId=$("#staff_id").val();
	var sort=$("#time_sort").val();
	var time=$("#choose_time").html();
	$.post(_ctxPath + "/w/staff/signStatisticsList",{'staffId':staffId,'timeSort':sort,'time':time},
			  function(data){
		var obj=eval(data);
		var html="";
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		if (obj.data.length==0) {
			html+='<div class="notOrder"></div><i class="notOrderTitle">暂无相关数据</i>';
			$("#sign_list").html($("#sign_list").html()+html);
		}else {
			for(var i=0;i<obj.data.length;i++){
				html+='<li><div class="ckl-list-add"><div class="col-5"><h4 class="font-32">'+obj.data[i].createTime+'</h4></div><div class="col-1"><i class="sign-location"></i></div><div class="col-4"><h4 class="font-32">'+obj.data[i].organName+'</h4></div></div></li>';
			}
			$("#sign_list").html($("#sign_list").html()+html);
		}
	},  "json");	
}
