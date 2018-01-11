$(function() {
	$('#profile').click(function() {
		//var staffId = $.trim($("#staffId").val());
		//window.location.href=_ctxPath + "/w/home/staffDetail?staffId="+staffId+'&longitude=116.391786289962&latitude=34.9077741797592';
		$("#staffDetailForm").attr("action",_ctxPath + "/w/home/staffDetail").submit();
	});
	findList(1);
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	findList();
	    }
	});
});
function findList(page){
	if(page==1){
		$("#cases").html("");
	}else{
		 var nextPage=parseInt($("#page").val())+1;
		 page=nextPage;
		 var pages=parseInt($("#pages").val());
		 if(nextPage>pages){
			 return;
		 }
	}
	var staffId = $.trim($("#staffId").val());
	$.post(_ctxPath + "/w/home/queryCases",{'page':page,"staffId":staffId },
			  function(data){
		var obj=eval(data);
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		if(obj.data.length == 0) {
			$("#cases").removeClass('hair_list');
			$("#cases").addClass('bg_gray');
			var html = '<li style="list-style:none;" class="noData"><div></div><i>暂无相关数据</i></li>';
			$("#cases").html($("#cases").html()+ html);
		}
		
		for(var i=0;i<obj.data.length;i++){
			var html ='<div onclick="toCaseDes(this)"><input type="hidden" value="'+obj.data[i]._id+'">'
			         +'<div class="hair_pic"><img src="'+_ossImageHost + obj.data[i].logo[0]+'@!style400'+'"/></div><div><strong>'+
			         obj.data[i].title+'</strong></div><div class="margin_btm"><div class="hair_price_div">'+
	                    '<span>&yen;</span><span>'+obj.data[i].price+'</span></div><div class="attention_font">'
	                    +'<span>关注</span><span>' +obj.data[i].followCount +'</span></div></div></div>';
			$("#cases").html($("#cases").html()+ html);}
	},"json");//这里返回的类型有：json,html,xml,text	
}

/* 去案例详情 */
function toCaseDes(thisli){
	var caseId = $(thisli).find('input').val();
	var type="staff";
	var cityId=$("#cityId").val();
	var city=$("#city").val();
//	if (type=="collect") {
//		type+="store";
//	}
	var organId=$("#organId").val();
	var distance=$("#distance").val();
	var come = $("#come").val();
	window.location.href = _ctxPath+'/w/home/toCaseDes?caseId='+caseId+'&type='+type+'&come='+come+'&organId='+organId+'&cityId='+cityId+'&come=organDetail&distance='+distance+'&city='+encodeURIComponent(encodeURIComponent(city));
}
