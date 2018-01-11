$(function(){
	organList(1);
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	organList();
	    }
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
	var organName = $("#organName").val();
	var staffId=$("#staff_id").val();
	var cityId=$("#city_id").val();
	var districtId=$("#district_id").val();
	var regionId=$("#region_id").val();
	$.post(_ctxPath + "/w/staff/storesList",{"organName":organName,'staffId':staffId,'cityId':cityId,'districtId':districtId,'regionId':regionId,'page':page},
			  function(data){
		var obj=eval(data);
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		for(var i=0;i<obj.data.length;i++){
	
			var html='<li onclick="organInfo(this)"><div class="col-7"><div class="div1 font-32">'+obj.data[i].name+'</div><div class="div2 font-28">'+obj.data[i].address+'</div></div>'+'<input type="hidden" value="'+obj.data[i]._id+'" name="organId">'+'<div class="col-3"><img  src="';
			if (obj.data[i].logo==null || obj.data[i].logo=="") {
				html+=_ctxPath+'/module/staff/images/img/nopicture.jpg';
			}else {
				html+=_ossImageHost+obj.data[i].logo+'@!style400';
			}
			html+='"></div>';
			
			$("#store_list").html($("#store_list").html()+html);
		}
	}, "json");	
}
function organInfo(str){
	var organId=$(str).children('input:eq(0)').val();
	var staffId=$("#staff_id").val();
	window.location.href = _ctxPath + "/w/staff/storeDetail?staffId="+staffId+"&organId="+organId;
}