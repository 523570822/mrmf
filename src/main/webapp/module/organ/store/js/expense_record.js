$(function(){	
	storeList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	storeList();
	    }
	});
});
function storeList(){
	var userId=$("#userId").val();
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
	$.post(_ctxPath + "/w/organ/expenseRecord",{'page':page},
			  function(data){
		 	  	var obj=eval(data);
		 	  	$("#pages").val(data.pages);
				$("#page").val(data.page);
				if (obj.data.length==0) {
					var html='<div class="notOrder" style="top:20rem;" ></div><div class="notOrderTitle" style="top:29rem;">暂无相关数据</div>';
					$(".bg_gray").html($(".bg_gray").html()+html);
				}else {
					for(var i=0;i<obj.data.length;i++){
						var html='<div class="expense_item_div"><div><div class="expense_item_date"><span>'+obj.data[i].orderTimeFormat+'</span></div>'+
						'<div class="expense_person"><div><img src="';
						if (obj.data[i].organLogo==null||obj.data[i].organLogo=="") {
							html+=_ctxPath+'/module/resources/images/nopicture.jpg';
						}else {
							html+=_ossImageHost+obj.data[i].organLogo+'@!style400';
						}
						
						html+='"><div><span>-'+obj.data[i].orderPrice+'</span><i>'+obj.data[i].organName+'</i>'+
						'</div></div></div></div></div>';
						$(".bg_gray").html($(".bg_gray").html()+html);
					}
				}
			  },
			  "json");//这里返回的类型有：json,html,xml,text
}