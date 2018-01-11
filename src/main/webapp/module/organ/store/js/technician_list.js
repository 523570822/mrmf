$(function(){
	staffList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	staffList();
	    	
	    }
	});
	$("#back").click(function(){
		var organId=$("#organId").val();
		window.location.href = _ctxPath + "/w/organ/toIndex.do?organId="+organId;
	});
});
function staffList(){
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
	$.post(_ctxPath + "/w/organ/organStaffList",{'organId':organId,'page':page},
			  function(data){
		 	  	var obj=eval(data);
		 	  	$("#pages").val(data.pages);
				$("#page").val(data.page);
				if(obj.data.length==0){
					$("#staffList").addClass("nodateul");
					var html='<li style="background:#eee;border:0;" class="nodatali"><div class="notDate"></div><div class="notDateTitle">暂无相关数据</div></li>';
					$("#staffList").html($("#staffList").html()+html);
				}else{
					$("#staffList").removeClass("nodateul");
					for(var i=0;i<obj.data.length;i++){
						var html='<div class="technician_item"><div class="technician_pic">';
						if(obj.data[i].logo==""||obj.data[i].logo=="null"){
							html+='<img src="'+_ctxPath+'/module/resources/images/nopicture.jpg"/>';
							//html+='<img src="'+_ossImageHost+obj.data[i].logo+'"/>';
						}else{
							html+='<img src="'+_ossImageHost+obj.data[i].logo+'@!avatar'+'"/>';
						}
							html+='</div><div class="search_tech_info" ><div class="search_tech_name"><span class="organ_name">'+obj.data[i].name+'</span></div><div class="search_tech_rose"><ul>';
							for(j=0;j<obj.data[i].zanCount;j++){
								html+='<li><img src="'+_ctxPath+'/module/resources/images/enquiryprice/icon_flower_90_pre.png" /> </li>';
							}
							html+='</ul></div><div class="search_tech_attention"><span>'+obj.data[i].followCount+'&nbsp;<i>人关注</i></span></div></div><div class="technician_price">'+
							'<span>&yen;&nbsp;<i>'+obj.data[i].startPrice+'</i>&nbsp;起</span></div></div>';
							
							$("#staffList").html($("#staffList").html()+html);
					}
				}
			  },
			  "json");//这里返回的类型有：json,html,xml,text
}