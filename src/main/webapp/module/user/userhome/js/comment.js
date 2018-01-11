$(function(){
	findList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	findList();
	    }
	});
});
function findList(){
	var staffId=$("#staffId").val();
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
	$.post(_ctxPath + "/w/home/commentList",{'staffId':staffId,'page':page},
			  function(data){
		 	  	var obj=eval(data);
		 	  	$("#pages").val(data.pages);
				$("#page").val(data.page);
		 	  	for(var i=0;i<obj.data.length;i++){
		 	  		var html='<li><div class="cPhoto2"><img src="'+_ossImageHost+obj.data[i].userImg+'@!avatar'+'"></div><div class="estimation"><div style="width: 100%;height: 1.5rem;">'+
		 	  			'<h4 class="font-32">'+obj.data[i].userName+'</h4><div class="flowerList">';
		 	  		for(var j=0;j<obj.data[i].starZan;j++){
		 	  			html+='<h4 class="flowerRight"><img class="img" src="'+_ctxPath+'/module/resources/images/img/icon_flower_90_pre.png"></h4>';
		 	  		}
		 	  		html+='</div></div><div class="time">'+obj.data[i].createTime+'</div><div class="evaluate">'+obj.data[i].content+'</div></div></li>';
		 	  		$("#comment_content").html($("#comment_content").html()+html);
		 	  	}
			  },
			  "json");//这里返回的类型有：json,html,xml,text
}