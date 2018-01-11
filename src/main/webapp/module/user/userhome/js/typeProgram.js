$(function() {
	findList(1);
	/*  判断是否滚动到屏幕 */
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	findList();
	    }
	});
	function findList(page){
		if(page==1){
			$("#hair_list").html("");
		}else{
			 var nextPage=parseInt($("#page").val())+1;
			 page=nextPage;
			 var pages=parseInt($("#pages").val());
			 if(nextPage>pages){
				 return;
			 }
		}
		var homeType = $.trim($('#homeType').val());
		var hotOrder = null;
		var priceOrder = null;
		
		if($('.price_sort_type').hasClass('b_active')) {
			priceOrder = "price";
		}
		if($('.hot_sort_type').hasClass('b_active')) {
			hotOrder = "followCount";
		}
		$.ajaxSetup({
	        async: false
	    });
		$.post(_ctxPath + "/w/home/typeProgramList",{'page':page,'type':homeType,'hotOrder':hotOrder,'priceOrder':priceOrder},
				  function(data){
			var obj=eval(data);
			$("#pages").val(data.pages);
			$("#page").val(data.page);
		    if(obj.data.length == 0) {
				var html ='<li style="list-style:none;background:#eee;border:0;"><div class="notOrder"></div><div ' +
					'class="notOrderTitle">暂无相关数据</div></li>';
				$("#hair_list").html($("#hair_list").html()+ html);
				return;
			}
			for(var i=0;i<obj.data.length;i++){
				var html1 ='<div onclick="toCaseDes(this)"><input type="hidden" value="'+obj.data[i]._id+'">'
				         +'<div class="hair_pic"><img src="'+_ossImageHost+obj.data[i].logo[0]+'@!avatar'+'"/></div><div><strong>'+
				         obj.data[i].title+'</strong></div><div class="margin_btm"><div class="hair_price_div">'+
		                    '<span>&yen;</span><span>'+obj.data[i].price+'</span></div><div class="attention_font">'
		                    +'<span>关注</span><span>' +obj.data[i].followCount +'</span></div></div></div>';
				$("#hair_list").html($("#hair_list").html()+ html1);}
		},"json");//这里返回的类型有：json,html,xml,text	
	}
	$('.hot_sort_type').click(function() {
        $('.hot_sort_type').toggleClass('b_active');
        $('.price_sort_type').removeClass('b_active');
        findList(1);
    });
    $('.price_sort_type').click(function() {
        $('.price_sort_type').toggleClass('b_active');
        $('.hot_sort_type').removeClass('b_active');
        findList(1);
    });
	/* 点击图标跳转到搜索案例界面 */
	$('.search').click(function() {
		var title = $.trim($('#title').text());
		window.location.href=_ctxPath+'/w/home/toQueryCase?homeType='+ encodeURIComponent(encodeURIComponent(title));
	});
	
});


/* 去案例详情 */
function toCaseDes(thisli){
	var caseId = $(thisli).find('input').val();
	var homeType = $.trim($('.hair_nav').find('h4').eq(0).text());
	window.location.href = _ctxPath+'/w/home/toCaseDes?caseId='+caseId+'&homeType='+ encodeURIComponent(encodeURIComponent(homeType));
}


