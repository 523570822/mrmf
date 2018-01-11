$(function(){
	/*$(".record div").click(function(){
		$(this).siblings().removeClass("sel_earn");
		$(this).addClass("sel_earn");
		var index=$(this).index();
		if(index==0){
			$("#type").val("1");
			$("#pages").val("");
			$("#page").val("");
			//$("#message_context").html("");
			//messageList();
		}else if(index==1){
			$("#type").val("2");
			$("#pages").val("");
			$("#page").val("");
			//$("#message_context").html("");
			//messageList();
		}
	});*/
	earnList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	earnList();
	    }
	});
});
function earnList(){
	var organId=$("#organId").val();
	var page=$("#page").val();
	var pages=$("#pages").val();
//	var type=$("#type").val();
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
	$.post(_ctxPath + "/w/organ/organEarnList",{'organId':organId,'page':page},
			  function(data){
				//console.log(data);
				/*if(""==page){
					$("#message_context").html("");
				}*/
		 	  	var obj=eval(data);
		 	  	
		 	  	$("#pages").val(data.pages);
				$("#page").val(data.page);
				if(obj.data.length==0){
					$("#earnList").addClass("nodateul");
					var html='<li style="background:#eee;border:0;" class="nodatali"><div class="notDate"></div><div class="notDateTitle">暂无相关数据</div></li>';
					$("#earnList").html($("#earnList").html()+html);
				}else{
					$("#earnList").removeClass("nodateul");
			 	  	for(var i=0;i<obj.data.length;i++){

			 	  		var html='<li><div class="shopMess"><h4 class="name">'+obj.data[i].smallsortName+'</h4><h4 class="shopName">'+obj.data[i].organName+'</h4>'+
			 	  			'<h4 class="orderTime">'+obj.data[i].createTimeFormat+'</h4></div><div class="orderMoney"><h4>+ '+obj.data[i].money2+'</h4></div></li>';
			 	  		$("#earnList").html($("#earnList").html()+html);
			 	  	}
				}
			  },
			  "json");//这里返回的类型有：json,html,xml,text	
}