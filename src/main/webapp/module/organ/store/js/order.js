$(function(){
	var starZan =$("#starZan").val();
	if(starZan!=""){
		starZan=parseInt(starZan);
	}
	for(var i=0;i<starZan;i++){
		var html ='<li></li>';
		$("#starZan_flower").html($("#starZan_flower").html()+html);
	}
	
	orderList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	orderList();
	    	
	    }
	});
	$("#orderDetail_back").click(function(){
		var organId=$("#organId").val();
		window.location.href = _ctxPath+"/w/organ/toOrderList.do?organId="+organId;
	});
	$("#back").click(function(){
		var organId=$("#organId").val();
		window.location.href = _ctxPath + "/w/organ/toIndex.do?organId="+organId;
	});
});
function orderList(){
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
	$.post(_ctxPath + "/w/organ/organOrderList",{'organId':organId,'page':page},
			  function(data){
		 	  	var obj=eval(data);
		 	  	$("#pages").val(data.pages);
				$("#page").val(data.page);
				if(obj.data.length==0){
					$(".order_list").addClass("nodateul");
					var html='<li style="background:#eee;border:0;" class="nodatali"><div class="notDate"></div><div class="notDateTitle">暂无相关数据</div></li>';
					$(".order_list").html($(".order_list").html()+html);
				}else{
					$(".order_list").removeClass("nodateul");
			 	  	for(var i=0;i<obj.data.length;i++){
			 	  		var html='<li onclick="orderDetail(this);"><input value="'+obj.data[i]._id+'" type="hidden"/><h4><i class="beauty"></i>'+obj.data[i].title+'<span>已完成</span></h4><ul><li>';
			 	  		if(obj.data[i].organLogo==""||obj.data[i].organLogo=="null"){
			 	  			html+='<img src="'+_ctxPath+'/module/resources/images/nopicture.jpg">';
			 	  		}else{
			 	  			html+='<img src="'+_ossImageHost+obj.data[i].organLogo+'@!style400'+'" alt="tx"/>';
			 	  		}
			 	  		
			 	  		html+='<span>'+obj.data[i].organName+'</span></li><li><label>预约时间</label><span>'+obj.data[i].orderTime+'</span></li>'+
			 	  			'<li><label>店铺地址</label><span>'+obj.data[i].organAddress+'</span></li></ul></li>';
			 	  		$(".order_list").html($(".order_list").html()+html);
			 	  	}
				}
			  },
			  "json");//这里返回的类型有：json,html,xml,text
}
function orderDetail(orderThis){
	var organId=$("#organId").val();
	var orderId=$(orderThis).children("input").val();
	window.location.href = _ctxPath + "/w/organ/toOrderDetail.do?orderId="+orderId+"&organId="+organId;
}
function customDetail(){//会员消费详情
	var userId=$("#user_id").val();
	window.location.href = _ctxPath + "/w/staffMy/customerDetail2.do?userId="+userId;
}