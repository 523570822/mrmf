$(function() { 
    var longitude =$.trim($("#longitude").val());
    var latitude =$.trim($("#latitude").val());
    if(longitude == "" ||longitude =="0" ||latitude == "" ||latitude =="0") {
    	alert("对不起每页获取到位置,请从新登录！");
    	return;
    }
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
	    var priceCount = 1;
	    if($('#priceSort').hasClass('b_active')) {
	    	priceCount =2;
	    } else {
	    	priceCount =1;
	    }
		var type = $.trim($("#type").text());
		var hotOrder = null;
		var priceOrder = null;
		
		if($('.price_sort').hasClass('b_active')) {
			priceOrder = "price";
		}
		if($('.hot_sort').hasClass('b_active')) {
			hotOrder = "followCount";
		}
		$.ajaxSetup({
	        async: false
	    });
		$.post(_ctxPath + "/w/home/beautyHairList",{'page':page,'type':type,'hotOrder':hotOrder,'priceOrder':priceOrder,"priceCount":priceCount},
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
				/**/
			for(var i=0;i<obj.data.length;i++){
				var html1 ='<div onclick="toCaseDes(this)"><input type="hidden" value="'+obj.data[i]._id+'">'
				         +'<div class="hair_pic"><img src="'+_ossImageHost+obj.data[i].logo[0]+'@!avatar'+'"/></div><div><strong>'+
				         obj.data[i].title+'</strong></div><div class="margin_btm"><div class="hair_price_div">'+
		                    '<span>&yen;</span><span>'+obj.data[i].price+'</span></div><div class="attention_font">'
		                    +'<span>关注</span><span>' +obj.data[i].followCount +'</span></div></div></div>';
				$("#hair_list").html($("#hair_list").html()+ html1);}
		},"json");//这里返回的类型有：json,html,xml,text	
	}
	
	$('.store_type li').click(function () {
        $(this).addClass('add_selected').siblings().removeClass('add_selected');
        $($('#type').get(0)).html($(this).text()+'&nbsp;&nbsp;<img style="height: 0.57rem; width: 0.57rem; margin-left: 0.74rem;"'+'src='+_ctxPath+"/module/resources/images/home/icon_aorrw_down.png"+'>');
        $('#homeType').val($(this).text());
        findList(1);
        $('.modal_bg').fadeOut('fast');
        $('.modal_content ').fadeOut('fast');
    });
	
	$('.modal_bg').click(function() {
		 $('.modal_bg').fadeOut('fast');
	     $('.modal_content ').fadeOut('fast');
	});
	
	$('.hot_sort').click(function() {
        $('.hot_sort').toggleClass('b_active');
        $('.price_sort').removeClass('b_active');
        $('.modal_bg').fadeOut('fast');
        $('.modal_content').fadeOut('fast');
        findList(1);
    });
	
	$('#type').click(function () {
		$('.hair_nav').find('h4').text("精美发型");
		$('.modal_bg').fadeIn('fast');
        $('.modal_content').fadeIn('fast');
        $('.type_sort').get(1).src= _ctxPath+'/module/resources/images/home/icon_aorrw_down_pre.png';
        $('#type_m').css('color','#f4370b');
    });
	
    $('.price_sort').click(function() {
        $('.price_sort').toggleClass('b_active');
        $('.hot_sort').removeClass('b_active');
        $('.modal_bg').fadeOut('fast');
        $('.modal_content').fadeOut('fast');
        findList(1);
    });
    
	/* 点击图标跳转到搜索案例界面 */
	$('.search').click(function() {
		var homeType = $.trim($('.hair_nav').find('h4').eq(0).text());
		window.location.href=_ctxPath+'/w/home/toQueryCase?homeType='+ encodeURIComponent(encodeURIComponent(homeType));
	});
});


/* 去案例详情 */
function toCaseDes(thisli){
	var caseId = $(thisli).find('input').val();
	var homeType = $.trim($('.hair_nav').find('h4').eq(0).text());
    window.location.href = _ctxPath+'/w/home/toCaseDes?caseId='+caseId+'&homeType='+ encodeURIComponent(encodeURIComponent(homeType));
}


