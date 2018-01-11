$(function(){
	estimationList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	estimationList();
	    }
	});
});
function estimationList(){
	var staffId=$("#staff_id").val();
	var page=$("#page").val();
	var pages=$("#pages").val();
	if(page!=""){
		page=parseInt(page)+1;
	}else{
		page=1;
	}
	if(pages!=""){
		pages=parseInt(pages);
		if(page>pages){
			return;
		}
	}
	 
	$.post(_ctxPath + "/w/staffMy/estimationList",{'staffId':staffId,'page':page},
			function(data){
		var obj=eval(data);
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		if (obj.data.length==0) {
			var html='<div class="notOrder"></div><i class="notOrderTitle">暂无相关数据</i>';
			$("#estimation_list").html($("#estimation_list").html()+html);
		}else {
			for(var i=0;i<obj.data.length;i++){
				var html='<li><div class="cPhoto2"><img src="'+_ossImageHost+obj.data[i].userImg+'@!avatar'+'"></div><div class="estimation"><div class="name_level" ><h4 class="font-32">'
				+obj.data[i].userName+'</h4><div class="flowerList">';
				for ( var j = 0; j < obj.data[i].starZan; j++) {
					html+='<i></i>';
				}
				html+='</div></div><div class="time">'+obj.data[i].commentTime+'</div><div class="evaluate">'+obj.data[i].content+'</div></div></li>';
				$("#estimation_list").html($("#estimation_list").html()+html);
			}
		}
	},
	"json");
}