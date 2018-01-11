$(function(){
	getUserList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	getUserList();
	    }
	});
});
function getUserList(){
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
	var redId=$("#redId").val();
		$.post(_ctxPath + "/w/staffMy/getRedUserList",{'redId':redId,'page':page},
				function(data){
			var obj=eval(data);
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			var html="";
			if (obj.data.length==0) {
				html+='<div class="notOrder_wallet"></div><i class="notOrderTitle_wallet">暂无相关数据</i>';
				$("#getUser_list").html($("#getUser_list").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					html+='<li><div class="getMoney"><div class="getMoney-photo"><img src="'+_ossImageHost+obj.data[i].userAvatar+'@!avatar'+'"></div><div class="col-7"><h4 class="h4">'
					+obj.data[i].userNick+'</h4><h4 class="h4-2">'+obj.data[i].createTime+'</h4></div><div class="col-1"><h4>'
					+obj.data[i].amount+'&nbsp;元</h4></div></div></li>';
				}
				$("#getUser_list").html($("#getUser_list").html()+html);
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
}
