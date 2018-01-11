$(function(){
	
	myvipCardList(1);
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	myvipCardList();
	    }
	});
	$("#back").click(function() {
		var cardId=$("#card_id").val();
		window.location.href = _ctxPath + "/w/user/myCardDetail.do?cardId=" + cardId;
	});
});
function myvipCardList(page){
	if(page==1){
		$("#card_list").html("");
	}else{
		 var nextPage=parseInt($("#page").val())+1;
		 page=nextPage;
		 var pages=parseInt($("#pages").val());
		 if(nextPage>pages){
			 return;
		 }
	}
	/**
余额￥<a>500</a>.<span>00</span></p></li>
	 */
	var userId=$("#user_id").val();
	var cardId=$("#card_id").val();
	$.post(_ctxPath + "/w/user/myvipInCardList",{'userId':userId,'page':page,'cardId':cardId},
			  function(data){
		var obj=eval(data);
		console.info(data);
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		for(var i=0;i<obj.data.length;i++){
			var html='<li onclick="cardDetail(this)"><p>'+obj.data[i].usersortName+'</p><input type="hidden" value="'
			+obj.data[i]._id+'"><p>';
			if (obj.data[i].flag1=="1001") {
				html+='折扣<a> '+obj.data[i].zhekou*10+' 折</a></p></li>';
			}else if (obj.data[i].flag1=="1002") {
				var str=obj.data[i].money4+'';
				var arr=str.split(".");
				html+='余额￥<a>'+arr[0]+'</a>.<span>'+(arr[1]==null?0:arr[1])+'</span></p></li>';
			}else if (obj.data[i].flag1=="1003") {
				html+='剩余次数<a> '+obj.data[i].shengcishu+'</a></p></li>';
			}
			$("#card_list").html($("#card_list").html()+html);
		}
	}, "json");	
}
function cardDetail(str){
	var incardId=$(str).children('input:eq(0)').val();
	var userId=$("#user_id").val();
	var cardId=$("#card_id").val();
	window.location.href = _ctxPath + "/w/user/myInCardDetail.do?userId="+userId+"&incardId="+incardId+"&cardId="+cardId;
}
