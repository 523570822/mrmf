$(function(){
	storeStaffList(1);
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	storeStaffList();
	    }
	});
	
});
function storeStaffList(page){
	if(page==1){
		$("#staff_list").html("");
	}else{
		 var nextPage=parseInt($("#page").val())+1;
		 page=nextPage;
		 var pages=parseInt($("#pages").val());
		 if(nextPage>pages){
			 return;
		 }
	}
	
	var organId=$("#organ_id").val();
	$.post(_ctxPath + "/w/staff/storeStaffList",{'organId':organId,'page':page},
			  function(data){
		var obj=eval(data);
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		var html="";
		$("#staff_list").html("");
		if (obj.data.length !=0) {
			for(var i=0;i<obj.data.length;i++){
				html+='<li><div class="staffimg"><img src="';
				if (obj.data[i].logo==null || obj.data[i].logo=="") {
					html+=_ctxPath+'/module/staff/images/img/nopicture.jpg';
				}else {
					html+=_ossImageHost+obj.data[i].logo+'@!avatar';
				}
				html+='"></div><div class="col-6 font-32">'+obj.data[i].name+'</div><div class="col-6">';
				for ( var j = 0; j <= obj.data[i].zanLevel; j++) {
					html+='<i></i>';
				}
				html+='</div><div class="col-6"style="font-size: 0.9rem;color: #666;"><span>'+obj.data[i].followCount+'</span>人关注</div><div class="col-1">￥'+obj.data[i].startPrice+'起</div></li>';
			}
			$("#staff_info").removeClass('bg_gray');
			$("#staff_list").html($("#staff_list").html()+html);
		}else {
			$("#staff_info").addClass('bg_gray');
			html+='<div></div><i>暂无相关数据</i>';
			$("#staff_list").html($("#staff_list").html()+html);
		}
	},
			  "json");	
}