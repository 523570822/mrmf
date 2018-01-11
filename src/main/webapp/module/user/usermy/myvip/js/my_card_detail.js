$(function(){
	$.ajaxSetup({
		  async: false
	});
	cardDetailList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	cardDetailList();
	    }
	});
	$(".card_info a").click(function(){
		$(this).siblings().removeClass("cA");
		$(this).addClass("cA");
		var index=$(this).index();
		if(index==0){//会员卡详情
			$("#type").val("1");
			$("#pages").val("");
			$("#page").val("");
			$("#card_list").html("");
			$("#card_list").attr("class", "");
			$("#card_list").addClass("basic_info_list");
			cardDetailList();
		}else if(index==1){//消费记录
			$("#type").val("2");
			$("#pages").val("");
			$("#page").val("");
			$("#card_list").html("");
			$("#card_list").attr("class", "");
			$("#card_list").addClass("record_list");
			cardDetailList();
		}else if(index==2){//充值记录
			$("#type").val("3");
			$("#pages").val("");
			$("#page").val("");
			$("#card_list").html("");
			$("#card_list").attr("class", "");
			$("#card_list").addClass("top-up_list");
			cardDetailList();
		}
	});
	$(".card_img a").click(function() {
		$(this).children('p:eq(0)').addClass("none");
		$(this).children('p:eq(1)').addClass("none");
	});
	$("#back").click(function() {//返回
		window.location.href = _ctxPath + "/w/user/myvipCard";
	});
	$("#my_incard").click(function() {//查看子卡
		var cardId=$("#card_id").val();
		window.location.href = _ctxPath + "/w/user/myvipInCard?cardId="+cardId;
	});
});
function cardDetailList(){
	var userId=$("#user_id").val();
	var cardId=$("#card_id").val();
	var page=$("#page").val();
	var pages=$("#pages").val();
	var type=$("#type").val();
	var cardType="parent";
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
	if (type==1) {//会员详情
		$.post(_ctxPath + "/w/user/cardDetailList",{'cardId':cardId,'page':page},
				function(data){
			var obj=eval(data);
			var html='';
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			if (obj.data.length==0) {
				html+='<li><div class="notOrder"></div><div class="notOrderTitle">暂无相关数据</div></li>';
				$("#card_list").html($("#card_list").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					if (obj.data[i].flag1=="1001") {//单纯打折卡
						html+='<li><span>金额</span><span>'+obj.data[i].money4+'元</span></li><li><span>卡类型</span><span>'
						+(obj.data[i].cardType==null?'无':obj.data[i].cardType)+'</span></li><li><span>办卡日期</span><span>'+(obj.data[i].createTimeFormat==null?'':obj.data[i].createTimeFormat)+'</span></li><li><span>有效期</span><span>'+(obj.data[i].lawDayFormat==null?'':obj.data[i].lawDayFormat)+'</span></li><li><span>开卡门店</span><span>'
						+obj.data[i].organName+'</span></li><li><span>折扣</span><span>'+(obj.data[i].zhekou==null || obj.data[i].zhekou ==0?'无':obj.data[i].zhekou/10+'折');
						html+='</span></li><li><span>积分</span><span>'+(obj.data[i].coin==null?0:obj.data[i].coin)+'</span></li>';
					}else if (obj.data[i].flag1=="1002") {//存钱打折卡
						html+='<li><span>金额</span><span>'+obj.data[i].money4+'元</span></li><li><span>卡类型</span><span>'
						+(obj.data[i].cardType==null?'无':obj.data[i].cardType)+'</span></li><li><span>办卡日期</span><span>'+(obj.data[i].createTimeFormat==null?'':obj.data[i].createTimeFormat)+'</span></li><li><span>有效期</span><span>'+(obj.data[i].lawDayFormat==null?'':obj.data[i].lawDayFormat)+'</span></li><li><span>开卡门店</span><span>'
						+obj.data[i].organName+'</span></li><li><span>折扣</span><span>'+(obj.data[i].zhekou==null || obj.data[i].zhekou ==0?'无':obj.data[i].zhekou/10+'折');
						html+='</span></li><li><span>积分</span><span>'+(obj.data[i].coin==null?0:obj.data[i].coin)+'</span></li>';
					}else if (obj.data[i].flag1=="1003") {//次数卡
						html+='<li><span>卡类型</span><span>'
						+(obj.data[i].cardType==null?'无':obj.data[i].cardType)+'</span></li><li><span>办卡日期</span><span>'+(obj.data[i].createTimeFormat==null?'':obj.data[i].createTimeFormat)+'</span></li><li><span>有效期</span><span>'+(obj.data[i].lawDayFormat==null?'':obj.data[i].lawDayFormat)+'</span></li><li><span>开卡门店</span><span>'
						+obj.data[i].organName+'</span></li><li><span>剩余次数</span><span>'+obj.data[i].shengcishu+'</span></li>';
						html+='</span></li><li><span>积分</span><span>'+(obj.data[i].coin==null?0:obj.data[i].coin)+'</span></li>';
					}
				}
				/*<li><span>金额</span><span>'+obj.data[i].money4+'元</span></li>*/
				$("#card_list").html($("#card_list").html()+html);
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
	}else if (type==2) {//消费记录
		$.post(_ctxPath + "/w/user/customList",{'cardId':cardId,'page':page,'cardType':cardType},
				function(data){
			var obj=eval(data);
			var html='';
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			if (obj.data.length==0) {
				html+='<li><div class="notOrder"></div><div class="notOrderTitle">暂无相关数据</div></li>';
				$("#card_list").html($("#card_list").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					html+='<li><a class="font-34">'+obj.data[i].createTimeFormat+'</a><a><img src="'+_ossImageHost+obj.data[i].organLogo+'@!style400'+'"></a><a><span class="font-34">';
					if(obj.data[i].money_xiaofei== 0) {
						html+='消费' +obj.data[i].cishu +'次';
					} else {
						html+='-'+obj.data[i].money_xiaofei+'元';
					}
					html+= '</span><br /><span>'+obj.data[i].organName+'</span></a></li>';
				}
				$("#card_list").html($("#card_list").html()+html);
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
	}else if (type==3) {//充值记录
		var cardType="parent";
		$.post(_ctxPath + "/w/user/rechargeList",{'userId':userId,'cardId':cardId,'page':page,'cardType':cardType},
				function(data){
			var obj=eval(data);
			var html='';
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			if (obj.data.length==0) {
				html+='<li><div class="notOrder"></div><div class="notOrderTitle">暂无相关数据</div></li>';
				$("#card_list").html($("#card_list").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					html+='<li><div class="top-up_list_left"><p class="font-30 line-top-up">';
					if (obj.data[i].weixinCharge !=null && obj.data[i].weixinCharge==true) {
						html+='在线充值';
					}else {
						html+='门店充值';
					}
					html+='</p><time class="font-24 top-up_time">'
					+obj.data[i].createTimeFormat+'</time></div><div class="top-up_list_right"><a class="font-32">+'+obj.data[i].money2+'</a></div></li>';
				}
				$("#card_list").html($("#card_list").html()+html);
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
	}
}
function cardStore() {
	var userId=$("#user_id").val();
	var cardId=$("#card_id").val();
	var card="parent";
	window.location.href = _ctxPath + "/w/user/cardStore.do?userId="+userId+"&cardId="+cardId+"&card="+card;
}


function payOnline(){
	var userId=$("#user_id").val();
	var cardId=$("#card_id").val();
	var cardType=$("#usersort").val();
	window.location.href = _ctxPath + "/w/pay/toPayOnline.do?userId="+userId+"&cardId="+cardId+"&cardType="+cardType;
}