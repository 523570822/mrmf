$(function(){
	ratedList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	ratedList();
	    }
	});
});
function ratedList(){
	var organId=$("#organId").val();
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
	$.post(_ctxPath + "/w/organ/organRatedList",{'organId':organId,'page':page},
			  function(data){
		 	  	var obj=eval(data);
		 	  	$("#pages").val(data.pages);
				$("#page").val(data.page);
				if(obj.data.length==0){
					$("#comment_content").addClass("nodateul");
					var html='<li style="background:#eee;border:0;" class="nodatali"><div class="notDate"></div><div class="notDateTitle">暂无相关数据</div></li>';
					$("#comment_content").html($("#comment_content").html()+html);
				}else{
					$("#comment_content").removeClass("nodateul");
			 	  	for(var i=0;i<obj.data.length;i++){
			 	  		var html='<li><div class="cPhoto2">';
			 	  		if(obj.data[i].userImg==""||obj.data[i].userImg=="null"){
			 	  			html+='<img src="'+_ctxPath+'/module/resources/images/nopicture.jpg">';
			 	  		}else{
			 	  			html+='<img src="'+_ossImageHost+obj.data[i].userImg+'@!avatar'+'">';
			 	  		}
			 	  		
			 	  		html+='</div><div class="estimation"><div style="width: 100%;height: 1.5rem;">'+
			 	  			'<h4 class="font-32">'+obj.data[i].userName+'</h4><div class="flowerList">';
			 	  		for(var j=0;j<obj.data[i].starZan;j++){
			 	  			html+='<h4 class="flowerRight"><img class="img" src="'+_ctxPath+'/module/resources/images/img/icon_flower_90_pre.png"></h4>';
			 	  		}
			 	  		html+='</div></div><div class="time">'+obj.data[i].createTime+'</div><div class="evaluate">'+obj.data[i].content+'</div></div></li>';
			 	  		$("#comment_content").html($("#comment_content").html()+html);
			 	  	}
				}
			  },
			  "json");//这里返回的类型有：json,html,xml,text
}