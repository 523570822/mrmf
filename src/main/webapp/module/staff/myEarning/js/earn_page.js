$(function(){
	$.ajaxSetup({
		  async: false
	});
	earnList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	earnList();
	    }
	});
	$(".messTitle div").click(function(){
		$(this).siblings().removeClass("sel_message");
		$(this).addClass("sel_message");
		var index=$(this).index();
		if(index==0){//收益记录
			$("#type").val("1");
			$("#pages").val("");
			$("#page").val("");
			$("#earn_list").html("");
			earnList();
		}else if(index==1){//项目提成
			$("#type").val("2");
			$("#pages").val("");
			$("#page").val("");
			$("#earn_list").html("");
			earnList();
		}
	});
});
function earnList(){
	var staffId=$("#staff_id").val();
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
	if(type=="1") {
		$.post(_ctxPath + "/w/staffMy/earnList",{'page':page},
					function(data){
				var obj=eval(data);
				$("#pages").val(data.pages);
				$("#page").val(data.page);
				if (obj.data.length==0) {
					var html='<div class="notOrder_wallet"></div><i class="notOrderTitle_wallet">暂无相关数据</i>';
					$("#earn_list").html($("#earn_list").html()+html);
				}else {
					for(var i=0;i<obj.data.length;i++){
						var html='<li><div class="shopMess"><h4 class="name">'+obj.data[i].title+'</h4><h4 class="shopName">'
						+obj.data[i].organName+'</h4><h4 class="orderTime">'+obj.data[i].dateTimeFormat+'</h4></div><div class="orderMoney"><h4>';
						if(obj.data[i].amount>=0) {
							html+='+' +obj.data[i].amount;
						} else {
							html+=obj.data[i].amount;
						}
						html += '</h4></div></li>';
						$("#earn_list").html($("#earn_list").html()+html);
					}
				}
			},
			"json");
	 } else if(type=="2") {
		 $.post(_ctxPath + "/w/staffMy/staffPercentage",{'page':page},
					function(data){
				var obj=eval(data);
				$("#pages").val(data.pages);
				$("#page").val(data.page);
				if (obj.data.length==0) {
					var html='<div class="notOrder_wallet"></div><i class="notOrderTitle_wallet">暂无相关数据</i>';
					$("#earn_list").html($("#earn_list").html()+html);
				}else {
					for(var i=0;i<obj.data.length;i++){
						var html='<li><div class="shopMess"><h4 class="name">'+obj.data[i].smallsortName+'</h4><h4 class="shopName">'
						+obj.data[i].organName+'</h4><h4 class="orderTime">'+obj.data[i].createTime+'</h4></div><div class="orderMoney"><h4>+'
						+obj.data[i].percentage+'</h4></div></li>';
						$("#earn_list").html($("#earn_list").html()+html);
					}
				}
			},
			"json");
	 }
}
