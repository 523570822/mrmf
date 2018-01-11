$(function(){
	storesList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	storesList();
	    }
	});
	$('#back').click(function() {
		var cardId=$("#card_id").val();
		window.location.href=_ctxPath + "/w/user/myCardDetail?cardId=" + cardId;
	});
});
function storesList(){
	var card = 'parent';
	var cardId=$("#card_id").val();
	var page=$("#page").val();
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
	$.post(_ctxPath + "/w/user/cardStoreList",{'cardId':cardId,'page':page,"card":card},
				function(data){
			var obj=eval(data);
			console.info(obj);
			var html='';
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			if (obj.data.length==0) {
				html+='<div class="notOrderStore"></div><div class="notOrderTitleStore">暂无相关数据</div>';
				$("#stores_list").html($("#stores_list").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					html+='<li><div class="stores_img"><img src="';
					if (obj.data[i].logo==null||obj.data[i].logo=="") {
						html+=_ctxPath+'/module/resources/images/nopicture.jpg';
					}else {
						html+=_ossImageHost+obj.data[i].logo+'@!style400';
					}
					html+='"></div><div class="focus_box"><p class="font-30">'+obj.data[i].name+'</p><div>';
					for ( var j = 1; j <= obj.data[i].level; j++) {
						html+='<i></i>';
					}
					html+='</div><p>'+obj.data[i].followCount+' <span>人关注</span></p></div><div class="location-box">';
					if (obj.data[i].state==0) {
						html+='<p class="free">空闲</p>';
					}else if (obj.data[i].state==1) {
						html+='<p class="general">一般</p>';
					}else if (obj.data[i].state==2) {
						html+='<p class="busy">繁忙</p>';
					}
					html+='<p class="wz">'+obj.data[i].distance+ obj.data[i].unit +'<i></i></p></div></li>';
				}
				$("#stores_list").html($("#stores_list").html()+html);
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
}
