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
		$("#example_list").html("");
	}else{
		 var nextPage=parseInt($("#page").val())+1;
		 page=nextPage;
		 var pages=parseInt($("#pages").val());
		 if(nextPage>pages){
			 return;
		 }
	}
	var staffId=$("#staff_id").val();
	$.post(_ctxPath + "/w/staff/toExampleList",{'staffId':staffId,'page':page},
			  function(obj){
		$("#pages").val(obj.pages);
		$("#page").val(obj.page);
		for(var i=0;i<obj.data.length;i++){
			var html='<li><div class="inner" onclick="exampleInfo(this)"><img src="'+_ossImageHost+obj.data[i].logo[0]+'@!style400'+'"><h4>'+obj.data[i].title+'</h4>'+'<input type="hidden" value="'+obj.data[i]._id+'" name="exampleId">'+'<div><h4 style="color: #f4370b;">&yen;'+obj.data[i].price+'</h4><i>关注&nbsp;'+obj.data[i].followCount+'</i></div></div></li>';
			$("#example_list").html($("#example_list").html()+html);
		}
	}, "json");	
}
function exampleInfo(str){
	var exampleId=$(str).children('input:eq(0)').val();
	var staffId=$("#staff_id").val();
	window.location.href = _ctxPath + "/w/staff/example.do?staffId="+staffId+"&exampleId="+exampleId;
}
