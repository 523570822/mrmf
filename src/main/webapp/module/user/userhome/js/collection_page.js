$(function(){
	$.ajaxSetup({
		  async: false
	});
	collectionList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	collectionList();
	    	
	    }
	});
	$(".my_fun_div div").click(function(){
		$(this).siblings().removeClass("sel_message");
		$(this).addClass("sel_message");
		var index=$(this).index();
		if(index==0){//案例收藏
			$("#type").val("1");
			$("#pages").val("");
			$("#page").val("");
			$("#collection_list").html("");
			collectionList();
		}else if(index==1){//技师收藏
			$("#type").val("2");
			$("#pages").val("");
			$("#page").val("");
			$("#collection_list").html("");
			collectionList();
		}else if(index==2){//店铺收藏
			$("#type").val("3");
			$("#pages").val("");
			$("#page").val("");
			$("#collection_list").html("");
			collectionList();
		}
	});
});
function collectionList(){
	var userId=$("#user_id").val();
	var page=$("#page").val();
	var pages=$("#pages").val();
	var type=$("#type").val();
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
	if (type=="1") {//案例收藏
		
		$.post(_ctxPath + "/w/staffMy/collectionCaseList",{'userId':userId,'page':page,'type':type},
				function(data){
			var obj=eval(data);
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			if (obj.data.length==0) {
				var html='<div class="notOrder"style="top:15rem;"></div><i class="notOrderTitle"style="top:24rem;">暂无相关数据</i>';
				$("#collection_list").html($("#collection_list").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					var html='<li onclick="toCaseDes(this)"><input type="hidden" value="'+obj.data[i]._id+'"><div class="hair_list"><div><div class="hair_pic"><img src="'+_ossImageHost+obj.data[i].logo[0]+'@!avatar'+'"/></div><div class="desc"><strong>'+obj.data[i].title+'</strong></div><div class="margin_btm"><div class="hair_price_div"><span>&yen;</span><span>'
					+obj.data[i].price+'</span></div><div class="attention_font"><span>关注</span><span>'+obj.data[i].followCount+'</span></div></div></div></div></li>';
					$("#collection_list").html($("#collection_list").html()+html);
				}
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
		
	}else if (type=="2") {//技师收藏

		$.post(_ctxPath + "/w/staffMy/collectionStaffList",{'userId':userId,'page':page,'type':type},
				function(data){
			var obj=eval(data);
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			if (obj.data.length==0) {
				var html='<div class="notOrder"style="top:15rem;"></div><i class="notOrderTitle"style="top:24rem;">暂无相关数据</i>';
				$("#collection_list").html($("#collection_list").html()+html);;
			}else {
				for(var i=0;i<obj.data.length;i++){
					var html='<li style="width: 100%;height: 5rem;" onclick="staffDetail(this)"><input type="hidden" value="'+obj.data[i]._id+'"><div class="staff_nav"><div class="col-2 tx"><img src="'+_ossImageHost+obj.data[i].logo+'@!style400'+'"/></div><div class="col-6 txt"><h4>'+obj.data[i].name+'</h4><ul class="flowers">';
					for ( var j = 1; j <= obj.data[i].zanCount; j++) {
						html+='<li></li>';
					}
					html+='</ul><p><span>'+obj.data[i].followCount+'</span> 人关注</p></div><div class="col-2 location"><div><i></i></div><div class="price">&yen; <span>'+obj.data[i].startPrice+'</span> 起</div></div></div>';
					$("#collection_list").html($("#collection_list").html()+html);
				}
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
	}else if (type=="3") {//店铺收藏

		$.post(_ctxPath + "/w/staffMy/collectionOrganList",{'userId':userId,'page':page,'type':type},
				function(data){
			var obj=eval(data);
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			if (obj.data.length==0) {
				var html='<div class="notOrder"style="top:15rem;"></div><i class="notOrderTitle"style="top:24rem;">暂无相关数据</i>';
				$("#collection_list").html($("#collection_list").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					var html='<li  style="width: 100%;height: 5rem;" onclick="organDetail(this)"><input type="hidden" value="'+obj.data[i]._id+'"><input name="distance" type="hidden" value="'+obj.data[i].distance+obj.data[i].unit+'" /><div class="organ_div"><div class="store_img"><img src="'+(obj.data[i].logo==null || obj.data[i].logo==''?_ctxPath+'/module/staff/images/img/nopicture.jpg':_ossImageHost+obj.data[i].logo+'@!style400')+'"></div><div class="name_rose_attent"><h4>'
					+obj.data[i].name+'</h4><ul class="flowers">';
					for ( var j = 1; j <= obj.data[i].zanCount; j++) {
						html+='<li></li>';
					}
					html+='</ul><p><span>'+obj.data[i].followCount+'</span> 人关注</p></div><div class="dis_state"><div style="padding-top:0">';
					if (obj.data[i].state==0) {
						html+='<span class="state_idle">空闲</span>';
					}else if (obj.data[i].state==1) {
						html+='<span class="state_general">一般</span>';
					}else if (obj.data[i].state==2) {
						html+='<span class="state_busy">繁忙</span>';
					}
					html+='</div><div><div class="distance"><img src="'+_ctxPath+'/module/user/images/icon_location_list.png" /><span>'+obj.data[i].distance+obj.data[i].unit +'</span></div></div></div></div></li>';
					$("#collection_list").html($("#collection_list").html()+html);
				}
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
	}
}
/* 去案例详情 */
function toCaseDes(str){
	var caseId=$(str).children('input:eq(0)').val();
	var type="collect";
	window.location.href = _ctxPath+'/w/home/toCaseDes?caseId='+caseId+'&type='+type;
}
/* 去技师详情 */
function staffDetail(str){
	var staffId=$(str).children('input:eq(0)').val();
	var type="collect";
	window.location.href = _ctxPath+'/w/home/staffDetail?staffId='+staffId+'&type='+type;
}
/* 去店铺详情 */
function organDetail(str){
	var organId=$(str).children('input:eq(0)').val();
	var distance=$(str).children('input:eq(1)').val();
	var type="collect";
	var city="北京";
	window.location.href = _ctxPath+'/w/organ/toOrganDetail?organId='+organId+'&distance='+distance+'&type='+type+'&city='+encodeURIComponent(encodeURIComponent(city));
}