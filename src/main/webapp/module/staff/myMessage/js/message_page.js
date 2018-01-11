$(function(){
	$.ajaxSetup({
		  async: false
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
		if(index==0){//个人消息
			$("#type").val("1");
			$("#pages").val("");
			$("#page").val("");
			$("#message_list").html("");
			messageList();
		}else if(index==1){//系统消息
			$("#type").val("2");
			$("#pages").val("");
			$("#page").val("");
			$("#message_list").html("");
			$('#sysMessage').val("message");
			messageList();
		}
	});
	$("#back").click(function(){ 
		var sysMessage = $('#sysMessage').val();
		window.location.href = _ctxPath + '/w/staff/mine?sysMessage='+sysMessage+'&status=message';
	});
});
function messageList(){
	var staffId=$("#staff_id").val();
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
	$.post(_ctxPath + "/w/staffMy/myMessageList",{'staffId':staffId,'page':page,'type':type},
				function(data){
			var obj=eval(data);
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			var html="";
			if (obj.data.length==0) {
				html+='<div class="notOrder"></div><i class="notOrderTitle">暂无相关数据</i>';
				$("#message_list").html($("#message_list").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					html+='<li><div class="mess" ';
					if(obj.data[i].readFalg == true) {
						html+='style="color:#9d9d9d;"';
					}	
					html+=' >'+obj.data[i].content+'</div><div class="time">'+obj.data[i].createTimeFormat+'</div></li>';
				}
				$("#message_list").html($("#message_list").html()+html);
			}
		},
	 "json");//这里返回的类型有：json,html,xml,text
}
