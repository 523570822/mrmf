$(function(){
	$.ajaxSetup({
		  async: false
	});
	organList(1);
	$(".sort_enq li:nth-child(1)").click(function(){
		$(this).toggleClass("color_red");
		var flag=$(this).hasClass('color_red');
		if(flag){
			$("#orderStatus").val("price");
			var hot=$("#hot");
			var distance=$("#distance");
			if(hot.hasClass('color_red')){
				hot.toggleClass("color_red");
			}
			if (distance.hasClass('color_red')) {
				distance.toggleClass("color_red");
			}
			organList(1);
		}else{
			$("#orderStatus").val("");
		}
	});
	$(".sort_enq li:nth-child(2)").click(function(){
		$(this).toggleClass("color_red");
		var flag=$(this).hasClass('color_red');
		if(flag){
			$("#orderStatus").val("hot");
			var price=$("#price");
			var distance=$("#distance");
			if(price.hasClass('color_red')){
				price.toggleClass("color_red");
			}
			if (distance.hasClass('color_red')) {
				distance.toggleClass("color_red");
			}
			organList(1);
		}else{
			$("#orderStatus").val("");
		}
	});
	$(".sort_enq li:nth-child(3)").click(function(){
		$(this).toggleClass("color_red");
		var flag=$(this).hasClass('color_red');
		if(flag){
			$("#orderStatus").val("distance");
			var price=$("#price");
			var hot=$("#hot");
			if(price.hasClass('color_red')){
				price.toggleClass("color_red");
			}
			if (hot.hasClass('color_red')) {
				hot.toggleClass("color_red");
			}
			organList(1);
		}else{
			$("#orderStatus").val("");
		}
	});
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	organList();
	    }
	});
	
});
function organList(page){
	if(page==1){
		$("#quote_list").html("");
	}else{
		 var nextPage=parseInt($("#page").val())+1;
		 page=nextPage;
		 var pages=parseInt($("#pages").val());
		 if(nextPage>pages){
			 return;
		 }
	}
	var userId=$("#user_id").val();
	var orderStatus=$("#orderStatus").val();
	$.post(_ctxPath + "/w/user/toQuoteList",{'inquiryId':$("#inquiry_id").val(),'orderStatus':orderStatus,'page':page},
			  function(data){
		var obj=eval(data);
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		var html="";
		for(var i=0;i<obj.data.length;i++){
			html+='<li><div class="sort_background" onclick="quoteDetail(this)"><input type="hidden" value="'+obj.data[i]._id+'"><div class="sort_appointment_div" ><div class="sort_appointment_price"><span class="price_font_small">&yen;</span><span class="price_font_big">'+obj.data[i].price+'</span></div><div class="person_div" ><div class="person"><div class="person_img_div"><img class="person_img" src="'+_ossImageHost+obj.data[i].logo+'@!style400'+'" /><span class="person_font">';
			html+=obj.data[i].name+'</span></div><div class="listroles">';
			for ( var j = 0; j < obj.data[i].level; j++) {
				html+='<i></i>';
			}
			html+='</div></div><div class="position_div"><div class="distance"><i></i><span class="position_div_span">'+obj.data[i].distance+'km</span></div><div style="clear: both"><span class="attention" ><span class="attention_num">'+obj.data[i].followCount+'</span> 人关注</span></div></div></div></div></li>';
		}
		$("#quote_list").html($("#quote_list").html()+html);
	},
			  "json");	
}
function quoteDetail(str) {//询价详情
	 var userId=$("#user_id").val();
	 var quoteId=$(str).children('input:eq(0)').val();
	 window.location.href = _ctxPath + "/w/user/enquiryDetail.do?quoteId="+quoteId+"&userId="+userId;
}
