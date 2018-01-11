$(function(){
	instruction();
	/*$.ajaxSetup({
		  async: false
	});
	ruleList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	ruleList();
	    	
	    }
	});
	$(".messTitle div").click(function(){
		$(this).siblings().removeClass("sel_message");
		$(this).addClass("sel_message");
		var index=$(this).index();
		if(index==0){//赔付规则
			$("#type").val("1");
			$("#pages").val("");
			$("#page").val("");
			$("#compensate_list").html("");
			ruleList();
		}else if(index==1){//申诉处理人
			$("#type").val("2");
			$("#pages").val("");
			$("#page").val("");
			$("#compensate_list").html("");
			ruleList();
		}
	});*/
});
/*function ruleList(){
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
	*//**

	
	 	
	 *//*
	if (type=="1") {//规则说明
		instruction();
	}else {//申诉处理人(暂无)
		$.post(_ctxPath + "/w/userMy/myMessageList",{'page':page,'type':type},
				function(data){
			var obj=eval(data);
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			var html="";
			if (obj.data.length==0) {
				html='<div class="notOrder"></div><i class="notOrderTitle">暂无相关数据</i>';
				$("#compensate_list").html($("#compensate_list").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					html+='<li><div class="mess">'+obj.data[i].content+'</div><div class="time">'+obj.data[i].createTimeFormat+'</div></li>';
				}
				$("#compensate_list").html($("#compensate_list").html()+html);
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
	}
}*/
function instruction() {
	$("#compensate_list").html("");
	var html='<li style="border:0;height:25rem;width:92%;"><div class="compensate"><span>理赔规则</span>';
		html+='<span>1、顾客凡使用任性猫平台进行预约美发的，需熟知注意事项，以便给您带来不必要的麻烦及损失。</span>';
		html+='<span>2、遇过敏问题申请理赔的需要提供三甲级及以上医院的相关证明及正规专业检测机构的书面检测证明，否则将不予以理赔。</span>';
		html+='<span>3、顾客在剪发前需向发型师叙述清楚所需发型，长短。</span>';
		html+='<span>4、洗发时需向助理提出是否对洗护产品过敏，或有特殊要求的需提前向助理提出。</span>';
		html+='<span>5、烫染头发前需向发型师说明是否过敏，如不知自己是否过敏，可向发型师主动提出做皮肤测试。</span>';
		html+='<span>6、烫发后需按照发型师提的建议来护理自己的头发，如因顾客个人原因护理不当，在烫发后出现发型不理想的，则不予以受理理赔请求。</span>';
		html+='<span>7、如顾客做完头发离店后，要求理赔的，因已离店无法判定责任方（过敏除外），任性猫售后将不予以理赔。</span>';
		html+='<span>8、如顾客对平台理赔处理结果有异议，可到相关部门进行检测，提供相关书面检测结果。</span>';
		html+='</div></li>';
	$("#compensate_list").html(html);
}