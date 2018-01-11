$(function(){
	tariffList(1);
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	tariffList();
	    }
	});
	
});
function tariffList(page){
	if(page==1){
		$("#tariff_list").html("");
	}else{
		 var nextPage=parseInt($("#page").val())+1;
		 page=nextPage;
		 var pages=parseInt($("#pages").val());
		 if(nextPage>pages){
			 return;
		 }
	}
/**
 * 


 */   var organId=$("#organId").val();
	$.post(_ctxPath + "/w/organ/tariffList",{'page':page,'organId':organId},
			  function(data){
		var obj=eval(data);
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		for(var i=0;i<obj.data.length;i++){
			var html='<li><div class="list"><div class="list_up"><h3 class="font-30">类型</h3><h4 class="font-32">'+obj.data[i].name+'</h4></div><div class="clear"></div><div class="list_down"><div class="list_left"><h3 class="font-30">价格</h3>';
			html+='<h4 class="font-32">￥'+obj.data[i].price+'</h4></div><div class="list_right"><h3 class="font-30">时间</h3><h4 class="font-32">'+obj.data[i].small_time+'分钟</h4></div></div></div></li>';
			$("#tariff_list").html($("#tariff_list").html()+html);
		}
	},
			  "json");	
}
