$(function(){
	
	compensateList(1);
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	compensateList();
	    }
	});
	
});
function compensateList(page){
	if(page==1){
		$("#compensate_list").html("");
	}else{
		 var nextPage=parseInt($("#page").val())+1;
		 page=nextPage;
		 var pages=parseInt($("#pages").val());
		 if(nextPage>pages){
			 return;
		 }
	}
	var userId=$("#user_id").val();
	var type=$("#type").val();
	$.post(_ctxPath + "/w/userMy/compensateList",{'userId':userId,'page':page,'type':type},
			  function(data){
		var obj=eval(data);
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		for(var i=0;i<obj.data.length;i++){
			var html='<li class="compensate_item" onclick="compensate(this)"><input type="hidden" value="'+obj.data[i]._id+'"><div><div class="com_name"><span>理赔项目&nbsp;<i>'
			+obj.data[i].service+'</i></span><p>';
			if (obj.data[i].state=="0") {
				html+='待处理';
			}else if (obj.data[i].state=="1") {
				html+='完成';
			}
			html+='</p></div><div class="com_store"><img src="'+(obj.data[i].logo==null || obj.data[i].logo==''?_ctxPath+'/module/staff/images/img/nopicture.jpg':_ossImageHost+obj.data[i].logo+'@!style400')+'" /><span>'+obj.data[i].providerName+'</span></div><div class="com_time"><span>申请时间&nbsp;&nbsp;'
			+obj.data[i].timeFormat+'</span></div></div></li>';
			$("#compensate_list").html($("#compensate_list").html()+html);
		}
	},"json");	
}
function compensate(str){
	var compensateId=$(str).children('input:eq(0)').val();
	var userId=$("#user_id").val();
	window.location.href = _ctxPath + "/w/userMy/compensateResult.do?userId="+userId+"&compensateId="+compensateId;
}
