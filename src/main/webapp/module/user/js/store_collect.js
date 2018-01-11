$(function(){
	$("#collect_back").click(function(){
		var organId=$("#organId").val();
		window.location.href = _ctxPath + "/w/organ/toIndex.do?userId="+userId;
	});
	
	$(".my_fun_div li").click(function(){
		$(this).parent().children("li").removeClass("active"); 
		$(this).addClass("active");
		var index=$(this).index()+1;
		$("#type").val(index);
		$("#page").val("");
		$("#pages").val("");
		$("#store_list").html("");
		$("#hair_list").html("");
		$("#list").html("");
		collect();
	});
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	collect();	    	
	    }
	});
	collect();
});
function collect(){
	var userId=$("#userId").val();
	var page=$("#page").val();
	var pages=$("#pages").val();
	var type=$("#type").val();
	alert("type3=="+type);
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
	$.post(_ctxPath + "/w/userMy/myCollect",{'userId':userId,'page':page,'type':type},
			  function(data){
		 	  	var obj=eval(data);
		 	  	$("#pages").val(data.pages);
				$("#page").val(data.page);
				alert(obj.data.length);
				alert(obj.data[0].picture);
				alert(type);
		 	  	for(var i=0;i<obj.data.length;i++){
		 	  		if(type == 1){
		 	  			var html='';
		 	  			if(i == 0){
		 	  				html='<div><div class="hair_pic"><img src="'+obj.data[i].picture+'" /></div><div><strong>'+obj.data[i].title+'</strong></div>'+
		 	  				'<div class="margin_btm"><div class="hair_price_div"><span>&yen;</span> <span>'+obj.data[i].price+'</span></div>'+
		 	  				'<div class="attention_font" ><span>关注</span><span>'+obj.data[i].followCount+'</span></div></div></div>';
		 	  			}else if(i%2==1){
		 	  				html='<div class="hair_list"><div><div class="hair_pic"><img src="'+obj.data[i].picture+'" /></div><div><strong>'+obj.data[i].title+'</strong></div>'+
		 	  				'<div class="margin_btm"><div class="hair_price_div"><span>&yen;</span> <span>'+obj.data[i].price+'</span></div>'+
		 	  				'<div class="attention_font" ><span>关注</span><span>'+obj.data[i].followCount+'</span></div></div></div>';
		 	  			}else if(i%2==0){
		 	  				html='<div><div class="hair_pic"><img src="'+obj.data[i].picture+'" /></div><div><strong>'+obj.data[i].title+'</strong></div>'+
		 	  				'<div class="margin_btm"><div class="hair_price_div"><span>&yen;</span> <span>'+obj.data[i].price+'</span></div>'+
		 	  				'<div class="attention_font" ><span>关注</span><span>'+obj.data[i].followCount+'</span></div></div></div></div>';
		 	  			}
		 	  			$(".hair_list").html($(".hair_list").html()+html);
		 	  		}else if(type == 2){
		 	  			var html='<li><div class="col-2 tx"><img src="'+obj.data[i].picture+'" alt="header"/></div><div class="col-6 txt">'+
		 	  				'<h4>'+obj.data[i].title+'</h4><ul class="flowers"><c:forEach items="'+obj.data[i].zanCount+'" var="zanCnt"><li></li>'+
		                   	'</c:forEach></ul><p><span>'+obj.data[i].followCount+'</span> 人关注</p></div><div class="col-2 location"><div><i></i></div>'+
		                   	'<div class="price">&yen; <span>'+obj.data[i].price+'</span> 起</div></div></li>';
		 	  			$(".list").html($(".list").html()+html);
		 	  		}else if(type == 3){
		 	  			var html='<li><div><div class="store_img"><img src="'+obj.data[i].picture+'"></div><div class="name_rose_attent">'+
		 	  			'<h4>'+obj.data[i].title+'</h4><ul class="flowers"><c:forEach items="'+obj.data[i].zanCount+'" var="zanCnt"><li></li></c:forEach>'+
		 	  			'</ul><p><span>'+obj.data[i].followCount+'</span> 人关注</p></div><div class="dis_state"><div><c:if test="${'+obj.data[i].state+'==0}">'+
		                '<span class="state_idle">空闲</span></c:if><c:if test="${'+obj.data[i].state+'==1}"><span class="state_general">一般</span></c:if>'+
		                '<c:if test="${'+obj.data[i].state+'==2}"><span class="state_busy">繁忙</span></c:if></div><div><div class="distance">'+
		                '<img src="${ctxPath}/module/user/images/icon_location_list.png" /><span>'+obj.data[i].distance+'km</span></div></div></div></div></li>';
		 	  			$(".store_list").html($(".store_list").html()+html);
		 	  		}		 	  				 	  		
		 	  	}
			  },
			  "json");//这里返回的类型有：json,html,xml,text	
}
function changePage(type){
	alert("type::::"+type);
	$("#type").val(type);
	alert("type2::::"+$("#type").val());
	window.location.href = _ctxPath + "/w/userMy/myCollect.do?oppenid=zlj_oppenid_organ";
}