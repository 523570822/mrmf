$(function(){
	$.ajaxSetup({
		  async: false
	});
	$("#messageback").click(function(){
		var organId=$("#organId").val();
		window.location.href = _ctxPath + "/w/organ/toIndex.do?organId="+organId;
	});
	messageList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	messageList();
	    }
	});
	$(".messTitle div").click(function(){
		$(this).siblings().removeClass("sel_message");
		$(this).addClass("sel_message");
		var index=$(this).index();
		if(index==0){
			$("#type").val("1");
			$("#pages").val("");
			$("#page").val("");
			$("#message_context").html("");
			messageList();
		}else if(index==1){
			$("#type").val("2");
			$("#pages").val("");
			$("#page").val("");
			$("#message_context").html("");
			messageList();
		}
	});
});
function messageList(){
	var organId=$("#organId").val();
	var page=$("#page").val();
	var pages=$("#pages").val();
	var type=$("#type").val();
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
	$.post(_ctxPath + "/w/organ/organMessageList",{'organId':organId,'page':page,'type':type},
			  function(data){
				if(""==page){
					$("#message_context").html("");
				}
		 	  	var obj=eval(data);
		 	  	console.log(obj.data);
		 	  	$("#pages").val(data.pages);
				$("#page").val(data.page);
				if(obj.data.length==0){
					$("#message_context").addClass("nodateul");
					var html='<li style="background:#eee;border:0;" class="nodatali"><div class="notDate"></div><div class="notDateTitle">暂无相关数据</div></li>';
					$("#message_context").html($("#message_context").html()+html);
				}else{
					$("#message_context").removeClass("nodateul");
			 	  	for(var i=0;i<obj.data.length;i++){
			 	  		var html="";
			 	  		
			 	  		if(obj.data[i].readFalg==true){
			 	  			html='<li style="color:#9d9d9d">';
			 	  		}else{
			 	  			html='<li>';
			 	  		}
			 	  		 html+='<div class="mess">'+obj.data[i].content+'</div><div class="time">'+obj.data[i].createTimeFormat+'</div></li>';
			 	  		$("#message_context").html($("#message_context").html()+html);
			 	  	}
				}
			  },
			  "json");//这里返回的类型有：json,html,xml,text	
}
