$(function(){
	myStoreList(1);
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	myStoreList();
	    }
	});
	
});
function myStoreList(page){
	if(page==1){
		$("#myStore_list").html("");
	}else{
		 var nextPage=parseInt($("#page").val())+1;
		 page=nextPage;
		 var pages=parseInt($("#pages").val());
		 if(nextPage>pages){
			 return;
		 }
	}
	/**

	 */
	var staffId=$("#staff_id").val();
	$.post(_ctxPath + "/w/staff/myStoreList",{'staffId':staffId,'page':page},
			  function(data){
		var obj=eval(data);
		var html='';
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		for(var i=0;i<obj.data.length;i++){
			html+='<li class="li" onclick="myStore(this)"><input type="hidden" value="'+obj.data[i].organId+'"><div class="nav2"><div class="col-2"><img src="'+(obj.data[i].logo==null ||obj.data[i].logo==""?_ctxPath+'/module/staff/images/img/nopicture.jpg':_ossImageHost+obj.data[i].logo+"@!style400")+'"></div><div class="col-6 font-30">'
			+obj.data[i].organName+'</div><div class="col-2 color_red font-28">';
			if (obj.data[i].state==0) {
				html+='审核中';
			} else if (obj.data[i].state==1) {
				html+='审核通过';
			} else if (obj.data[i].state==-1) {
				html+='不通过';
			} else if (obj.data[i].state==-2) {
				html+='已解约';
			} else {
				alert('程序有错误');
			}
			html+='</div></div></li>';
		}
		$("#myStore_list").html($("#myStore_list").html()+html);
	}, "json");	
}
function myStore(str) {
	var staffId=$("#staff_id").val();
	 var organId=$(str).children('input:eq(0)').val();
	 window.location.href = _ctxPath + "/w/staff/store.do?staffId="+staffId+"&organId="+organId;
}